package easy.currencytgbot.Bot.infrastructure.Commands;

import easy.currencytgbot.Bot.Application.Bot.Bot;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class LoggingCommandDecorator extends CommandDecorator {

    public LoggingCommandDecorator(Command decoratedCommand) {
        super(decoratedCommand);
    }

    @Override
    public void execute(Update update, Bot bot) {
        log.info("Executing command: {}", decoratedCommand.getClass().getSimpleName());
        super.execute(update, bot);
        log.info("Command executed: {}", decoratedCommand.getClass().getSimpleName());
    }
}
