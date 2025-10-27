package persistence.jpa.boot;

import jakarta.persistence.spi.PersistenceUnitInfo;

import java.util.List;

public interface EntityManagerFactoryConfigurator {

    PersistenceUnitInfo persistenceUnitInfo(String name);

    List<String> entityClassNames();

}
