package me.combimagnetron.passport.user;

import net.kyori.adventure.audience.Audience;

public interface ExtendableUser<T extends Audience> {

    UserHolder<T> holder();

}
