package easy.currencytgbot.Bot.infrastructure.States;

import easy.currencytgbot.Bot.Application.Bot.Bot;
import easy.currencytgbot.Bot.Application.Interfaces.UserState;

public class FromCurrencyState implements UserState {
    @Override
    public void handle(String input, long chatId, Bot bot) {
        bot.getUserStateStorage().setUserFromCurrency(chatId, input);
        bot.getUserStateStorage().setUserState(chatId, "TO_CURRENCY");
        bot.sendMessage(chatId, "Выберите валюту в которую хотите конвертировать:", bot.createCurrencyKeyboard());
    }
}
