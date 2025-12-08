package me.wyne.dtf.format;

import me.wyne.dtf.Args;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Locale;
import java.util.Map;

public final class LocalReformat implements Format {

    @Override
    public @NotNull String format(OfflinePlayer player, Args args, Map<String, String> formats) {
        var dateTimeString = args.get(0);
        var fromFormat = args.get(1);
        var toFormat = args.get(2);
        var fromLocaleString = args.get(3);
        var fromLocale = (!fromLocaleString.isBlank() && !fromLocaleString.equalsIgnoreCase("NOW")) ? Locale.forLanguageTag(fromLocaleString) : Locale.getDefault();
        var toLocaleString = args.get(4);
        var toLocale = (!toLocaleString.isBlank() && !toLocaleString.equalsIgnoreCase("NOW")) ? Locale.forLanguageTag(toLocaleString) : Locale.getDefault();

        DateTimeFormatter fromFormatter = LocalFormat.FORMATTERS.containsKey(fromFormat)
                ? LocalFormat.FORMATTERS.get(fromFormat).withLocale(fromLocale)
                : DateTimeFormatter.ofPattern(formats.getOrDefault(fromFormat, fromFormat), fromLocale);

        DateTimeFormatter toFormatter = LocalFormat.FORMATTERS.containsKey(toFormat)
                ? LocalFormat.FORMATTERS.get(toFormat).withLocale(toLocale)
                : DateTimeFormatter.ofPattern(formats.getOrDefault(toFormat, toFormat), toLocale);

        TemporalAccessor parsed = fromFormatter.parseBest(
                dateTimeString,
                LocalDateTime::from,
                LocalDate::from,
                LocalTime::from
        );

        return toFormatter.format(parsed);
    }

}
