# DateTimeFormatExpansion (dtf)

Format durations and local/zoned date-times via [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI)

```
%dtf_duration_<duration>_<format>%
%dtf_local_<adjuster>_<time>_<format>%
%dtf_zoned_<adjuster>_<time>_<zone>_<format>%
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

* `1h30m` = 1 hour 30 minutes
* `150s` = 150 seconds
* `5m20s15ms` = 5m 20s 15ms
* `10t` = 10 ticks

Supported units (defaults to ticks):

| Symbol | Meaning      |
| ------ | ------------ |
| `ms`   | milliseconds |
| `s`    | seconds      |
| `m`    | minutes      |
| `h`    | hours        |
| `d`    | days         |
| `t`    | ticks        |

### Format

Format can be provided as string directly to placeholder:
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

There is also a bunch of predefined formats (addressed by key):

| Key       | Maps to                                                                                                                                                                                         |
|-----------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `HMS`     | [`01:23:00.000`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html#formatDurationHMS(long))                                         |
| `ISO`     | [`P0Y0M0DT1H23M0.000S`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html#formatDurationISO(long))                                  |
| `WORDS`   | [`0 days 1 hour 23 minutes 0 seconds`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html#formatDurationWords(long,boolean,boolean)) |
| `WORDSL`  | [`1 hours 23 minutess 0 seconds`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html#formatDurationWords(long,boolean,boolean))      |
| `WORDST`  | [`0 days 1 hour 23 minutes`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html#formatDurationWords(long,boolean,boolean))           |
| `WORDSLT` | [`1 hour 23 minutes`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html#formatDurationWords(long,boolean,boolean))                  |

Format is applied using [Apache DurationFormatUtils](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html)

---

## 2. Local DateTime Formatting

**Syntax:**

```
%dtf_local_<adjuster>_<time>_<format>%
```

### Local Adjusters

These shift the current `LocalDateTime.now()` before formatting.
Examples:

* `NOW`
* `NEXT-MONDAY`
* `PREVIOUS-FRIDAY`
* `MONDAY`, `TUESDAY`, ... → move to that day of week of current week
* `MONTH` → first day of current month
* `NEXT-MONTH` → first day of next month
* `YEAR` → first day of current year
* `NEXT-YEAR` → first day of next year

### Local Time Argument

Use either:

* `NOW` → keep system time
* `HH:mm:ss` → override time (parsed using [LocalTime#parse](https://docs.oracle.com/javase/8/docs/api/java/time/LocalTime.html#parse-java.lang.CharSequence-))

Example:

```
%dtf_local_NEXT-MONDAY_NOW_ISO-LOCAL-DATE%
```

→ Returns next Monday at current time as ISO date.

### Format

Format is parsed as described in [Duration Formatting](#1-duration-formatting), but predefined formatters are different:

| Key                   | Maps To                                 |
|-----------------------| --------------------------------------- |
| `BASIC-ISO-DATE`      | `DateTimeFormatter.BASIC_ISO_DATE`      |
| `ISO-LOCAL-DATE`      | `DateTimeFormatter.ISO_LOCAL_DATE`      |
| `ISO-DATE`            | `DateTimeFormatter.ISO_DATE`            |
| `ISO-LOCAL-TIME`      | `DateTimeFormatter.ISO_LOCAL_TIME`      |
| `ISO-TIME`            | `DateTimeFormatter.ISO_TIME`            |
| `ISO-LOCAL-DATE-TIME` | `DateTimeFormatter.ISO_LOCAL_DATE_TIME` |
| `ISO-DATE-TIME`       | `DateTimeFormatter.ISO_DATE_TIME`       |
| `ISO-ORDINAL-DATE`    | `DateTimeFormatter.ISO_ORDINAL_DATE`    |
| `ISO-WEEK-DATE`       | `DateTimeFormatter.ISO_WEEK_DATE`       |


---

## 3. Zoned DateTime Formatting

**Syntax:**

```
%dtf_zoned_<adjuster>_<time>_<zone>_<format>%
```

### Zone Examples

* `UTC`
* `America/New_York`
* `Europe/Berlin`
* `CET`, `EST`, etc.
* `NOW` → uses system default timezone

### Adjusters

Uses the same adjusters as `LOCAL`, referencing `LocalFormat.STATIC_ADJUSTERS`.

### Zoned Time Argument

* `NOW` → keep original time
* `HH:mm:ss` → override local time before applying zone

### Static Zoned Formatters

| Name                 |
| -------------------- |
| BASIC-ISO-DATE       |
| ISO-LOCAL-DATE       |
| ISO-OFFSET-DATE      |
| ISO-DATE             |
| ISO-LOCAL-TIME       |
| ISO-OFFSET-TIME      |
| ISO-TIME             |
| ISO-LOCAL-DATE-TIME  |
| ISO-OFFSET-DATE-TIME |
| ISO-ZONED-DATE-TIME  |
| ISO-DATE-TIME        |
| ISO-ORDINAL-DATE     |
| ISO-WEEK-DATE        |
| ISO-INSTANT          |
| RFC-1123-DATE-TIME   |

---

# 📝 Custom Format Configuration

Inside the expansion's config, you may define:

```
my-custom-format: "yyyy-MM-dd HH:mm:ss"
```

Then use:

```
%dtf_local_NOW_NOW_my-custom-format%
```

---

# 📦 Internals Overview

### Format Implementations

* **DurationFormat** – handles duration parsing & formatting
* **LocalFormat** – formats `LocalDateTime`
* **ZonedFormat** – formats `ZonedDateTime`

### Duration Parsing Logic

Defined in `Durations` class:

* Regex: `(\d+)(ms|[smhdt])?` matches all supported segments
* Converts all units into **milliseconds**, then wraps in `TimeSpan`

---

# ✅ Examples

```
%dtf_duration_5m30s_HMS%
%dtf_local_NEXT-MONDAY_NOW_ISO-LOCAL-DATE%
%dtf_zoned_NOW_NOW_UTC_RFC-1123-DATE-TIME%
%dtf_local_NOW_14:00_my-custom-format%
%dtf_duration_1h30m_"mm 'minutes'"%
```

---

This README documents usage, formats, adjusters, and duration syntax based directly on:

* LocalFormat.java fileciteturn0file1
* ZonedFormat.java fileciteturn0file0
* DurationFormat.java fileciteturn0file2
* DateTimeFormatExpansion.java fileciteturn0file4
* Durations.java fileciteturn0file5
