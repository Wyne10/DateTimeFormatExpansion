---
description: >-
  %dtf_local_…% shows the server's current date and time—or a moment relative to
  it, such as next Monday at 15:00—in any pattern and language.
---

# Local date and time

```
%dtf_local_<adjuster>_<time>_<format>_[locale]%
```

Takes the current date and time in the server's time zone, moves it with `<adjuster>`, sets the time of day with `<time>`, and formats the result. For a fixed time zone rather than the server's, use [`%dtf_zoned_…%`](zoned-date-and-time.md).

```
%dtf_local_NEXT-MONDAY_15:00_ISO-LOCAL-DATE-TIME%
```

shows next Monday at 15:00, such as `2025-12-15T15:00:00`.

## Adjuster

Moves the date. Adjusters are case-insensitive, and keep the time of day—set `<time>` to change that.

| Adjuster                           | Moves to                                                                                                        |
| ---------------------------------- | --------------------------------------------------------------------------------------------------------------- |
| `NOW`                              | Nowhere: today.                                                                                                 |
| A duration, such as `1d` or `-30m` | That far into the future, or the past with a leading `-`. Uses the units of [durations](durations.md#duration). |
| `NEXT-<DAY>`                       | The next such weekday, never today: `NEXT-MONDAY`.                                                              |
| `NEXT-OR-SAME-<DAY>`               | The next such weekday, or today if it is one.                                                                   |
| `PREVIOUS-<DAY>`                   | The previous such weekday, never today.                                                                         |
| `PREVIOUS-OR-SAME-<DAY>`           | The previous such weekday, or today if it is one.                                                               |
| `<DAY>`                            | That weekday in the current week, which starts on Monday: `SUNDAY`.                                             |
| `MONTH`                            | The first day of this month.                                                                                    |
| `NEXT-MONTH`                       | The first day of next month.                                                                                    |
| `YEAR`                             | The first day of this year.                                                                                     |
| `NEXT-YEAR`                        | The first day of next year.                                                                                     |

`<DAY>` is `MONDAY`, `TUESDAY`, `WEDNESDAY`, `THURSDAY`, `FRIDAY`, `SATURDAY` or `SUNDAY`.

An adjuster that is none of these, and has no number in it, is ignored: the date isn't moved.

## Time

`NOW` keeps the current time of day. Anything else sets it, as `HH:mm` or `HH:mm:ss` on the 24-hour clock: `15:00`, `00:00`, `23:59:59`.

## Format

Either a pattern or the key of a predefined format.

### Patterns

Patterns use the letters of Java's [`DateTimeFormatter`](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#patterns). The common ones:

| Letters        | Shows                           | Example            |
| -------------- | ------------------------------- | ------------------ |
| `yyyy`         | Year                            | `2025`             |
| `MM`           | Month number                    | `12`               |
| `MMM` / `MMMM` | Month name, short or full       | `Dec` / `December` |
| `dd` / `d`     | Day of the month, padded or not | `08` / `8`         |
| `EEE` / `EEEE` | Weekday name, short or full     | `Mon` / `Monday`   |
| `HH`           | Hour, 24-hour clock             | `22`               |
| `hh` and `a`   | Hour, 12-hour clock, and AM/PM  | `10` and `PM`      |
| `mm`           | Minutes                         | `04`               |
| `ss`           | Seconds                         | `06`               |

Put text in single quotes to show it as written: `HH'h'mm` shows `22h04`.

A pattern can't contain `_`, which separates the placeholder's arguments. Put such a pattern in the config and use its name instead—see [Named formats](formats-and-nesting.md#named-formats). That's also handy for long patterns you use in several places.

### Predefined formats

These keys are case-sensitive. The examples show 8 December 2025, 22:04:06:

| Key                   | Shows                           |
| --------------------- | ------------------------------- |
| `BASIC-ISO-DATE`      | `20251208`                      |
| `ISO-LOCAL-DATE`      | `2025-12-08`                    |
| `ISO-DATE`            | `2025-12-08`                    |
| `ISO-LOCAL-TIME`      | `22:04:06.034735454`            |
| `ISO-TIME`            | `22:04:06.034735454`            |
| `ISO-LOCAL-DATE-TIME` | `2025-12-08T22:04:06.034735454` |
| `ISO-DATE-TIME`       | `2025-12-08T22:04:06.034735454` |
| `ISO-ORDINAL-DATE`    | `2025-342`                      |
| `ISO-WEEK-DATE`       | `2025-W50-1`                    |

The fraction of a second has as many digits as the clock provides. When `<time>` is set, there is no fraction and it isn't shown: `15:00:00`.

## Locale

The language for month and weekday names, as an [IETF language tag](https://en.wikipedia.org/wiki/IETF_language_tag): `en`, `de`, `ru`, `pt-BR`. Leave it out, or write `NOW`, to use the server's default language.

## Examples

With this in PlaceholderAPI's config:

```yaml
expansions:
  dtf:
    event: 'EEEE, d MMMM HH:mm'
```

| Placeholder                                  | Shows, on Monday 8 December 2025 at 22:04 |
| -------------------------------------------- | ----------------------------------------- |
| `%dtf_local_NOW_NOW_HH:mm%`                  | `22:04`                                   |
| `%dtf_local_NEXT-MONDAY_NOW_ISO-LOCAL-DATE%` | `2025-12-15`                              |
| `%dtf_local_SUNDAY_20:00_event_en%`          | `Sunday, 14 December 20:00`               |
| `%dtf_local_NOW_NOW_event_de%`               | `Montag, 8 Dezember 22:04`                |
| `%dtf_local_MONTH_00:00_dd.MM.yyyy%`         | `01.12.2025`                              |
| `%dtf_local_-30m_NOW_HH:mm%`                 | `21:34`                                   |
