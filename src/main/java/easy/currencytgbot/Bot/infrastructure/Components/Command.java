package easy.currencytgbot.Bot.infrastructure.Components;
import easy.currencytgbot.Bot.Application.Bot.Bot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс для команд бота.
 * Этот интерфейс определяет метод для выполнения команды.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
public interface Command {

    /**
     * Выполняет команду.
     *
     * @param update обновление, содержащее информацию о сообщении или событии
     * @param bot экземпляр бота, который будет использоваться для выполнения команды
     */
    void execute(Update update, Bot bot);
}