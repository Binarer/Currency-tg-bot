package easy.currencytgbot.Bot.Domain.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRateResponse {
    @JsonProperty("result")
    private String result;

    @JsonProperty("base_code")
    private String baseCode;

    @JsonProperty("conversion_rates")
    private Map<String, Double> conversionRates;
}
