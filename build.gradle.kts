plugins {
    id("java")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(16))
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.12.2")
}

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name()
}