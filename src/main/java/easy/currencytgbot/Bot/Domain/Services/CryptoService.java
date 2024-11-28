package easy.currencytgbot.Bot.Domain.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import easy.currencytgbot.Bot.Application.Interfaces.ICryptoService;
import easy.currencytgbot.Bot.Domain.Models.CryptoRateResponse;
import easy.currencytgbot.Bot.Domain.Models.CryptoSymbol;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Сервис для получения курсов криптовалют.
 * Этот класс реализует интерфейс {@link ICryptoService} и предоставляет метод для получения курсов криптовалют.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
@Getter
@Service
public class CryptoService implements ICryptoService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * URL API для получения курсов криптовалют.
     */
    @Value("${crypto.api.url}")
    private String api;

    /**
     * Ключ API для доступа к курсам криптовалют.
     */
    @Value("${crypto.api.key}")
    private String key;

    /**
     * Конструктор сервиса для получения курсов криптовалют.
     *
     * @param restTemplate экземпляр {@link RestTemplate} для выполнения HTTP-запросов
     * @param objectMapper экземпляр {@link ObjectMapper} для преобразования JSON в объекты Java
     */
    public CryptoService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Получает курсы криптовалют.
     *
     * @return список символов криптовалют с их курсами
     * @throws RuntimeException если произошла ошибка при получении курсов криптовалют
     */
    @Override
    public List<CryptoSymbol> getCryptoRates() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + key);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(api, HttpMethod.GET, entity, String.class);
        String response = responseEntity.getBody();

        try {
            CryptoRateResponse cryptoRateResponse = objectMapper.readValue(response, CryptoRateResponse.class);
            return cryptoRateResponse.getSymbols();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching crypto rates");
        }
    }
}