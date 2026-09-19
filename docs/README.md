---
description: >-
  A PlaceholderAPI expansion that formats durations and dates—in any pattern,
  language and time zone, with values taken from other placeholders.
---

# DateTimeFormatExpansion

DateTimeFormatExpansion, `dtf` for short, is a [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI) expansion for showing time. It turns the `5400` ticks another plugin reports into `04:30`, shows next Saturday's event date in each player's language, the time in New York, or a date another plugin prints in a format your players can read.

## Placeholders

| Placeholder                                                            | Shows                                               | See                                           |
| ---------------------------------------------------------------------- | --------------------------------------------------- | --------------------------------------------- |
| `%dtf_duration_<duration>_<format>%`                                   | A length of time, such as a cooldown.               | [Durations](durations.md)                     |
| `%dtf_local_<adjuster>_<time>_<format>_[locale]%`                      | The server's date and time, now or relative to now. | [Local date and time](local-date-and-time.md) |
| `%dtf_zoned_<adjuster>_<time>_<zone>_<format>_[locale]%`               | The same, in a time zone of your choice.            | [Zoned date and time](zoned-date-and-time.md) |
| `%dtf_format_<datetime>_<from>_<to>_[from-locale]_[to-locale]_[zone]%` | A date from elsewhere, written in another format.   | [Reformatting dates](reformatting-dates.md)   |

Arguments in square brackets are optional. Any argument can take its value from another placeholder, written in curly brackets, and any format can be a name you define once in PlaceholderAPI's config—see [Formats and nesting](formats-and-nesting.md).

## Examples

| Placeholder                                        | Shows                                   |
| -------------------------------------------------- | --------------------------------------- |
| `%dtf_duration_90m_HH:mm:ss%`                      | `01:30:00`                              |
| `%dtf_duration_5m30s_WORDSLT%`                     | `5 minutes 30 seconds`                  |
| `%dtf_local_NEXT-MONDAY_NOW_ISO-LOCAL-DATE%`       | `2025-12-15`, on Monday 8 December 2025 |
| `%dtf_zoned_NOW_NOW_UTC_RFC-1123-DATE-TIME%`       | `Mon, 8 Dec 2025 18:04:06 GMT`          |
| `%dtf_format_8-Dec-2025_d-MMM-yyyy_dd.MM.yyyy_en%` | `08.12.2025`                            |

## Installing

|          |                                        |
| -------- | -------------------------------------- |
| Server   | Paper 1.16.5 or later, or a fork of it |
| Java     | 16 or later                            |
| Requires | PlaceholderAPI                         |

### From the eCloud

The expansion is on PlaceholderAPI's [eCloud](https://ecloud.placeholderapi.com/expansions/datetimeformat/) as `DateTimeFormat`, so it installs from the game or the console:

```
/papi ecloud download DateTimeFormat
/papi reload
```

When a new version comes out, the same two commands update it.

### By hand

1. Download the jar from the [latest release](https://github.com/Wyne10/DateTimeFormatExpansion/releases/latest).
2. Put it in `plugins/PlaceholderAPI/expansions/`.
3. Run `/papi reload`.

Either way, check it's loaded with `/papi parse me %dtf_local_NOW_NOW_HH:mm%`, which should print the server's time.

To compile it yourself, see [Building from source](building-from-source.md). DateTimeFormatExpansion is licensed under the MIT License.
