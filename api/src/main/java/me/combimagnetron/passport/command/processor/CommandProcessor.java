package me.combimagnetron.passport.command.processor;

import me.combimagnetron.passport.command.ProcessedCommand;

public interface CommandProcessor {

    ProcessedCommand process(Class<?> commandClass);

}
