package easy.currencytgbot.Bot.Domain.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import easy.currencytgbot.Bot.Application.Interfaces.ICurrencyConversionService;
import easy.currencytgbot.Bot.Domain.Models.CurrencyRateResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyConversionService implements ICurrencyConversionService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CurrencyConversionService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public double convert(String fromCurrency, String toCurrency, double amount) {
        String apiUrl = "https://v6.exchangerate-api.com/v6/73d3ee6f3557bbc103075d44/latest/" + fromCurrency;
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