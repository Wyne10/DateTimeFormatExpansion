---
description: >-
  %dtf_format_…% reads a date another plugin produced and writes it again in the
  pattern, language and time zone you want.
---

# Reformatting dates

```
%dtf_format_<datetime>_<from>_<to>_[from-locale]_[to-locale]_[zone]%
```

Parses `<datetime>` with the format `<from>`, and writes it out with the format `<to>`. The date usually comes from another plugin's placeholder, passed in through [brackets](formats-and-nesting.md#using-other-placeholders):

```
%dtf_format_{someplugin_last_login}_ISO-DATE-TIME_dd.MM.yyyy HH:mm%
```

## Arguments

| Argument        | What it is                                                                                                                                                                          |
| --------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `<datetime>`    | The date, time, or date and time to read.                                                                                                                                           |
| `<from>`        | The format `<datetime>` is written in: a pattern, a [predefined key from the zoned page](zoned-date-and-time.md#format), or a [named format](formats-and-nesting.md#named-formats). |
| `<to>`          | The format to write it in, from the same choices.                                                                                                                                   |
| `[from-locale]` | The language `<datetime>` is written in, for month and weekday names. Server default when left out or `NOW`.                                                                        |
| `[to-locale]`   | The language to write names in. Server default when left out or `NOW`.                                                                                                              |
| `[zone]`        | A [zone](zoned-date-and-time.md#zone) to show the date in. Left out, the date keeps the zone it was read with.                                                                      |

To give a later argument while skipping an earlier one, write `NOW` in its place: `…_NOW_NOW_UTC`.

## Matching `<from>` to the date

`<from>` must describe the whole of `<datetime>`:

| `<datetime>`                                        | `<from>`             |
| --------------------------------------------------- | -------------------- |
| `Mon, 8 Dec 2025 23:01:16 +0500`                    | `RFC-1123-DATE-TIME` |
| `2025-12-08T22:04:06.034735454+04:00[Asia/Yerevan]` | `ISO-DATE-TIME`      |
| `2025-12-08`                                        | `ISO-LOCAL-DATE`     |
| `22:04:26.733221114`                                | `ISO-LOCAL-TIME`     |
| `8-Dec-2025`                                        | `d-MMM-yyyy`         |

The date is read as precisely as `<from>` allows: with its zone or offset if it has one, otherwise as a date and time, a date, or a time. `<to>` can only use what was read—a date read without a time can't be written with `HH:mm`, and one read without a zone can't be written with its offset. Asking for more is an error.

## Zone

With `[zone]` given:

* A date read **with** a zone or offset is converted: the same moment, shown in the new zone.
* A date and time read **without** one is taken to be in that zone, and isn't shifted.
* A date alone, or a time alone, ignores it.

`NOW` as the zone means the server's time zone.

## Examples

| Placeholder                                                                                                  | Shows                            |
| ------------------------------------------------------------------------------------------------------------ | -------------------------------- |
| `%dtf_format_8-Dec-2025_d-MMM-yyyy_dd.MM.yyyy_en%`                                                           | `08.12.2025`                     |
| `%dtf_format_2025-12-08_ISO-LOCAL-DATE_EEEE_NOW_en%`                                                         | `Monday`                         |
| `%dtf_format_{dtf_zoned_NOW_NOW_Asia/Yerevan_ISO-DATE-TIME}_ISO-DATE-TIME_RFC-1123-DATE-TIME_NOW_NOW_GMT+5%` | `Mon, 8 Dec 2025 23:04:06 +0500` |

The last one reads the time in Yerevan from another `dtf` placeholder and shows the same moment at GMT+5, an hour later.
