package easy.currencytgbot.Bot.infrastructure.States;

import easy.currencytgbot.Bot.Application.Bot.Bot;
import easy.currencytgbot.Bot.Application.Interfaces.UserState;

public class AmountState implements UserState {
    @Override
    public void handle(String input, long chatId, Bot bot) {
        try {
            double amount = Double.parseDouble(input);
            String fromCurrency = bot.getUserStateStorage().getUserFromCurrency(chatId);
            String toCurrency = bot.getUserStateStorage().getUserToCurrency(chatId);

            double convertedAmount = bot.getCurrencyConversionService().convert(fromCurrency, toCurrency, amount);
            bot.sendMessage(chatId, String.format("""
                    💸 Результат конвертации:
                    💵 Из: %.2f %s
                    💶 В: %.2f %s
                    """, amount, fromCurrency, convertedAmount, toCurrency));

            // Reset state
            bot.getUserStateStorage().removeUserState(chatId);
            bot.getUserStateStorage().removeUserFromCurrency(chatId);
            bot.getUserStateStorage().removeUserToCurrency(chatId);
        } catch (NumberFormatException e) {
            bot.sendMessage(chatId, "Ошибка суммы. Введите корректную сумму!");
        }
    }
}
