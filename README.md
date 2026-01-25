# DateTimeFormatExpansion (dtf)

Format durations and local/zoned date-times via [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI)

```
%dtf_duration_<duration>_<format>%
%dtf_local_<adjuster>_<time>_<format>_[locale]%
%dtf_zoned_<adjuster>_<time>_<zone>_<format>_[locale]%
%dtf_format_<datetime>_<from>_<to>_[from-locale]_[to-locale]_[zone]%
```

---

# ⚙️ Placeholder Usage

## 1. Duration Formatting

**Syntax:**

```
%dtf_duration_<duration>_<format>%
```

### Duration

Durations can include chained units:

* `1h30m` →  1 hour 30 minutes
* `150s` →  150 seconds
* `5m20s15ms` →  5 minutes 20 seconds 15 milliseconds
* `10t` →  10 ticks

Supported units **case-insensitive** (defaults to ticks):

| Symbol | Meaning      |
|--------|--------------|
| `ms`   | Milliseconds |
| `s`    | Seconds      |
| `m`    | Minutes      |
| `h`    | Hours        |
| `d`    | Days         |
| `t`    | Ticks        |

### Format

Format can be provided directly to placeholder:

```
%dtf_duration_90m_HH:mm:ss%
```

It can also be provided in PlaceholderAPI config.yml (located at .../plugins/PlaceholderAPI/config.yml):

```yaml
expansions:
  dtf:
    example: 'HH:mm:ss'
```

And then addressed by key:

```
%dtf_duration_90m_example%
```

There is also a bunch of predefined formats **case-sensitive** (addressed by key):

