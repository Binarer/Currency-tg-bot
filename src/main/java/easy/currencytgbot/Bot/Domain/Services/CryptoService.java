package easy.currencytgbot.Bot.Domain.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import easy.currencytgbot.Bot.Application.Interfaces.ICryptoService;
import easy.currencytgbot.Bot.Domain.Models.CryptoRateResponse;
import easy.currencytgbot.Bot.Domain.Models.CryptoSymbol;
import lombok.Getter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Getter
@Service
public class CryptoService implements ICryptoService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CryptoService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    @Override
    public List<CryptoSymbol> getCryptoRates() {
        String apiUrl = "https://api.freecryptoapi.com/v1/getData?symbol=BTC+ETH+ETHBTC@binance";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer ems58cndr82sb0wxvgoe");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
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