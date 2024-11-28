package easy.currencytgbot.Bot.Domain.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import easy.currencytgbot.Bot.Application.Interfaces.ICurrencyConversionService;
import easy.currencytgbot.Bot.Domain.Models.CurrencyRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
/**
 * Сервис для конвертации валют.
 * Этот класс реализует интерфейс {@link ICurrencyConversionService} и предоставляет метод для конвертации валют.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
@Service
public class CurrencyConversionService implements ICurrencyConversionService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * URL API для получения курсов валют.
     */
    @Value("${currency.api.url}")
    private String api;

    /**
     * Конструктор сервиса конвертации валют.
     *
     * @param restTemplate экземпляр {@link RestTemplate} для выполнения HTTP-запросов
     * @param objectMapper экземпляр {@link ObjectMapper} для преобразования JSON в объекты Java
     */
    public CurrencyConversionService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Конвертирует сумму из одной валюты в другую.
     *
     * @param fromCurrency исходная валюта
     * @param toCurrency целевая валюта
     * @param amount сумма для конвертации
     * @return конвертированная сумма
     * @throws RuntimeException если произошла ошибка при получении курса валют
     */
    @Override
    public double convert(String fromCurrency, String toCurrency, double amount) {
        String apiUrl = api + fromCurrency;
        String response = restTemplate.getForObject(apiUrl, String.class);

        try {
            CurrencyRateResponse currencyRateResponse = objectMapper.readValue(response, CurrencyRateResponse.class);
            Map<String, Double> conversionRates = currencyRateResponse.getConversionRates();
            double rate = conversionRates.getOrDefault(toCurrency, 0.0);
            return amount * rate;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching currency rate");
        }
    }
}