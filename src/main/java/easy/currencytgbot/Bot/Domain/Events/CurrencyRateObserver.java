package easy.currencytgbot.Bot.Domain.Events;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class CurrencyRateObserver {
    private List<ICurrencyRateListener> listeners = new ArrayList<>();

    public void addListener(ICurrencyRateListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ICurrencyRateListener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners(CurrencyRateUpdatedEvent event) {
        for (ICurrencyRateListener listener : listeners) {
            listener.onCurrencyRateUpdated(event);
        }
    }
}
