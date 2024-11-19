package easy.currencytgbot.Bot.infrastructure.Storage;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class UserStateStorage {
    private final Map<Long, String> userStates = new HashMap<>();
    private final Map<Long, String> userFromCurrency = new HashMap<>();
    private final Map<Long, String> userToCurrency = new HashMap<>();
    public void setUserState(long chatId, String state) {
        userStates.put(chatId, state);
    }

    public String getUserState(long chatId) {
        return userStates.get(chatId);
    }

    public void removeUserState(long chatId) {
        userStates.remove(chatId);
    }

    public void setUserFromCurrency(long chatId, String currency) {
        userFromCurrency.put(chatId, currency);
    }

    public String getUserFromCurrency(long chatId) {
        return userFromCurrency.get(chatId);
    }

    public void removeUserFromCurrency(long chatId) {
        userFromCurrency.remove(chatId);
    }

    public void setUserToCurrency(long chatId, String currency) {
        userToCurrency.put(chatId, currency);
    }

    public String getUserToCurrency(long chatId) {
        return userToCurrency.get(chatId);
    }

    public void removeUserToCurrency(long chatId) {
        userToCurrency.remove(chatId);
    }
    public Set<Long> getAllUserChatIds() {
        Set<Long> chatIds = new HashSet<>(userStates.keySet());
        chatIds.addAll(userFromCurrency.keySet());
        chatIds.addAll(userToCurrency.keySet());
        return chatIds;
    }
}
