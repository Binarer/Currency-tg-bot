package easy.currencytgbot.Bot.Application.Interfaces;

import easy.currencytgbot.Bot.Domain.Models.CryptoSymbol;

import java.util.List;

public interface ICryptoService {
    List<CryptoSymbol> getCryptoRates();
}
