package me.combimagnetron.passport.logic.action;

import me.combimagnetron.passport.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public record ActionWrapper(String name, @Nullable Class<Event> eventClass, Action action, Collection<Argument<?>> arguments) {

    public static ActionWrapper of(String name, @Nullable Class<Event> eventClass, Action action, Collection<Argument<?>> arguments) {
        return new ActionWrapper(name, eventClass, action, arguments);
    }

}
