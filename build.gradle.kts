plugins {
    java
    id("application") // Explicitly define the ID to avoid accessor confusion
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

application {
    applicationDefaultJvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
    mainClass.set("Application")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.github.kwhat:jnativehook:2.2.2")
    implementation("org.jline:jline:4.0.0")
    implementation("org.apache.commons:commons-collections4:4.5.0")
}

tasks.test {
    useJUnitPlatform()
}