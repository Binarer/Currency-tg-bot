package easy.currencytgbot.Bot.Domain.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CryptoSymbol {
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("last")
    private String last;

    @JsonProperty("last_btc")
    private String lastBtc;

    @JsonProperty("lowest")
    private String lowest;

    @JsonProperty("highest")
    private String highest;

    @JsonProperty("date")
    private String date;

    @JsonProperty("daily_change_percentage")
    private String dailyChangePercentage;
}
