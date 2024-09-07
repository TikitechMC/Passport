package me.combimagnetron.passport.event.impl.connection;

import me.combimagnetron.passport.event.Event;
import me.combimagnetron.passport.user.User;

public record UserJoinEvent(User<?> user) implements Event {

    @Override
    public Class<? extends Event> eventType() {
        return UserJoinEvent.class;
    }
}
