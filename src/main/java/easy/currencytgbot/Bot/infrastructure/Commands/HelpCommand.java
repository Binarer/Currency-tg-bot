package easy.currencytgbot.Bot.infrastructure.Commands;

import easy.currencytgbot.Bot.Application.Bot.Bot;
import easy.currencytgbot.Bot.infrastructure.Components.BotCommands;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.stereotype.Component;
/**
 * Команда для отправки справки пользователю.
 * Этот класс реализует интерфейс {@link Command} и предоставляет метод для выполнения команды отправки справки.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
@Slf4j
@Component
public class HelpCommand implements Command {
    /**
     * Выполняет команду отправки справки пользователю.
     *
     * @param update обновление, содержащее информацию о сообщении или событии
     * @param bot экземпляр бота, который будет использоваться для выполнения команды
     */
    @Override
    public void execute(Update update, Bot bot) {
        long chatId = update.getMessage().getChatId();
        SendMessage message = SendMessage.builder().chatId(chatId).text(BotCommands.HELP_TEXT).build();
        try {
            bot.execute(message);
            log.info("Help message sent");
        } catch (TelegramApiException e) {
            log.error("Error sending help message: {}", e.getMessage(), e);
        }
    }
}
