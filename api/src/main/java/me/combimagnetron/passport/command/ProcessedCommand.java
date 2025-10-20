package me.combimagnetron.passport.command;

import me.combimagnetron.passport.util.condition.Condition;
import org.jetbrains.annotations.Nullable;

public interface ProcessedCommand {

    Format format();

    void execute();

    @Nullable Condition condition();

}
