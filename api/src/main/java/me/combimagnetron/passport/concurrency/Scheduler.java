package me.combimagnetron.passport.concurrency;

import me.combimagnetron.passport.util.Duration;

import java.util.concurrent.*;

public class Scheduler {
    private final static ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(2);

    public static void delayTick(Runnable code) {
        SCHEDULED_EXECUTOR_SERVICE.schedule(code, 50L, TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture<?> repeat(Runnable code, Duration duration) {
        return SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(code, 0L, duration.time(), duration.unit());
    }

    public static LifeCycle loop(Runnable code, Duration interval) {
        return new LifeCycle() {
            private ScheduledFuture<?> future = SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(code, 0L, interval.time(), interval.unit());

            @Override
            public void stop() {
                future.cancel(true);
            }

            @Override
            public void pause() {
                future.cancel(true);
            }

            @Override
            public void resume() {
                future = SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(code, 0L, interval.time(), interval.unit());
            }
        };
    }

    public static LifeCycle run(Runnable code, Duration delay) {
        return new LifeCycle() {
            private ScheduledFuture<?> future = SCHEDULED_EXECUTOR_SERVICE.schedule(code, delay.time(), delay.unit());

            @Override
            public void stop() {
                future.cancel(true);
            }

            @Override
            public void pause() {
                future.cancel(true);
            }

            @Override
            public void resume() {
                future = SCHEDULED_EXECUTOR_SERVICE.schedule(code, 0L, TimeUnit.SECONDS);
            }
        };
    }

    public static <T> T async(Callable<T> runnable) {
        try {
            return SCHEDULED_EXECUTOR_SERVICE.schedule(runnable, 0L, TimeUnit.MICROSECONDS).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
