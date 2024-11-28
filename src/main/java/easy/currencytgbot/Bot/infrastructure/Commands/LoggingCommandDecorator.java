package easy.currencytgbot.Bot.infrastructure.Commands;

import easy.currencytgbot.Bot.Application.Bot.Bot;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
/**
 * Декоратор для логирования выполнения команд.
 * Этот класс расширяет {@link CommandDecorator} и добавляет логирование перед и после выполнения команды.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
@Slf4j
public class LoggingCommandDecorator extends CommandDecorator {
    /**
     * Конструктор декоратора для логирования команд.
     *
     * @param decoratedCommand декорируемая команда
     */
    public LoggingCommandDecorator(Command decoratedCommand) {
        super(decoratedCommand);
    }
    /**
     * Выполняет декорируемую команду с логированием.
     *
     * @param update обновление, содержащее информацию о сообщении или событии
     * @param bot экземпляр бота, который будет использоваться для выполнения команды
     */
    @Override
    public void execute(Update update, Bot bot) {
        log.info("Executing command: {}", decoratedCommand.getClass().getSimpleName());
        super.execute(update, bot);
        log.info("Command executed: {}", decoratedCommand.getClass().getSimpleName());
    }
}
