package easy.currencytgbot.Bot.infrastructure.Commands;

import easy.currencytgbot.Bot.Application.Bot.Bot;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для приветствия пользователя.
 * Этот класс реализует интерфейс {@link Command} и предоставляет метод для выполнения команды приветствия.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
@Slf4j
@Component
public class StartCommand implements Command {
    /**
     * Выполняет команду приветствия пользователя.
     *
     * @param update обновление, содержащее информацию о сообщении или событии
     * @param bot    экземпляр бота, который будет использоваться для выполнения команды
     */
    @Override
    public void execute(Update update, Bot bot) {
        long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getFrom().getFirstName();

        SendMessage message = SendMessage.builder().chatId(chatId)
                .text(String.format("Привет, %s! Я бот для конвертации валют и криптовалют. Как я могу помочь?", userName))
                .build();
        ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup.builder().resizeKeyboard(true).oneTimeKeyboard(true).build();

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("/rate"));
        row.add(new KeyboardButton("/convert"));
        row.add(new KeyboardButton("/crypto"));
        row.add(new KeyboardButton("/help"));
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            bot.execute(message);
            log.info("Welcome message sent");
        } catch (TelegramApiException e) {
            log.error("Error sending welcome message: {}", e.getMessage(), e);
        }
    }
}