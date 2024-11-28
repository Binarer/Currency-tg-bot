package easy.currencytgbot.Bot.Application.Interfaces;
/**
 * Интерфейс для сервиса конвертации валют.
 * Этот интерфейс определяет метод для конвертации суммы из одной валюты в другую.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
public interface ICurrencyConversionService {
    double convert(String fromCurrency, String toCurrency, double amount);
}
