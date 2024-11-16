package easy.currencytgbot.Bot.infrastructure.Components.Commands;

import easy.currencytgbot.Bot.Application.Bot.Bot;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
public class RateCommand implements Command {
    @Override
    public void execute(Update update, Bot bot) {
        long chatId = update.getMessage().getChatId();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите валюту:");

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("USD"));
        row1.add(new KeyboardButton("EUR"));
        row1.add(new KeyboardButton("RUB"));
        row1.add(new KeyboardButton("CNY"));

        keyboard.add(row1);

        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);

        try {
            bot.execute(message);
            log.info("Currency selection sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
