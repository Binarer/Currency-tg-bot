package easy.currencytgbot.Bot.infrastructure.Commands;

import easy.currencytgbot.Bot.Application.Bot.Bot;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Абстрактный класс декоратора для команд бота.
 * Этот класс реализует интерфейс {@link Command} и предоставляет базовую функциональность для декорирования команд.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
public abstract class CommandDecorator implements Command {

    /**
     * Декорируемая команда.
     */
    protected Command decoratedCommand;

    /**
     * Конструктор декоратора команды.
     *
     * @param decoratedCommand декорируемая команда
     */
    public CommandDecorator(Command decoratedCommand) {
        this.decoratedCommand = decoratedCommand;
    }

    /**
     * Выполняет декорируемую команду.
     *
     * @param update обновление, содержащее информацию о сообщении или событии
     * @param bot экземпляр бота, который будет использоваться для выполнения команды
     */
    @Override
    public void execute(Update update, Bot bot) {
        decoratedCommand.execute(update, bot);
    }
}
