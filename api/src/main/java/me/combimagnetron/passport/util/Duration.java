package me.combimagnetron.passport.util;

import java.util.concurrent.TimeUnit;

public record Duration(long time, TimeUnit unit) {

    public static Duration of(long time, TimeUnit unit) {
        return new Duration(time, unit);
    }

}
