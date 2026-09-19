---
description: Clone the repository and build the expansion jar.
---

# Building from source

## What you need

Git, a JDK to run Gradle with, and nothing else—the Gradle wrapper fetches Gradle itself, and the build provisions the Java 16 JDK it compiles with.

## Clone and build

```bash
git clone https://github.com/Wyne10/DateTimeFormatExpansion.git
cd DateTimeFormatExpansion
./gradlew build
```

The expansion jar lands in `build/libs/DateTimeFormatExpansion-<version>.jar`. Copy it to `plugins/PlaceholderAPI/expansions/` and run `/papi reload`.

The version lives in `gradle.properties`. The build compiles against the Paper 1.16.5 and PlaceholderAPI 2.12.2 APIs, and bundles no libraries: the expansion uses the Apache Commons Lang that the server already ships.
