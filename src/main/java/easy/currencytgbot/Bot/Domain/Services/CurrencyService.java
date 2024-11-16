package easy.currencytgbot.Bot.Domain.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import easy.currencytgbot.Bot.Application.Interfaces.ICurrencyService;
import easy.currencytgbot.Bot.Domain.Models.CurrencyRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyService implements ICurrencyService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Value("${currency.api.url}")
    private String api;
    public CurrencyService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

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
