package easy.currencytgbot.Bot.infrastructure.Components;
import easy.currencytgbot.Bot.Application.Bot.Bot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    void execute(Update update, Bot bot);
}
