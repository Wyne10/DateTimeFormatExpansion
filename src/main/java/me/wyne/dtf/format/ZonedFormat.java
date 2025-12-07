package me.wyne.dtf.format;

import me.wyne.dtf.Args;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Map;

public final class ZonedFormat implements Format {

    public static final Map<String, DateTimeFormatter> STATIC_FORMATTERS = Map.ofEntries(
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
        var adjuster = args.get(0);
        var time = args.get(1);
        var zone = args.get(2);
        var format = args.get(3);

        var dateTime = ZonedDateTime.now(zone.equalsIgnoreCase("NOW") ? ZoneId.systemDefault() : ZoneId.of(zone, ZoneId.SHORT_IDS));
        var adjusted = dateTime.with(LocalFormat.STATIC_ADJUSTERS.get(adjuster));
        if (!time.equalsIgnoreCase("NOW"))
            adjusted = adjusted.with(LocalTime.parse(time));

        if (STATIC_FORMATTERS.containsKey(format)) {
            return adjusted.format(STATIC_FORMATTERS.get(format));
        } else return adjusted.format(DateTimeFormatter.ofPattern(formats.getOrDefault(format, format)));
    }

}
