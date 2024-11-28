package easy.currencytgbot.Bot.Domain.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import easy.currencytgbot.Bot.Application.Interfaces.ICurrencyService;
import easy.currencytgbot.Bot.Domain.Models.CurrencyRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Сервис для получения текущих курсов валют.
 * Этот класс реализует интерфейс {@link ICurrencyService} и предоставляет метод для получения текущих курсов валют.
 *
 * @author Эдуард Минаков
 * @version 1.0
 * @since 2024-11-21
 */
@Service
public class CurrencyService implements ICurrencyService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * URL API для получения курсов валют.
     */
    @Value("${currency.api.url}")
    private String api;

    /**
     * Конструктор сервиса для получения курсов валют.
     *
     * @param restTemplate экземпляр {@link RestTemplate} для выполнения HTTP-запросов
     * @param objectMapper экземпляр {@link ObjectMapper} для преобразования JSON в объекты Java
     */
    public CurrencyService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Получает текущий курс валюты.
     *
     * @param currency валюта, для которой нужно получить курс
     * @return строка с текущими курсами валюты в формате:
     * 💹 Текущий курс для {currency}:
     * 💵 USD: {курс}
     * 💶 EUR: {курс}
     * 💴 CNY: {курс}
     * 💸 RUB: {курс}
     */
    @Override
    public String getCurrentCurrencyRate(String currency) {
        String apiUrl = api + currency;
        String response = restTemplate.getForObject(apiUrl, String.class);

        try {
            CurrencyRateResponse currencyRateResponse = objectMapper.readValue(response, CurrencyRateResponse.class);
            Map<String, Double> conversionRates = currencyRateResponse.getConversionRates();
            return formatCurrencyRate(currency, conversionRates);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching currency rate";
        }
    }

    /**
     * Форматирует текущий курс валюты в строку.
     *
     * @param currency        валюта, для которой нужно сформировать строку
     * @param conversionRates карта курсов валют
     * @return строка с текущими курсами валюты в формате:
     * 💹 Текущий курс для {currency}:
     * 💵 USD: {курс}
     * 💶 EUR: {курс}
     * 💴 CNY: {курс}
     * 💸 RUB: {курс}
     */
    private String formatCurrencyRate(String currency, Map<String, Double> conversionRates) {
        return String.format("""
                        💹 Текущий курс для %s:
                        💵 USD: %.2f
                        💶 EUR: %.2f
                        💴 CNY: %.2f
                        💸 RUB: %.2f
                        """,
                currency,
                conversionRates.getOrDefault("USD", 0.0),
                conversionRates.getOrDefault("EUR", 0.0),
                conversionRates.getOrDefault("CNY", 0.0),
                conversionRates.getOrDefault("RUB", 0.0));
    }
}