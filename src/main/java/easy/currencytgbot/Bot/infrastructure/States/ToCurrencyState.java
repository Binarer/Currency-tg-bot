package easy.currencytgbot.Bot.infrastructure.States;

import easy.currencytgbot.Bot.Application.Bot.Bot;
import easy.currencytgbot.Bot.Application.Interfaces.UserState;

public class ToCurrencyState implements UserState {
    @Override
    public void handle(String input, long chatId, Bot bot) {
        bot.getUserStateStorage().setUserToCurrency(chatId, input);
        bot.getUserStateStorage().setUserState(chatId, "AMOUNT");
        bot.sendMessage(chatId, "Введите сумму:");
    }
}

