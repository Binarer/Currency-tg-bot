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

/**
 * Команда для получения и отправки курсов криптовалют.
 * Этот класс реализует интерфейс {@link Command} и предоставляет метод для выполнения команды получения курсов криптовалют.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
@Slf4j
@Component
public class CryptoCommand implements Command {
    /**
     * Выполняет команду получения и отправки курсов криптовалют.
     *
     * @param update обновление, содержащее информацию о сообщении или событии
     * @param bot    экземпляр бота, который будет использоваться для выполнения команды
     */
    @Override
    public void execute(Update update, Bot bot) {
        long chatId = update.getMessage().getChatId();
        List<CryptoSymbol> cryptoRates = bot.getCryptoService().getCryptoRates();
        StringBuilder messageText = getMessageText(cryptoRates);
        SendMessage message = SendMessage.builder().chatId(chatId).text(messageText.toString()).build();
        try {
            bot.execute(message);
            log.info("Crypto rates sent");
        } catch (TelegramApiException e) {
            log.error("Error sending crypto rates: {}", e.getMessage(), e);
        }
    }

    private static StringBuilder getMessageText(List<CryptoSymbol> cryptoRates) {
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
        return messageText;
    }
}