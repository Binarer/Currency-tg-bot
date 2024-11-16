package easy.currencytgbot.Bot.Application.Interfaces;

public interface ICurrencyConversionService {
    double convert(String fromCurrency, String toCurrency, double amount);
}
