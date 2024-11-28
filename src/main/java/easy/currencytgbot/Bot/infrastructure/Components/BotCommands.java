package easy.currencytgbot.Bot.infrastructure.Components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;
/**
 * Интерфейс для команд бота.
 * Этот интерфейс определяет список команд и текст справки для бота.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
public interface BotCommands {
    /**
     * Список команд бота.
     */
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "Запустить бота"),
            new BotCommand("/help", "Информация о боте"),
            new BotCommand("/rate", "Текущий курс валют"),
            new BotCommand("/convert", "Конвертация валют"),
            new BotCommand("/crypto", "Курс криптовалют")
    );


    /**
     * Текст справки для бота.
     */
    String HELP_TEXT = """
            Этот бот создан для просмотра текущего курса валют и криптовалют, а также для конвертации валют.
            В настоящий момент доступны следующие команды:

            /start - Запустить бота
            /help - Показать это меню справки
            /rate - Показать текущий курс валют
            /convert - Рассчитать конвертацию валют из одной в другую
            /crypto - Показать курс криптовалют
            """;
}
