package easy.currencytgbot.Bot.Domain.Factory;

import easy.currencytgbot.Bot.infrastructure.Commands.*;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import org.springframework.stereotype.Component;

@Component
public class CommandFactory {
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
