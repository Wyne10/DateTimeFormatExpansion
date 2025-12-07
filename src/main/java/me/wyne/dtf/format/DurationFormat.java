package me.wyne.dtf.format;

import me.wyne.dtf.Args;
import me.wyne.dtf.duration.Durations;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public final class DurationFormat implements Format {

    public static final Set<String> STATIC_FORMATS = Set.of(
            "HMS",
            "ISO",
            "WORDS",
            "WORDSL",
            "WORDST",
            "WORDSLT"
    );

    @Override
    public @Nullable String format(OfflinePlayer player, Args args, Map<String, String> formats) {
        var durationString = args.get(0);
        var duration = Durations.getTimeSpan(durationString);
        var format = args.get(1);

        if (STATIC_FORMATS.contains(format)) {
            switch (format) {
                case "HMS": return DurationFormatUtils.formatDurationHMS(duration.getMillis());
                case "ISO": return DurationFormatUtils.formatDurationISO(duration.getMillis());
                case "WORDS": return DurationFormatUtils.formatDurationWords(duration.getMillis(), false, false);
                case "WORDSL": return DurationFormatUtils.formatDurationWords(duration.getMillis(), true, false);
                case "WORDST": return DurationFormatUtils.formatDurationWords(duration.getMillis(), false, true);
                case "WORDSLT", "WORDSTL": return DurationFormatUtils.formatDurationWords(duration.getMillis(), true, true);
            }
        } else return DurationFormatUtils.formatDuration(duration.getMillis(), formats.getOrDefault(format, format));

        return null;
    }

}
