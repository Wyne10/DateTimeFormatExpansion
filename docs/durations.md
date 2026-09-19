---
description: >-
  %dtf_duration_…% turns a length of time—a cooldown, the time left on an
  effect—into text: a pattern such as HH:mm:ss, or words.
---

# Durations

```
%dtf_duration_<duration>_<format>%
```

Formats a length of time. The usual source is another placeholder that returns a number, passed in through [brackets](formats-and-nesting.md#using-other-placeholders):

```
%dtf_duration_{effect_vip-day_remaining}_HH:mm:ss%
```

## Duration

One or more amounts, each followed by a unit. Units are case-insensitive:

| Unit | Meaning      | Example  |
| ---- | ------------ | -------- |
| `ms` | milliseconds | `1500ms` |
| `s`  | seconds      | `150s`   |
| `m`  | minutes      | `90m`    |
| `h`  | hours        | `1h30m`  |
| `d`  | days         | `2d12h`  |
| `t`  | ticks        | `5400t`  |

The amounts are added up, so `5m20s15ms` is 5 minutes, 20 seconds and 15 milliseconds. A number with no unit counts as **ticks**, which suits plugins that report time in ticks: `5400` is 4 minutes and 30 seconds. A tick is 50 milliseconds, so `10t` is half a second.

When another placeholder returns a bare number of seconds or milliseconds, add the unit after the brackets: `{plugin_cooldown}s`.

A minus sign is ignored—`-5m` is five minutes. Text with no number in it at all isn't a duration, and the placeholder shows that text unchanged.

## Format

Either a pattern or the key of a predefined format.

### Patterns

Patterns use the letters of [Apache Commons `DurationFormatUtils`](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/time/DurationFormatUtils.html), which are **not** the same as the date-time letters on the other pages:

| Letter | Unit         |
| ------ | ------------ |
| `y`    | years        |
| `M`    | months       |
| `d`    | days         |
| `H`    | hours        |
| `m`    | minutes      |
| `s`    | seconds      |
| `S`    | milliseconds |

Repeat a letter to pad with zeros: `HH` shows `01`, `H` shows `1`. Put text in single quotes to show it as written: `m'm' s's'` shows `4m 30s`.

The largest unit in the pattern takes everything above it. Without `d`, hours keep counting past 24:

| Placeholder                         | Shows      |
| ----------------------------------- | ---------- |
| `%dtf_duration_90m_HH:mm:ss%`       | `01:30:00` |
| `%dtf_duration_30h_HH:mm:ss%`       | `30:00:00` |
| `%dtf_duration_30h_d'd' H'h' m'm'%` | `1d 6h 0m` |

A pattern can't contain `_`, which separates the placeholder's arguments. Put such a pattern in the config and use its name instead—see [Named formats](formats-and-nesting.md#named-formats).

### Predefined formats

These keys are case-sensitive. The examples show 1 hour and 23 minutes:

| Key                  | Shows                                |
| -------------------- | ------------------------------------ |
| `HMS`                | `01:23:00.000`                       |
| `ISO`                | `P0Y0M0DT1H23M0.000S`                |
| `WORDS`              | `0 days 1 hour 23 minutes 0 seconds` |
| `WORDSL`             | `1 hour 23 minutes 0 seconds`        |
| `WORDST`             | `0 days 1 hour 23 minutes`           |
| `WORDSLT`, `WORDSTL` | `1 hour 23 minutes`                  |

In the `WORDS` family, `L` drops the zero units at the start and `T` drops those at the end. The words are always English; for another language, write a pattern with the words in quotes.

## Examples

| Placeholder                                         | Shows                                    |
| --------------------------------------------------- | ---------------------------------------- |
| `%dtf_duration_5m30s_HMS%`                          | `00:05:30.000`                           |
| `%dtf_duration_5400_mm:ss%`                         | `04:30`                                  |
| `%dtf_duration_10t_ss.SSS%`                         | `00.500`                                 |
| `%dtf_duration_{effect_vip-day_remaining}_WORDSLT%` | `1 hour 23 minutes`, with that long left |
