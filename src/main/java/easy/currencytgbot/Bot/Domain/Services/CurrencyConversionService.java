package easy.currencytgbot.Bot.Domain.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import easy.currencytgbot.Bot.Application.Interfaces.ICurrencyConversionService;
import easy.currencytgbot.Bot.Domain.Models.CurrencyRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyConversionService implements ICurrencyConversionService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Value("${currency.api.url}")
    private String api;

    public CurrencyConversionService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

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