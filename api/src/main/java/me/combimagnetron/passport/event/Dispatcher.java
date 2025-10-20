package me.combimagnetron.passport.event;

import me.combimagnetron.passport.concurrency.LifeCycle;
import me.combimagnetron.passport.concurrency.Scheduler;
import me.combimagnetron.passport.util.Duration;

import java.util.concurrent.TimeUnit;

public sealed interface Dispatcher<T extends Event, V extends EventSubscription<T>> permits Dispatcher.SimpleDispatcher {
    SimpleDispatcher<? extends EventSubscription<?>> SIMPLE = new SimpleDispatcher<>();

    static <V extends EventSubscription<Event>> Dispatcher<Event, V> dispatcher() {
        return (SimpleDispatcher<V>) SIMPLE;
    }

    void postCancellable(Class<T> type, T event);

    LifeCycle postAsync(Class<T> type, T event);

    void post(Class<T> type, T event);

    void post(T event);

    EventSubscriptionManager<? extends EventSubscription<T>> manager();

    final class SimpleDispatcher<V extends EventSubscription<Event>> implements Dispatcher<Event, V> {
        private final EventSubscriptionManager<EventSubscription<Event>> subscriptionManager = new EventSubscriptionManager.Impl();

        @Override
        public void postCancellable(Class<Event> type, Event event) {

        }

        @Override
        public LifeCycle postAsync(Class<Event> type, Event event) {
            return Scheduler.run(() -> post(type, event), Duration.of(0, TimeUnit.SECONDS));
        }

        @Override
        public void post(Class<Event> type, Event event) {
            subscriptionManager.subscriptionMap().values().stream().filter(e -> e.getEventClass() == type).forEach(e -> e.handler().accept(event));
        }

        @Override
        public void post(Event event) {
            post((Class<Event>) event.getClass(), event);
        }

        @Override
        public EventSubscriptionManager<EventSubscription<Event>> manager() {
            return subscriptionManager;
        }

    }

}
