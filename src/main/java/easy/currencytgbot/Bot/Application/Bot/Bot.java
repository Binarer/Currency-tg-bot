package easy.currencytgbot.Bot.Application.Bot;

import easy.currencytgbot.Bot.Application.Interfaces.ICryptoService;
import easy.currencytgbot.Bot.Application.Interfaces.ICurrencyConversionService;
import easy.currencytgbot.Bot.Application.Interfaces.ICurrencyService;
import easy.currencytgbot.Bot.Application.Interfaces.UserState;
import easy.currencytgbot.Bot.Domain.Events.CurrencyRateObserver;
import easy.currencytgbot.Bot.Domain.Events.CurrencyRateUpdatedEvent;
import easy.currencytgbot.Bot.Domain.Events.ICurrencyRateListener;
import easy.currencytgbot.Bot.Domain.Factory.CommandFactory;
import easy.currencytgbot.Bot.infrastructure.Commands.LoggingCommandDecorator;
import easy.currencytgbot.Bot.infrastructure.Components.BotCommands;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import easy.currencytgbot.Bot.infrastructure.Config.BotConfig;
import easy.currencytgbot.Bot.infrastructure.States.AmountState;
import easy.currencytgbot.Bot.infrastructure.States.FromCurrencyState;
import easy.currencytgbot.Bot.infrastructure.States.ToCurrencyState;
import easy.currencytgbot.Bot.infrastructure.Storage.UserStateStorage;
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

import java.util.*;

/**
 * Основной класс бота для Telegram.
 * Этот класс реализует интерфейсы {@link ICurrencyRateListener} и {@link BotCommands},
 * а также предоставляет методы для обработки обновлений и отправки сообщений.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot implements ICurrencyRateListener, BotCommands {

    private final BotConfig config;
    private final ICurrencyService currencyService;
    @Getter
    private final ICurrencyConversionService currencyConversionService;
    @Getter
    private final ICryptoService cryptoService;
    @Getter
    private final UserStateStorage userStateStorage;
    private final CommandFactory commandFactory;
    private final CurrencyRateObserver currencyRateObserver;
    private final Map<String, UserState> states = new HashMap<>();

    /**
     * Конструктор бота.
     *
     * @param config                    конфигурация бота
     * @param currencyService           сервис для работы с валютами
     * @param currencyConversionService сервис для конвертации валют
     * @param userStateStorage          хранилище состояний пользователей
     * @param cryptoService             сервис для работы с криптовалютами
     * @param commandFactory            фабрика команд
     * @param currencyRateObserver      наблюдатель за курсами валют
     */
    public Bot(BotConfig config, ICurrencyService currencyService, ICurrencyConversionService currencyConversionService, UserStateStorage userStateStorage, ICryptoService cryptoService, CommandFactory commandFactory, CurrencyRateObserver currencyRateObserver) {
        this.config = config;
        this.currencyService = currencyService;
        this.currencyConversionService = currencyConversionService;
        this.userStateStorage = userStateStorage;
        this.cryptoService = cryptoService;
        this.commandFactory = commandFactory;
        this.currencyRateObserver = currencyRateObserver;
        this.currencyRateObserver.addListener(this);
        registerStates();
        setCommands();
    }

    /**
     * Регистрирует состояния пользователей.
     */
    private void registerStates() {
        states.put("FROM_CURRENCY", new FromCurrencyState());
        states.put("TO_CURRENCY", new ToCurrencyState());
        states.put("AMOUNT", new AmountState());
    }

    /**
     * Устанавливает команды бота.
     */
    private void setCommands() {
        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot commands", e);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    /**
     * Обрабатывает полученные обновления.
     *
     * @param update обновление
     */
    @Override
    public void onUpdateReceived(Update update) {
        long chatId = extractChatId(update);
        long userId = extractUserId(update);
        String userName = extractUserName(update);
        String receivedMessage = extractReceivedMessage(update);

        botAnswerUtils(update, receivedMessage, chatId, userName);
    }

    /**
     * Извлекает идентификатор чата из обновления.
     *
     * @param update обновление
     * @return идентификатор чата
     */
    private long extractChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }
        return 0;
    }

    /**
     * Извлекает идентификатор пользователя из обновления.
     *
     * @param update обновление
     * @return идентификатор пользователя
     */
    private long extractUserId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }
        return 0;
    }

    /**
     * Извлекает имя пользователя из обновления.
     *
     * @param update обновление
     * @return имя пользователя
     */
    private String extractUserName(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getFirstName();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getFirstName();
        }
        return null;
    }

    /**
     * Извлекает текст сообщения из обновления.
     *
     * @param update обновление
     * @return текст сообщения
     */
    private String extractReceivedMessage(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData();
        }
        return null;
    }

    /**
     * Обрабатывает ответ бота на основе полученного сообщения.
     *
     * @param update          обновление
     * @param receivedMessage полученное сообщение
     * @param chatId          идентификатор чата
     * @param userName        имя пользователя
     */
    private void botAnswerUtils(Update update, String receivedMessage, long chatId, String userName) {
        try {
            Command command = new LoggingCommandDecorator(commandFactory.createCommand(receivedMessage));
            command.execute(update, this);
        } catch (IllegalArgumentException e) {
            handleMessage(receivedMessage, chatId, userName);
        }
    }

    /**
     * Обрабатывает сообщение на основе текущего состояния пользователя.
     *
     * @param receivedMessage полученное сообщение
     * @param chatId          идентификатор чата
     * @param userName        имя пользователя
     */
    private void handleMessage(String receivedMessage, long chatId, String userName) {
        String state = userStateStorage.getUserState(chatId);
        if (state != null) {
            UserState userState = states.get(state);
            if (userState != null) {
                userState.handle(receivedMessage, chatId, this);
            }
        } else {
            handleCurrencySelection(receivedMessage, chatId);
        }
    }

    /**
     * Обрабатывает выбор валюты и отправляет текущий курс.
     *
     * @param currency валюта
     * @param chatId   идентификатор чата
     */
    private void handleCurrencySelection(String currency, long chatId) {
        String rate = currencyService.getCurrentCurrencyRate(currency);
        sendMessage(chatId, rate);
    }
    /**
     * Отправляет сообщение пользователю.
     *
     * @param chatId идентификатор чата
     * @param text текст сообщения
     */
    public void sendMessage(long chatId, String text) {
        sendMessage(chatId, text, null);
    }
    /**
     * Отправляет сообщение пользователю с указанной клавиатурой.
     *
     * @param chatId идентификатор чата
     * @param text текст сообщения
     * @param markup клавиатура
     */
    public void sendMessage(long chatId, String text, ReplyKeyboardMarkup markup) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(markup)
                .build();
        executeMessage(message);
    }
    /**
     * Выполняет отправку сообщения.
     *
     * @param message сообщение
     */
    private void executeMessage(SendMessage message) {
        try {
            execute(message);
            log.info("Message sent");
        } catch (TelegramApiException e) {
            log.error("Error sending message", e);
        }
    }

    @Override
    public void onCurrencyRateUpdated(CurrencyRateUpdatedEvent event) {
        String message = String.format("Currency rate for %s updated to %.2f", event.getCurrencyCode(), event.getNewRate());
        //TODO: юз наблюдатель позже
    }
}