package easy.currencytgbot.Bot.Domain.Factory;

import easy.currencytgbot.Bot.infrastructure.Commands.*;
import easy.currencytgbot.Bot.infrastructure.Components.Command;
import org.springframework.stereotype.Component;

@Component
public class CommandFactory {
    public Command createCommand(String commandName) {
        switch (commandName) {
            case "/start":
                return new StartCommand();
            case "/help":
                return new HelpCommand();
            case "/rate":
                return new RateCommand();
            case "/convert":
                return new ConvertCommand();
            case "/crypto":
                return new CryptoCommand();
            default:
                throw new IllegalArgumentException("Unknown command: " + commandName);
        }
    }
}