| Key                  | Maps to                                                                                                                                                                                         |
|----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `HMS`                | [`01:23:00.000`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html#formatDurationHMS(long))                                         |
| `ISO`                | [`P0Y0M0DT1H23M0.000S`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html#formatDurationISO(long))                                  |
| `WORDS`              | [`0 days 1 hour 23 minutes 0 seconds`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html#formatDurationWords(long,boolean,boolean)) |
| `WORDSL`             | [`1 hours 23 minutess 0 seconds`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html#formatDurationWords(long,boolean,boolean))      |
| `WORDST`             | [`0 days 1 hour 23 minutes`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html#formatDurationWords(long,boolean,boolean))           |
| `WORDSLT`, `WORDSTL` | [`1 hour 23 minutes`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html#formatDurationWords(long,boolean,boolean))                  |

Format is applied using [Apache DurationFormatUtils](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html)

---

## 2. Local DateTime Formatting

**Syntax:**

```
%dtf_local_<adjuster>_<time>_<format>_[locale]%
```

### Adjuster

Shift the current [`LocalDateTime.now()`](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html#now--) before formatting.<br>
You can adjust using [durations](#duration) (can be negative) or predefined adjusters **case-insensitive**:

<details>

<summary>Predefined adjusters</summary>

| Key                          | Maps to                                      |
|------------------------------|----------------------------------------------|
| `NOW`                        | No adjustment — returns the current datetime |
| `PREVIOUS-MONDAY`            | Move to previous Monday                      |
| `PREVIOUS-TUESDAY`           | Move to previous Tuesday                     |
| `PREVIOUS-WEDNESDAY`         | Move to previous Wednesday                   |
| `PREVIOUS-THURSDAY`          | Move to previous Thursday                    |
| `PREVIOUS-FRIDAY`            | Move to previous Friday                      |
| `PREVIOUS-SATURDAY`          | Move to previous Saturday                    |
| `PREVIOUS-SUNDAY`            | Move to previous Sunday                      |
| `PREVIOUS-OR-SAME-MONDAY`    | Previous Monday, or today if Monday          |
| `PREVIOUS-OR-SAME-TUESDAY`   | Previous Tuesday, or today if Tuesday        |
| `PREVIOUS-OR-SAME-WEDNESDAY` | Previous Wednesday, or today if Wednesday    |
| `PREVIOUS-OR-SAME-THURSDAY`  | Previous Thursday, or today if Thursday      |
| `PREVIOUS-OR-SAME-FRIDAY`    | Previous Friday, or today if Friday          |
| `PREVIOUS-OR-SAME-SATURDAY`  | Previous Saturday, or today if Saturday      |
| `PREVIOUS-OR-SAME-SUNDAY`    | Previous Sunday, or today if Sunday          |
| `NEXT-MONDAY`                | Move to next Monday                          |
| `NEXT-TUESDAY`               | Move to next Tuesday                         |
| `NEXT-WEDNESDAY`             | Move to next Wednesday                       |
| `NEXT-THURSDAY`              | Move to next Thursday                        |
| `NEXT-FRIDAY`                | Move to next Friday                          |
| `NEXT-SATURDAY`              | Move to next Saturday                        |
| `NEXT-SUNDAY`                | Move to next Sunday                          |
| `NEXT-OR-SAME-MONDAY`        | Next Monday, or today if Monday              |
| `NEXT-OR-SAME-TUESDAY`       | Next Tuesday, or today if Tuesday            |
| `NEXT-OR-SAME-WEDNESDAY`     | Next Wednesday, or today if Wednesday        |
| `NEXT-OR-SAME-THURSDAY`      | Next Thursday, or today if Thursday          |
| `NEXT-OR-SAME-FRIDAY`        | Next Friday, or today if Friday              |
| `NEXT-OR-SAME-SATURDAY`      | Next Saturday, or today if Saturday          |
| `NEXT-OR-SAME-SUNDAY`        | Next Sunday, or today if Sunday              |
| `MONDAY`                     | Set date to Monday of the current week       |
| `TUESDAY`                    | Set date to Tuesday of the current week      |
| `WEDNESDAY`                  | Set date to Wednesday of the current week    |
| `THURSDAY`                   | Set date to Thursday of the current week     |
| `FRIDAY`                     | Set date to Friday of the current week       |
| `SATURDAY`                   | Set date to Saturday of the current week     |
| `SUNDAY`                     | Set date to Sunday of the current week       |
| `MONTH`                      | First day of the current month               |
| `NEXT-MONTH`                 | First day of next month                      |
| `YEAR`                       | First day of the current year                |
| `NEXT_YEAR`                  | First day of next year                       |

</details>

### Time

Sets time.<br>
Use either **case-insensitive**:

* `NOW` → keep system time
* `HH:mm:ss` → override time (parsed using [`LocalTime.parse(CharSequence text)`](https://docs.oracle.com/javase/8/docs/api/java/time/LocalTime.html#parse-java.lang.CharSequence-))

Examples:

```
%dtf_local_NEXT-MONDAY_15:00_ISO-LOCAL-DATE%
```

→ Returns next Monday at 15:00 as ISO date.

```
%dtf_local_-30m_NOW_ISO-DATE-TIME%
```

→ Returns current time minus 30 minutes as ISO date-time.

### Format

Format is parsed as described in [Duration Formatting](#1-duration-formatting), but predefined formatters are different **case-sensitive**:

| Key                   | Maps to                                                                                                                            |
|-----------------------|------------------------------------------------------------------------------------------------------------------------------------|
| `BASIC-ISO-DATE`      | [`yyyyMMdd`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#BASIC_ISO_DATE)                     |
| `ISO-LOCAL-DATE`      | [`yyyy-MM-dd`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_LOCAL_DATE)                   |
| `ISO-DATE`            | [`yyyy-MM-dd`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_DATE)                         |
| `ISO-LOCAL-TIME`      | [`HH:mm:ss.SSS`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_LOCAL_TIME)                 |
| `ISO-TIME`            | [`HH:mm:ss.SSS`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_TIME)                       |
| `ISO-LOCAL-DATE-TIME` | [`yyyy-MM-ddTHH:mm:ss.SSS`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_LOCAL_DATE_TIME) |
| `ISO-DATE-TIME`       | [`yyyy-MM-ddTHH:mm:ss.SSS`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_DATE_TIME)       |
| `ISO-ORDINAL-DATE`    | [`yyyy-D`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_ORDINAL_DATE)                     |
| `ISO-WEEK-DATE`       | [`yyyy-Ww-e`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_WEEK_DATE)                     |

### Locale

You can also specify optional formatter locale as [IETF BCP 47 language tag](https://en.wikipedia.org/wiki/IETF_language_tag) (parsed using [`Locale.forLanguageTag(String languageTag)`](https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html#forLanguageTag-java.lang.String-), defaults to [Locale.getDefault()](https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html#getDefault--))<br><br>
`NOW` or blank → uses [Locale.getDefault()](https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html#getDefault--)

---

## 3. Zoned DateTime Formatting

**Syntax:**

```
%dtf_zoned_<adjuster>_<time>_<zone>_<format>_[locale]%
```

### Adjuster

See [Local DateTime Adjuster](#adjuster)

### Time

See [Local DateTime Time](#time)

### Zone

Sets the current [`ZonedDayTime.now(ZoneId zone)`](https://docs.oracle.com/javase/8/docs/api/java/time/ZonedDateTime.html#now-java.time.ZoneId-) to provided [zone id](https://docs.oracle.com/javase/8/docs/api/java/time/ZoneId.html).<br>
Zone id is defined as described in https://docs.oracle.com/javase/8/docs/api/java/time/ZoneId.html<br>
[`SHORT_IDS`](https://docs.oracle.com/javase/8/docs/api/java/time/ZoneId.html#SHORT_IDS) are valid as well. Examples:

* `NOW` → uses system default timezone
* `UTC`
* `GMT+5`
* `America/New_York`
* `Europe/Berlin`
* `CET`, `EST`, etc.

### Format

Format is parsed as described in [Duration Formatting](#1-duration-formatting), but predefined formatters are different **case-sensitive**:

| Key                    | Maps to                                                                                                                                                        |
|------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `BASIC-ISO-DATE`       | [`yyyyMMdd+HHmmss`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#BASIC_ISO_DATE)                                          |
| `ISO-LOCAL-DATE`       | [`yyyy-MM-dd`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_LOCAL_DATE)                                               |
| `ISO-OFFSET-DATE`      | [`yyyy-MM-dd+HH:mm:ss`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_OFFSET_DATE)                                     |
| `ISO-DATE`             | [`yyyy-MM-dd+HH:mm:ss`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_DATE)                                            |
| `ISO-LOCAL-TIME`       | [`HH:mm:ss.SSS`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_LOCAL_TIME)                                             |
| `ISO-OFFSET-TIME`      | [`HH:mm:ss.SSS+HH:mm:ss`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_OFFSET_TIME)                                   |
| `ISO-TIME`             | [`HH:mm:ss.SSS+HH:mm:ss`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_TIME)                                          |
| `ISO-LOCAL-DATE-TIME`  | [`yyyy-MM-ddTHH:mm:ss.SSS`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_LOCAL_DATE_TIME)                             |
| `ISO-OFFSET-DATE-TIME` | [`yyyy-MM-ddTHH:mm:ss.SSS+HH:mm:ss`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_OFFSET_DATE_TIME)                   |
| `ISO-ZONED-DATE-TIME`  | [`yyyy-MM-ddTHH:mm:ss.SSS+HH:mm:ss[ZoneId/ZoneOffset]`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_ZONED_DATE_TIME) |
| `ISO-DATE-TIME`        | [`yyyy-MM-ddTHH:mm:ss.SSS+HH:mm:ss[ZoneId/ZoneOffset]`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_DATE_TIME)       |
| `ISO-ORDINAL-DATE`     | [`yyyy-D+HH:mm:ss`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_ORDINAL_DATE)                                        |
| `ISO-WEEK-DATE`        | [`yyyy-Ww-e`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_WEEK_DATE)                                                 |
| `ISO-INSTANT`          | [`yyyy-MM-ddTHH:mm:ss.SSSZ`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_INSTANT)                                    |
| `RFC-1123-DATE-TIME`   | [`EEE, dd MMM yyyy HH:mm:ss ZoneId/ZoneOffset`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#RFC_1123_DATE_TIME)          |

### Locale

See [Local DateTime Locale](#locale)

---

## 4. Reformatting

You can also **re**format local and zoned date-times.

**Syntax:**

```
%dtf_format_<datetime>_<from>_<to>_[from-locale]_[to-locale]_[zone]%
```

### Datetime

Expects a date-time string such as:

* Mon, 8 Dec 2025 23:01:16 +0500
* 2025-12-08T22:04:06.034735454+04:00[Asia/Yerevan]
* 2025-12-08
* 22:04:26.733221114
* 8-Dec-2025

### From

Expects a [format](#format-2) matching provided [date-time](#datetime), for example:

* Mon, 8 Dec 2025 23:01:16 +0500 → `RFC-1123-DATE-TIME`
* 2025-12-08T22:04:06.034735454+04:00[Asia/Yerevan] → `ISO-DATE-TIME`
* 2025-12-08 → `ISO-LOCAL-DATE`
* 22:04:26.733221114 → `ISO-LOCAL-TIME`
* 8-Dec-2025 → `d-MMM-yyyy`

### To

Here you can specify any [format](#format-2) as described in [Zoned DateTime Format](#format-2)

### From locale

Expects a [locale](#locale) matching provided [date-time](#datetime) locale (defaults to [Locale.getDefault()](https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html#getDefault--)).

### To locale

Overrides [date-time](#datetime) [locale](#locale) (defaults to [Locale.getDefault()](https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html#getDefault--)).

### Zone

Overrides [time zone](#zone) (keeps original time zone if not specified).

---

# ❕ Notes

* Before formatting anything dtf will set **Bracket** placeholders inside itself, that way you can provide data from other placeholders.

# ✅ Examples

* `%dtf_duration_5m30s_HMS%` → 0:05:30.000
* `%dtf_local_NEXT-MONDAY_NOW_ISO-LOCAL-DATE%` (when 2025.12.08) -> 2025-12-15
* `%dtf_zoned_NOW_NOW_UTC_RFC-1123-DATE-TIME%` -> Mon, 8 Dec 2025 09:50:55 GMT
* `%dtf_zoned_NOW_NOW_NOW_d-MMM-yyyy_ja%` -> 8-12月-2025
* `%dtf_format_{dtf_zoned_NOW_NOW_NET_ISO-DATE-TIME}_ISO-DATE-TIME_RFC-1123-DATE-TIME_NOW_NOW_GMT+5%` ([dtf_zoned](#3-zoned-datetime-formatting) is used for demonstration purposes) → 2025-12-08T22:27:10.333235773+04:00[Asia/Yerevan] → Mon, 8 Dec 2025 23:27:10 +0500
