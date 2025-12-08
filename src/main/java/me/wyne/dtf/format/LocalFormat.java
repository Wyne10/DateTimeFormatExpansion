package me.wyne.dtf.format;

import me.wyne.dtf.Args;
import me.wyne.dtf.duration.Durations;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Locale;
import java.util.Map;

public final class LocalFormat implements Format {

    public static final Map<String, DateTimeFormatter> FORMATTERS = Map.ofEntries(
            Map.entry("BASIC-ISO-DATE", DateTimeFormatter.BASIC_ISO_DATE),
            Map.entry("ISO-LOCAL-DATE", DateTimeFormatter.ISO_LOCAL_DATE),
            Map.entry("ISO-DATE", DateTimeFormatter.ISO_DATE),
            Map.entry("ISO-LOCAL-TIME", DateTimeFormatter.ISO_LOCAL_TIME),
            Map.entry("ISO-TIME", DateTimeFormatter.ISO_TIME),
            Map.entry("ISO-LOCAL-DATE-TIME", DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            Map.entry("ISO-DATE-TIME", DateTimeFormatter.ISO_DATE_TIME),
            Map.entry("ISO-ORDINAL-DATE", DateTimeFormatter.ISO_ORDINAL_DATE),
            Map.entry("ISO-WEEK-DATE", DateTimeFormatter.ISO_WEEK_DATE)
    );

    public static final Map<String, TemporalAdjuster> ADJUSTERS = Map.ofEntries(
            Map.entry("NOW", temporal -> temporal),
            Map.entry("PREVIOUS-MONDAY", TemporalAdjusters.previous(DayOfWeek.MONDAY)),
            Map.entry("PREVIOUS-TUESDAY", TemporalAdjusters.previous(DayOfWeek.TUESDAY)),
            Map.entry("PREVIOUS-WEDNESDAY", TemporalAdjusters.previous(DayOfWeek.WEDNESDAY)),
            Map.entry("PREVIOUS-THURSDAY", TemporalAdjusters.previous(DayOfWeek.THURSDAY)),
            Map.entry("PREVIOUS-FRIDAY", TemporalAdjusters.previous(DayOfWeek.FRIDAY)),
            Map.entry("PREVIOUS-SATURDAY", TemporalAdjusters.previous(DayOfWeek.SATURDAY)),
            Map.entry("PREVIOUS-SUNDAY", TemporalAdjusters.previous(DayOfWeek.SUNDAY)),
            Map.entry("PREVIOUS-OR-SAME-MONDAY", TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
            Map.entry("PREVIOUS-OR-SAME-TUESDAY", TemporalAdjusters.previousOrSame(DayOfWeek.TUESDAY)),
            Map.entry("PREVIOUS-OR-SAME-WEDNESDAY", TemporalAdjusters.previousOrSame(DayOfWeek.WEDNESDAY)),
            Map.entry("PREVIOUS-OR-SAME-THURSDAY", TemporalAdjusters.previousOrSame(DayOfWeek.THURSDAY)),
            Map.entry("PREVIOUS-OR-SAME-FRIDAY", TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY)),
            Map.entry("PREVIOUS-OR-SAME-SATURDAY", TemporalAdjusters.previousOrSame(DayOfWeek.SATURDAY)),
            Map.entry("PREVIOUS-OR-SAME-SUNDAY", TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)),
            Map.entry("NEXT-MONDAY", TemporalAdjusters.next(DayOfWeek.MONDAY)),
            Map.entry("NEXT-TUESDAY", TemporalAdjusters.next(DayOfWeek.TUESDAY)),
            Map.entry("NEXT-WEDNESDAY", TemporalAdjusters.next(DayOfWeek.WEDNESDAY)),
            Map.entry("NEXT-THURSDAY", TemporalAdjusters.next(DayOfWeek.THURSDAY)),
            Map.entry("NEXT-FRIDAY", TemporalAdjusters.next(DayOfWeek.FRIDAY)),
            Map.entry("NEXT-SATURDAY", TemporalAdjusters.next(DayOfWeek.SATURDAY)),
            Map.entry("NEXT-SUNDAY", TemporalAdjusters.next(DayOfWeek.SUNDAY)),
            Map.entry("NEXT-OR-SAME-MONDAY", TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)),
            Map.entry("NEXT-OR-SAME-TUESDAY", TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY)),
            Map.entry("NEXT-OR-SAME-WEDNESDAY", TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY)),
            Map.entry("NEXT-OR-SAME-THURSDAY", TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY)),
            Map.entry("NEXT-OR-SAME-FRIDAY", TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)),
            Map.entry("NEXT-OR-SAME-SATURDAY", TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)),
            Map.entry("NEXT-OR-SAME-SUNDAY", TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)),
            Map.entry("MONDAY", DayOfWeek.MONDAY),
            Map.entry("TUESDAY", DayOfWeek.TUESDAY),
            Map.entry("WEDNESDAY", DayOfWeek.WEDNESDAY),
            Map.entry("THURSDAY", DayOfWeek.THURSDAY),
            Map.entry("FRIDAY", DayOfWeek.FRIDAY),
            Map.entry("SATURDAY", DayOfWeek.SATURDAY),
            Map.entry("SUNDAY", DayOfWeek.SUNDAY),
            Map.entry("MONTH", TemporalAdjusters.firstDayOfMonth()),
            Map.entry("NEXT-MONTH", TemporalAdjusters.firstDayOfNextMonth()),
            Map.entry("YEAR", TemporalAdjusters.firstDayOfYear()),
            Map.entry("NEXT-YEAR", TemporalAdjusters.firstDayOfNextYear())
    );

    @Override
    public @NotNull String format(OfflinePlayer player, Args args, Map<String, String> formats) {
        var adjuster = args.get(0).toUpperCase(Locale.ENGLISH);
        var time = args.get(1);
        var format = args.get(2);
        var localeString = args.get(3);
        var locale = (!localeString.isBlank() && !localeString.equalsIgnoreCase("NOW")) ? Locale.forLanguageTag(localeString) : Locale.getDefault();

        var dateTime = LocalDateTime.now();
        if (ADJUSTERS.containsKey(adjuster)) {
            dateTime = dateTime.with(ADJUSTERS.get(adjuster));
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
