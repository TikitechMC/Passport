package me.combimagnetron.passport.user;

import net.kyori.adventure.audience.Audience;

public record UserHolder<T extends Audience>(User<T> user) {

}
