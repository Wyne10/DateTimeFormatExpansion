package me.wyne.dtf.format;

import me.wyne.dtf.Args;
import me.wyne.dtf.duration.Durations;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Locale;
import java.util.Map;

public final class ZonedFormat implements Format {

    public static final Map<String, DateTimeFormatter> FORMATTERS = Map.ofEntries(
            Map.entry("BASIC-ISO-DATE", DateTimeFormatter.BASIC_ISO_DATE),
            Map.entry("ISO-LOCAL-DATE", DateTimeFormatter.ISO_LOCAL_DATE),
            Map.entry("ISO-OFFSET-DATE", DateTimeFormatter.ISO_OFFSET_DATE),
            Map.entry("ISO-DATE", DateTimeFormatter.ISO_DATE),
            Map.entry("ISO-LOCAL-TIME", DateTimeFormatter.ISO_LOCAL_TIME),
            Map.entry("ISO-OFFSET-TIME", DateTimeFormatter.ISO_OFFSET_TIME),
            Map.entry("ISO-TIME", DateTimeFormatter.ISO_TIME),
            Map.entry("ISO-LOCAL-DATE-TIME", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            Map.entry("ISO-OFFSET-DATE-TIME", DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            Map.entry("ISO-ZONED-DATE-TIME", DateTimeFormatter.ISO_ZONED_DATE_TIME),
            Map.entry("ISO-DATE-TIME", DateTimeFormatter.ISO_DATE_TIME),
            Map.entry("ISO-ORDINAL-DATE", DateTimeFormatter.ISO_ORDINAL_DATE),
            Map.entry("ISO-WEEK-DATE", DateTimeFormatter.ISO_WEEK_DATE),
            Map.entry("ISO-INSTANT", DateTimeFormatter.ISO_INSTANT),
            Map.entry("RFC-1123-DATE-TIME", DateTimeFormatter.RFC_1123_DATE_TIME)
    );

    @Override
    public @NotNull String format(OfflinePlayer player, Args args, Map<String, String> formats) {
        var adjuster = args.get(0).toUpperCase(Locale.ENGLISH);
        var time = args.get(1);
        var zone = args.get(2);
        var format = args.get(3);
        var localeString = args.get(4);
        var locale = (!localeString.isBlank() && !localeString.equalsIgnoreCase("NOW")) ? Locale.forLanguageTag(localeString) : Locale.getDefault();

        var dateTime = ZonedDateTime.now(zone.equalsIgnoreCase("NOW") ? ZoneId.systemDefault() : ZoneId.of(zone, ZoneId.SHORT_IDS));
        if (LocalFormat.ADJUSTERS.containsKey(adjuster)) {
            dateTime = dateTime.with(LocalFormat.ADJUSTERS.get(adjuster));
        } else {
            var timeSpan = Durations.getTimeSpan(adjuster);
            if (adjuster.startsWith("-"))
                dateTime = dateTime.minus(timeSpan.getMillis(), ChronoUnit.MILLIS);
            else
                dateTime = dateTime.plus(timeSpan.getMillis(), ChronoUnit.MILLIS);
        }
        if (!time.equalsIgnoreCase("NOW"))
            dateTime = dateTime.with(LocalTime.parse(time));

        if (FORMATTERS.containsKey(format)) {
            return dateTime.format(FORMATTERS.get(format).withLocale(locale));
        } else return dateTime.format(DateTimeFormatter.ofPattern(formats.getOrDefault(format, format), locale));
    }

}
