---
description: >-
  %dtf_zoned_…% shows the current date and time in a time zone of your
  choice—with its offset, if you want it.
---

# Zoned date and time

```
%dtf_zoned_<adjuster>_<time>_<zone>_<format>_[locale]%
```

Works like [`%dtf_local_…%`](local-date-and-time.md), with one more argument: the time zone. The current date and time are taken in that zone, then moved and formatted the same way. `<adjuster>`, `<time>` and `<locale>` are exactly as described there.

```
%dtf_zoned_NOW_NOW_America/New_York_HH:mm%
```

shows the time in New York, whatever the server's own zone is.

## Zone

| Zone                                             | Means                                                                                                                                |
| ------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------ |
| `NOW`                                            | The server's time zone.                                                                                                              |
| A region, such as `Europe/Berlin`                | That region's time, daylight saving included. See the [list of zones](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones). |
| An offset, such as `UTC`, `GMT+5` or `UTC-03:00` | A fixed offset from UTC.                                                                                                             |
| A short ID, such as `EST` or `CET`               | Java's [short zone IDs](https://docs.oracle.com/javase/8/docs/api/java/time/ZoneId.html#SHORT_IDS).                                  |

Zones are case-sensitive: `Europe/Berlin`, not `europe/berlin`. An unknown zone is an error—see [When something is wrong](formats-and-nesting.md#when-something-is-wrong).

## Format

Patterns work as on the [local page](local-date-and-time.md#patterns), plus the zone letters of `DateTimeFormatter`: `XXX` for the offset (`+04:00`), `z` for the zone's short name, `VV` for its ID (`Asia/Yerevan`).

The predefined formats include the zone. The examples show 8 December 2025, 22:04:06 in `Asia/Yerevan`:

| Key                    | Shows                                               |
| ---------------------- | --------------------------------------------------- |
| `BASIC-ISO-DATE`       | `20251208+0400`                                     |
| `ISO-LOCAL-DATE`       | `2025-12-08`                                        |
| `ISO-OFFSET-DATE`      | `2025-12-08+04:00`                                  |
| `ISO-DATE`             | `2025-12-08+04:00`                                  |
| `ISO-LOCAL-TIME`       | `22:04:06.034735454`                                |
| `ISO-OFFSET-TIME`      | `22:04:06.034735454+04:00`                          |
| `ISO-TIME`             | `22:04:06.034735454+04:00`                          |
| `ISO-LOCAL-DATE-TIME`  | `2025-12-08T22:04:06.034735454`                     |
| `ISO-OFFSET-DATE-TIME` | `2025-12-08T22:04:06.034735454+04:00`               |
| `ISO-ZONED-DATE-TIME`  | `2025-12-08T22:04:06.034735454+04:00[Asia/Yerevan]` |
| `ISO-DATE-TIME`        | `2025-12-08T22:04:06.034735454+04:00[Asia/Yerevan]` |
| `ISO-ORDINAL-DATE`     | `2025-342+04:00`                                    |
| `ISO-WEEK-DATE`        | `2025-W50-1+04:00`                                  |
| `ISO-INSTANT`          | `2025-12-08T18:04:06.034735454Z`, always in UTC     |
| `RFC-1123-DATE-TIME`   | `Mon, 8 Dec 2025 22:04:06 +0400`                    |

## Examples

| Placeholder                                       | Shows                          |
| ------------------------------------------------- | ------------------------------ |
| `%dtf_zoned_NOW_NOW_UTC_RFC-1123-DATE-TIME%`      | `Mon, 8 Dec 2025 18:04:06 GMT` |
| `%dtf_zoned_NOW_NOW_Europe/Berlin_HH:mm%`         | `19:04`                        |
| `%dtf_zoned_NOW_NOW_NOW_d-MMM-yyyy_ja%`           | `8-12月-2025`                   |
| `%dtf_zoned_NEXT-SATURDAY_18:00_UTC_ISO-INSTANT%` | `2025-12-13T18:00:00Z`         |
