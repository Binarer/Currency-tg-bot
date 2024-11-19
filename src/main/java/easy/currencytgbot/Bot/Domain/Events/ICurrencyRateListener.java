package easy.currencytgbot.Bot.Domain.Events;

public interface ICurrencyRateListener {
    void onCurrencyRateUpdated(CurrencyRateUpdatedEvent event);
}
