package me.combimagnetron.passport.event;

import me.combimagnetron.passport.concurrency.LifeCycle;
import me.combimagnetron.passport.concurrency.Scheduler;
import me.combimagnetron.passport.util.Duration;

import java.util.concurrent.TimeUnit;

public sealed interface Dispatcher<T extends Event> permits Dispatcher.SimpleDispatcher {
    SimpleDispatcher<? extends Event> SIMPLE = new SimpleDispatcher<>();

    static <T extends Event> Dispatcher<T> dispatcher() {
        return (SimpleDispatcher<T>) SIMPLE;
    }

    void postCancellable(Class<T> type, T event);

    LifeCycle postAsync(Class<T> type, T event);

    void post(Class<T> type, T event);

    EventSubscriptionManager<? extends EventSubscription<T>> manager();

    final class SimpleDispatcher<T extends Event> implements Dispatcher<T> {
        private final EventSubscriptionManager<? extends EventSubscription<T>> subscriptionManager = new EventSubscriptionManager.Impl<>();

        @Override
        public void postCancellable(Class<T> type, T event) {

        }

        @Override
        public LifeCycle postAsync(Class<T> type, T event) {
            return Scheduler.run(() -> post(type, event), Duration.of(0, TimeUnit.SECONDS));
        }

        @Override
        public void post(Class<T> type, T event) {
            subscriptionManager.subscriptionMap().values().stream().filter(e -> e.getEventClass() == type).forEach(e -> e.handler().accept(event));
        }

        @Override
        public EventSubscriptionManager<? extends EventSubscription<T>> manager() {
            return subscriptionManager;
        }

    }

}
