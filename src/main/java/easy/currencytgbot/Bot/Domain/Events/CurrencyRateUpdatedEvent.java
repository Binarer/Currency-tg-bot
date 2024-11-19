package easy.currencytgbot.Bot.Domain.Events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CurrencyRateUpdatedEvent {
    private String currencyCode;
    private double newRate;
}
