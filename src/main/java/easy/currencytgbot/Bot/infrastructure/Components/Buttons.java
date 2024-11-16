package easy.currencytgbot.Bot.infrastructure.Components;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class Buttons {
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");
    private static final InlineKeyboardButton RATE_BUTTON = new InlineKeyboardButton("Rate");
    private static final InlineKeyboardButton CONVERT_BUTTON = new InlineKeyboardButton("Convert");
    private static final InlineKeyboardButton CRYPTO_BUTTON = new InlineKeyboardButton("Crypto");

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
}
