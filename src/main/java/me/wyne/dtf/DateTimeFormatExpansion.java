package me.wyne.dtf;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.wyne.dtf.format.DurationFormat;
import me.wyne.dtf.format.LocalFormat;
import me.wyne.dtf.format.ZonedFormat;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateTimeFormatExpansion extends PlaceholderExpansion {

    private final static DurationFormat DURATION_FORMAT = new DurationFormat();
    private final static LocalFormat LOCAL_FORMAT = new LocalFormat();
    private final static ZonedFormat ZONED_FORMAT = new ZonedFormat();

    private final Map<String, String> formats = new HashMap<>();

    @Override
    public @NotNull String getIdentifier() {
        return "dtf";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Wyne";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean register() {
        if (getConfigSection() == null) return super.register();
        getConfigSection().getKeys(false)
                .forEach(key -> {
                    formats.put(key, getString(key, ""));
                });
        return super.register();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        var args = new Args(PlaceholderAPI.setBracketPlaceholders(player, params), "_");
        var cleanArgs = args.skip(1);

        var type = args.get(0).toUpperCase(Locale.ENGLISH);
        try {
            return switch (type) {
                case "DURATION" -> DURATION_FORMAT.format(player, cleanArgs, formats);
                case "LOCAL" -> LOCAL_FORMAT.format(player, cleanArgs, formats);
                case "ZONED" -> ZONED_FORMAT.format(player, cleanArgs, formats);
                default -> null;
            };
        } catch (Throwable throwable) {
            severe("Illegal placeholder %dtf_" + params + "%", throwable);
        }

        return null;
    }

}
