package me.wyne.dtf.format;

import me.wyne.dtf.Args;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface Format {
    @Nullable String format(OfflinePlayer player, Args args, Map<String, String> formats);
}
