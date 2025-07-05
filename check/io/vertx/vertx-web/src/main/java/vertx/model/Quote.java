package vertx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Quote implements Json {
    private Asset asset;
    private BigDecimal bid;
    private BigDecimal ask;
    @JsonProperty("last_price")
    private BigDecimal lastPrice;
    private BigDecimal volume;
}
