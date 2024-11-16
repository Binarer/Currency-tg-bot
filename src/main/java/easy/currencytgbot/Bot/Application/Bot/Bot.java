package easy.currencytgbot.Bot.Application.Bot;

import easy.currencytgbot.Bot.Application.Interfaces.ICryptoService;
import easy.currencytgbot.Bot.Application.Interfaces.ICurrencyConversionService;
import easy.currencytgbot.Bot.Application.Interfaces.ICurrencyService;
import easy.currencytgbot.Bot.infrastructure.Components.BotCommands;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import easy.currencytgbot.Bot.infrastructure.Components.Commands.*;
import easy.currencytgbot.Bot.infrastructure.Config.BotConfig;
import easy.currencytgbot.Bot.infrastructure.Storage.UserStateStorage;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot implements BotCommands {
    final BotConfig config;
    final ICurrencyService currencyService;
    final ICurrencyConversionService currencyConversionService;
    @Getter
    final ICryptoService cryptoService;
    @Getter
    final UserStateStorage userStateStorage;
    private final Map<String, Command> commands = new HashMap<>();

    public Bot(BotConfig config, ICurrencyService currencyService, ICurrencyConversionService currencyConversionService, UserStateStorage userStateStorage, ICryptoService cryptoService) {
        this.config = config;
        this.currencyService = currencyService;
        this.currencyConversionService = currencyConversionService;
        this.userStateStorage = userStateStorage;
        this.cryptoService = cryptoService;
        registerCommands();
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }
    private void registerCommands() {
        commands.put("/start", new StartCommand());
        commands.put("/help", new HelpCommand());
        commands.put("/rate", new RateCommand());
        commands.put("/convert", new ConvertCommand());
        commands.put("/crypto", new CryptoCommand());
    }


    @Override
    public String getBotUsername() { return config.getBotName(); }

    @Override
    public String getBotToken() { return config.getToken(); }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        long chatId = 0;
        long userId = 0; //это нам понадобится позже
        String userName = null;
        String receivedMessage;

        //если получено сообщение текстом
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();

            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(update, receivedMessage, chatId, userName);
            }

            //если нажата одна из кнопок бота
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();

            botAnswerUtils(update, receivedMessage, chatId, userName);
        }
    }

    private void botAnswerUtils(Update update, String receivedMessage, long chatId, String userName) {
        Command command = commands.get(receivedMessage);
        if (command != null) {
            command.execute(update, this);
        } else {
            handleMessage(receivedMessage, chatId, userName);
        }
    }

    private void handleMessage(String receivedMessage, long chatId, String userName) {
        String state = userStateStorage.getUserState(chatId);
        if (state != null) {
            switch (state) {
                case "FROM_CURRENCY" -> handleFromCurrencySelection(receivedMessage, chatId);
                case "TO_CURRENCY" -> handleToCurrencySelection(receivedMessage, chatId);
                case "AMOUNT" -> handleAmountInput(receivedMessage, chatId);
            }
        } else {
            handleCurrencySelection(receivedMessage, chatId);
        }
    }

    private void handleFromCurrencySelection(String currency, long chatId) {
        userStateStorage.setUserFromCurrency(chatId, currency);
        userStateStorage.setUserState(chatId, "TO_CURRENCY");

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите валюту в которую хотите конвертировать:");

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
            execute(message);
            log.info("Conversion selection sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void handleToCurrencySelection(String currency, long chatId) {
        userStateStorage.setUserToCurrency(chatId, currency);
        userStateStorage.setUserState(chatId, "AMOUNT");

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Введите сумму:");

        try {
            execute(message);
            log.info("Amount input requested");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void handleAmountInput(String amountStr, long chatId) {
        try {
            double amount = Double.parseDouble(amountStr);
            String fromCurrency = userStateStorage.getUserFromCurrency(chatId);
            String toCurrency = userStateStorage.getUserToCurrency(chatId);

            double convertedAmount = currencyConversionService.convert(fromCurrency, toCurrency, amount);
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(String.format("""
                    💸 Результат конвертации:
                    💵 Из: %.2f %s
                    💶 В: %.2f %s
                    """, amount, fromCurrency, convertedAmount, toCurrency));

            try {
                execute(message);
                log.info("Conversion result sent");
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }

            // Reset state
            userStateStorage.removeUserState(chatId);
            userStateStorage.removeUserFromCurrency(chatId);
            userStateStorage.removeUserToCurrency(chatId);
        } catch (NumberFormatException e) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Ошибка суммы. Введите корректную сумму!");

            try {
                execute(message);
                log.info("Invalid amount input");
            } catch (TelegramApiException ex) {
                log.error(ex.getMessage());
            }
        }
    }

    private void handleCurrencySelection(String currency, long chatId) {
        String rate = currencyService.getCurrentCurrencyRate(currency);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(rate);

        try {
            execute(message);
            log.info("Currency rate sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    public void execute(SendMessage message) throws TelegramApiException {
        super.execute(message);
    }
}