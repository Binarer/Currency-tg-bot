package easy.currencytgbot.Bot.Domain.Factory;

import easy.currencytgbot.Bot.infrastructure.Commands.*;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import org.springframework.stereotype.Component;
/**
 * Фабрика команд для бота.
 * Этот класс используется для создания экземпляров команд на основе их имен.
 *
 * @author Минаков Эдуард
 * @version 1.0
 * @since 2024-11-21
 */
@Component
public class CommandFactory {

    /**
     * Создает команду на основе ее имени.
     *
     * @param commandName имя команды
     * @return экземпляр команды
     * @throws IllegalArgumentException если имя команды неизвестно
     */
    public Command createCommand(String commandName) {
        return switch (commandName) {
            case "/start" -> new StartCommand();
            case "/help" -> new HelpCommand();
            case "/rate" -> new RateCommand();
            case "/convert" -> new ConvertCommand();
            case "/crypto" -> new CryptoCommand();
            default -> throw new IllegalArgumentException("Unknown command: " + commandName);
        };
    }
}