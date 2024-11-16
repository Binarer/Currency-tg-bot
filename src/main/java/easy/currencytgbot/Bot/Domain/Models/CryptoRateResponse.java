package easy.currencytgbot.Bot.Domain.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoRateResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("symbols")
    private List<CryptoSymbol> symbols;
}
