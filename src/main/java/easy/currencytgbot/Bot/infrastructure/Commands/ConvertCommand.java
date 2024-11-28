package easy.currencytgbot.Bot.infrastructure.Commands;


import easy.currencytgbot.Bot.Application.Bot.Bot;
import easy.currencytgbot.Bot.infrastructure.Components.Buttons;
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
 * Команда для выбора валюты для конвертации.
 * Этот класс реализует интерфейс {@link Command} и предоставляет метод для выполнения команды выбора валюты.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
@Slf4j
@Component
public class ConvertCommand implements Command {
    /**
     * Выполняет команду выбора валюты для конвертации.
     *
     * @param update обновление, содержащее информацию о сообщении или событии
     * @param bot    экземпляр бота, который будет использоваться для выполнения команды
     */
    @Override
    public void execute(Update update, Bot bot) {
        long chatId = update.getMessage().getChatId();

        SendMessage message = SendMessage.builder().chatId(chatId).text("Выберите валюту для конвертации:").build();
        message.setReplyMarkup(Buttons.createCurrencyKeyboard());

        bot.getUserStateStorage().setUserState(chatId, "FROM_CURRENCY");

        try {
            bot.execute(message);
            log.info("Conversion selection sent");
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке выбора конвертации валюты: {}", e.getMessage(), e);
        }
    }
}
