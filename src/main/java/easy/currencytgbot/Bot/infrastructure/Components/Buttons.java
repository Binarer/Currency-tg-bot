package easy.currencytgbot.Bot.infrastructure.Components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для создания и управления кнопками интерфейса бота.
 * Этот класс предоставляет методы для создания разметки кнопок для Telegram бота.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
public class Buttons {

    /**
     * Кнопка для запуска бота.
     */
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");

    /**
     * Кнопка для отображения справки.
     */
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");

    /**
     * Кнопка для отображения текущего курса валют.
     */
    private static final InlineKeyboardButton RATE_BUTTON = new InlineKeyboardButton("Rate");

    /**
     * Кнопка для конвертации валют.
     */
    private static final InlineKeyboardButton CONVERT_BUTTON = new InlineKeyboardButton("Convert");

    /**
     * Кнопка для отображения курса криптовалют.
     */
    private static final InlineKeyboardButton CRYPTO_BUTTON = new InlineKeyboardButton("Crypto");

    /**
     * Создает разметку кнопок для Telegram бота.
     *
     * @return разметка кнопок
     */
    public static InlineKeyboardMarkup inlineMarkup() {
        START_BUTTON.setCallbackData("/start");
        HELP_BUTTON.setCallbackData("/help");
        RATE_BUTTON.setCallbackData("/rate");
        CONVERT_BUTTON.setCallbackData("/convert");
        CRYPTO_BUTTON.setCallbackData("/crypto");

        List<InlineKeyboardButton> rowInline = List.of(START_BUTTON, HELP_BUTTON, RATE_BUTTON, CONVERT_BUTTON, CRYPTO_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
    /**
     * Создает клавиатуру для выбора валюты.
     *
     * @return клавиатура
     */
    public static ReplyKeyboardMarkup createCurrencyKeyboard() {
        ReplyKeyboardMarkup markup = ReplyKeyboardMarkup.builder().resizeKeyboard(true).oneTimeKeyboard(true).build();

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("USD"));
        row1.add(new KeyboardButton("EUR"));
        row1.add(new KeyboardButton("RUB"));
        row1.add(new KeyboardButton("CNY"));

        keyboard.add(row1);
        markup.setKeyboard(keyboard);
        return markup;
    }
}
