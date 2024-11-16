package easy.currencytgbot.Bot.infrastructure.Components.Commands;

import easy.currencytgbot.Bot.Application.Bot.Bot;
import easy.currencytgbot.Bot.infrastructure.Components.BotCommands;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class HelpCommand implements Command {
    @Override
    public void execute(Update update, Bot bot) {
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(BotCommands.HELP_TEXT);

        try {
            bot.execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
