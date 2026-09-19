---
description: >-
  How dtf reads its arguments, naming formats in PlaceholderAPI's config,
  feeding in values from other placeholders, and what happens when an argument
  is wrong.
---

# Formats and nesting

## How arguments are read

Everything after `%dtf_` is split on `_`: the first part picks the placeholder—`duration`, `local`, `zoned` or `format`, in any case—and the rest are its arguments, in order. Arguments in square brackets on the other pages are optional and can be left off the end. Spaces inside arguments are fine.

An `_` inside [curly brackets](formats-and-nesting.md#using-other-placeholders) doesn't split anything. Everywhere else it does, so a format written straight into the placeholder can't contain one—give it a [name](formats-and-nesting.md#named-formats) instead.

## Named formats

Any pattern can be given a name in PlaceholderAPI's config, `plugins/PlaceholderAPI/config.yml`, and used by that name wherever a format goes—in all four placeholders:

```yaml
expansions:
  dtf:
    clock: 'HH:mm:ss'
    event: 'EEEE, d MMMM HH:mm'
    cooldown: "m'm' s's'"
```

```
%dtf_duration_90s_cooldown%               → 1m 30s
%dtf_local_NEXT-SATURDAY_18:00_event_en%  → Saturday, 13 December 18:00
```

The names are read when the expansion loads, so run `/papi reload` after changing them. Use names that don't clash with a predefined key such as `ISO` or `HMS`: those always win.

Named formats are the only way to use a pattern with `_` in it, and keep long patterns you use in several places in one spot.

## Using other placeholders

Write another placeholder in curly brackets—without its `%` signs—as an argument, and `dtf` fills it in for the same player before reading it:

```
%dtf_duration_{effect_vip-day_remaining}_HH:mm:ss%
%dtf_duration_{plugin_cooldown}s_mm:ss%
%dtf_format_{someplugin_last_login}_ISO-DATE-TIME_clock%
```

The arguments are split first and filled in after, so a bracket placeholder is always exactly one argument—its own underscores, and any in the value it returns, stay inside it. A player name such as `Cool_Guy_1` is safe.

An argument can mix brackets with plain text, as `{plugin_cooldown}s` does, and `dtf` placeholders can be nested inside each other, as in the last example on [Reformatting dates](reformatting-dates.md#examples).

## When something is wrong

| Problem                                                                         | Result                                                                                                   |
| ------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------- |
| A duration with no number in it                                                 | The placeholder shows that text as it is.                                                                |
| An adjuster that isn't one, with no number                                      | Ignored; the date isn't moved.                                                                           |
| Anything else: a bad pattern, time, zone, or a date that doesn't match `<from>` | An error in the console, starting `Illegal placeholder %dtf_…%`, and the placeholder is left as written. |

The console error names the placeholder and explains what Java couldn't parse, which is usually enough to find the argument at fault.
