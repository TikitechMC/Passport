package me.combimagnetron.passport.event;

import java.util.Arrays;
import java.util.Optional;

public interface Event {

    Class<? extends Event> eventType();

    enum Priority {
        LOWEST((short)0), LOW((short) 1), NORMAL((short) 2), HIGH((short) 3), HIGHEST((short) 4);
        private final Short priority;
        Priority(Short priority) {
            this.priority = priority;
        }

        public short priority() {
            return this.priority;
        }

        public static Optional<Priority> byValue(short value) {
            return Arrays.stream(values()).filter(val -> val.priority == value).findFirst();
        }

    }

    interface FilteredEvent extends Event {

    }

}
