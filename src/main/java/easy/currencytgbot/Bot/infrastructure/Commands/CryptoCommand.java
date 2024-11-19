package easy.currencytgbot.Bot.infrastructure.Commands;

import easy.currencytgbot.Bot.Application.Bot.Bot;
import easy.currencytgbot.Bot.Domain.Models.CryptoSymbol;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
@Slf4j
@Component
public class CryptoCommand implements Command {
    @Override
    public void execute(Update update, Bot bot) {
        long chatId = update.getMessage().getChatId();
        List<CryptoSymbol> cryptoRates = bot.getCryptoService().getCryptoRates();
        StringBuilder messageText = new StringBuilder();
        messageText.append("🤑 Курс крипты:\n");

        for (CryptoSymbol symbol : cryptoRates) {
            messageText.append(String.format("""
        💵 %s:
        🔹 Last: $%s
        🔹 Lowest: $%s
        🔹 Highest: $%s
        🔹 Date: %s
        🔹 Daily Change: %s%%
        \n""", symbol.getSymbol(), symbol.getLast(), symbol.getLowest(), symbol.getHighest(), symbol.getDate(), symbol.getDailyChangePercentage()));
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText.toString());

        try {
            bot.execute(message);
            log.info("Crypto rates sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}