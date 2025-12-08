package me.wyne.dtf;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.wyne.dtf.format.DurationFormat;
import me.wyne.dtf.format.Format;
import me.wyne.dtf.format.LocalFormat;
import me.wyne.dtf.format.ZonedFormat;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateTimeFormatExpansion extends PlaceholderExpansion {

    private final static Map<String, Format> FORMATTERS = Map.ofEntries(
            Map.entry("DURATION", new DurationFormat()),
            Map.entry("LOCAL", new LocalFormat()),
            Map.entry("ZONED", new ZonedFormat())
    );

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
            FORMATTERS.get(type).format(player, cleanArgs, formats);
        } catch (Throwable throwable) {
            severe("Illegal placeholder %dtf_" + params + "%", throwable);
        }

        return null;
    }

}
