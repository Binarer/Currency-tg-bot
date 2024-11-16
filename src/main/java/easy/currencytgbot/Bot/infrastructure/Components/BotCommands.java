package easy.currencytgbot.Bot.infrastructure.Components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "start bot"),
            new BotCommand("/help", "bot info"),
            new BotCommand("/rate", "rate currency"),
            new BotCommand("/convert", "convert currency"),
             new BotCommand("/crypto", "rate crypto")
    );

    String HELP_TEXT = "Этот бот создан для просмотра текущего круса валют." +
            "В настоящий момент работают эти команды:\n\n" +
            "/start - запуск!\n"+
            "/help - вспомогательное меню.\n"+
            "/rate - текущий курс валют.\n"+
            "/convert - рассчитать конвертацию валют из одной в другую.\n"+
            "/crypto - получить курс криптовалюты";
}
