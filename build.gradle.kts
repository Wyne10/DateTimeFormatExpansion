plugins {
    id("java")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(16))
}

dependencies {
    compileOnly(libs.paperApi)
    compileOnly(libs.placeholderApi)
}

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name()
}