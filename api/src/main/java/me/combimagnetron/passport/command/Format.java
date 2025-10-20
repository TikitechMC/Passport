package me.combimagnetron.passport.command;

import java.util.Collection;

public interface Format {

    String command();

    Collection<Argument<?>> arguments();

    Collection<ProcessedCommand> subCommands();

}
