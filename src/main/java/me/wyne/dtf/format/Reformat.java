package me.wyne.dtf.format;

import me.wyne.dtf.Args;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Locale;
import java.util.Map;

public final class Reformat implements Format {

    @Override
    public @NotNull String format(OfflinePlayer player, Args args, Map<String, String> formats) {
        var dateTimeString = args.get(0);
        var fromFormat = args.get(1);
        var toFormat = args.get(2);
        var fromLocaleString = args.get(3);
        var fromLocale = (!fromLocaleString.isBlank() && !fromLocaleString.equalsIgnoreCase("NOW")) ? Locale.forLanguageTag(fromLocaleString) : Locale.getDefault();
        var toLocaleString = args.get(4);
        var toLocale = (!toLocaleString.isBlank() && !toLocaleString.equalsIgnoreCase("NOW")) ? Locale.forLanguageTag(toLocaleString) : Locale.getDefault();
        var zoneString = args.get(5);
        var zone = (!zoneString.isBlank() && !zoneString.equalsIgnoreCase("NOW")) ? ZoneId.of(zoneString, ZoneId.SHORT_IDS) : ZoneId.systemDefault();

        DateTimeFormatter fromFormatter = ZonedFormat.FORMATTERS.containsKey(fromFormat)
                ? ZonedFormat.FORMATTERS.get(fromFormat).withLocale(fromLocale)
                : DateTimeFormatter.ofPattern(formats.getOrDefault(fromFormat, fromFormat), fromLocale);

        DateTimeFormatter toFormatter = ZonedFormat.FORMATTERS.containsKey(toFormat)
                ? ZonedFormat.FORMATTERS.get(toFormat).withLocale(toLocale)
                : DateTimeFormatter.ofPattern(formats.getOrDefault(toFormat, toFormat), toLocale);

        TemporalAccessor parsed = fromFormatter.parseBest(
                dateTimeString,
                ZonedDateTime::from,
                OffsetDateTime::from,
                LocalDateTime::from,
                LocalDate::from,
                LocalTime::from
        );

        if (parsed instanceof ZonedDateTime zdt && !zoneString.isBlank())
            return toFormatter.format(zdt.withZoneSameInstant(zone));
        else if (parsed instanceof OffsetDateTime odt && !zoneString.isBlank())
            return toFormatter.format(odt.atZoneSameInstant(zone));
        else if (parsed instanceof LocalDateTime ldt && !zoneString.isBlank())
            return toFormatter.format(ldt.atZone(zone));
        else
            return toFormatter.format(parsed);
    }

}
