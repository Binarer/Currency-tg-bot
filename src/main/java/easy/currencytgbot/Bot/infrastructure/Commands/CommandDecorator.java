package easy.currencytgbot.Bot.infrastructure.Commands;

import easy.currencytgbot.Bot.Application.Bot.Bot;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class CommandDecorator implements Command {
    protected Command decoratedCommand;

    public CommandDecorator(Command decoratedCommand) {
        this.decoratedCommand = decoratedCommand;
    }

    @Override
    public void execute(Update update, Bot bot) {
        decoratedCommand.execute(update, bot);
    }
}
