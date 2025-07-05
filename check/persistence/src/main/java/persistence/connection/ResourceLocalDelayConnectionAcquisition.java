package persistence.connection;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.DataSourcePoolAdapter;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import persistence.AbstractConfigurator;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
class ResourceLocalDelayConnectionAcquisition extends AbstractConfigurator {

    private static final String DATA_FILE_PATH = "data/weather.xml";
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private final long warmUpDuration = TimeUnit.SECONDS.toNanos(5);
    private final long measurementsDuration = TimeUnit.SECONDS.toNanos(15);
    private final int parseCount = 100;

    private FlexyPoolDataSource<DataSource> flexyPoolDataSource;

    public static void main(String[] args) {
        new ResourceLocalDelayConnectionAcquisition().runExperiment();
    }

    @Override
    protected Class<?>[] entities() {
        return new Class[]{
                Forecast.class
        };
    }

    @Override
    protected boolean connectionPooling() {
        return false;
    }

    @Override
    protected HikariConfig hikariConfig(DataSource dataSource) {
        HikariConfig hikariConfig = super.hikariConfig(dataSource);
        hikariConfig.setAutoCommit(false);
        return hikariConfig;
    }

    @Override
    protected HikariDataSource connectionPoolDataSource(DataSource dataSource) {
        HikariConfig hikariConfig = new HikariConfig();
        int cpuCores = Runtime.getRuntime().availableProcessors();
        hikariConfig.setMaximumPoolSize(cpuCores * 4);
        hikariConfig.setDataSource(dataSource);

        return new HikariDataSource(hikariConfig);
    }

    @Override
    protected DataSource newDataSource() {
        DataSource dataSource = super.newDataSource();

        com.vladmihalcea.flexypool.config.Configuration<DataSource> configuration = new com.vladmihalcea.flexypool.config.Configuration.Builder<>(
                getClass().getSimpleName(), dataSource, DataSourcePoolAdapter.FACTORY)
                .setMetricLogReporterMillis(TimeUnit.SECONDS.toMillis(15))
                .build();
        flexyPoolDataSource = new FlexyPoolDataSource<>(configuration);
        return flexyPoolDataSource;
    }

    @Override
    protected Properties properties() {
        Properties properties = super.properties();
        properties.put("hibernate.generate_statistics", Boolean.FALSE.toString());
        properties.put("hibernate.jdbc.batch_size", "50");
        //
        // ~ connectionLeaseMillis p95
        //
        properties.put("hibernate.connection.provider_disables_autocommit", "true");
        return properties;
    }

    private void runExperiment() {
        long warmUpThreshold = System.nanoTime() + warmUpDuration;
        log.info("Warming up");
        while (System.nanoTime() < warmUpThreshold) {
            importForecasts();
        }

        long measurementsThreshold = System.nanoTime() + measurementsDuration;
        log.info("Measuring connection lease time");
        flexyPoolDataSource.start();
        while (System.nanoTime() < measurementsThreshold) {
            importForecasts();
        }
        flexyPoolDataSource.stop();
        sleep(500);
    }

    private void importForecasts() {
        doInJPA(entityManager -> {
            List<Forecast> forecasts = null;

            for (int i = 0; i < parseCount; i++) {
                Document forecastXmlDocument = readXmlDocument(DATA_FILE_PATH);
                forecasts = parseForecasts(forecastXmlDocument);
            }

            if (forecasts != null) {
                for (Forecast forecast : forecasts.subList(0, 50)) {
                    entityManager.persist(forecast);
                }
            }
        });
    }

    private Document readXmlDocument(String filePath) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private List<Forecast> parseForecasts(Document xmlDocument) {
        NodeList cityNodes = xmlDocument.getElementsByTagName("localitate");
        List<Forecast> forecasts = new ArrayList<>();
        for (int i = 0; i < cityNodes.getLength(); i++) {
            Node cityNode = cityNodes.item(i);
            String city = cityNode.getAttributes().getNamedItem("nume").getNodeValue();

            NodeList forecastNodes = cityNode.getChildNodes();
            for (int j = 0; j < forecastNodes.getLength(); j++) {
                Node forecastNode = forecastNodes.item(j);
                if (!"prognoza".equals(forecastNode.getNodeName())) {
                    continue;
                }

                Forecast forecast = new Forecast();
                forecast.setCity(city);

                String dateValue = forecastNode.getAttributes().getNamedItem("data").getNodeValue();
                try {
                    forecast.setDate(format.parse(dateValue));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }

                NodeList forecastDetailsNodes = forecastNode.getChildNodes();
                for (int k = 0; k < forecastDetailsNodes.getLength(); k++) {
                    Node forecastDetailsNode = forecastDetailsNodes.item(k);
                    switch (forecastDetailsNode.getNodeName()) {
                        case "temp_min":
                            forecast.setTemperatureMin(Byte.parseByte(forecastDetailsNode.getTextContent()));
                            break;
                        case "temp_max":
                            forecast.setTemperatureMax(Byte.parseByte(forecastDetailsNode.getTextContent()));
                            break;
                        case "fenomen_descriere":
                            forecast.setDescription(forecastDetailsNode.getTextContent());
                            break;
                    }
                }

                forecasts.add(forecast);
            }
        }
        return forecasts;
    }

    @Entity(name = "Forecast")
    @Getter
    @Setter
    public static class Forecast {
        @Id
        @GeneratedValue
        private Long id;
        private String city;
        @Temporal(TemporalType.DATE)
        @Column(name = "forecast_date")
        private Date date;
        @Column(name = "temperature_min")
        private byte temperatureMin;
        @Column(name = "temperature_max")
        private byte temperatureMax;
        private String description;
    }
}
