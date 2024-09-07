package me.combimagnetron.passport.event;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public sealed interface EventSubscriptionManager<T extends EventSubscription<? extends Event>> permits EventSubscriptionManager.Impl {

    void subscription(T subscription);

    Collection<T> subscriptions();

    Map<Class<? extends Event>, T> subscriptionMap();

    <V extends Event> void unsubscribe(EventSubscription<V> vEventSubscription);

    final class Impl<T extends EventSubscription<? extends Event>> implements EventSubscriptionManager<T> {
        private final Map<Class<? extends Event>, T> subscriptions = new ConcurrentHashMap<>();

        @Override
        public void subscription(T subscription) {
            subscriptions.put(subscription.getEventClass(), subscription);
        }


        @Override
        public Collection<T> subscriptions() {
            return subscriptions.values();
        }

        @Override
        public Map<Class<? extends Event>, T> subscriptionMap() {
            return subscriptions;
        }

        @Override
        public <V extends Event> void unsubscribe(EventSubscription<V> subscription) {
            subscriptions.remove(subscription.getEventClass(), subscription);
        }


    }

}
