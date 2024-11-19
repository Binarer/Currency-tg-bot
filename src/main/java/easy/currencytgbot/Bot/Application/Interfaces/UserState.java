package easy.currencytgbot.Bot.Application.Interfaces;

import easy.currencytgbot.Bot.Application.Bot.Bot;

public interface UserState {
    void handle(String input, long chatId, Bot bot);
}
