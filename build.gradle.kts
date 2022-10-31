group = "de.l.oklab.klimawatch"
version = "1.0.0-SNAPSHOT"

plugins {
    id("org.springframework.boot") version("2.7.5")
    id("org.jetbrains.kotlin.plugin.spring") version("1.7.20")
    idea
    kotlin("jvm") version "1.7.20"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.7.5"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.5")
    implementation("org.springframework:spring-web:5.3.23")
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.0-rc2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.14.0-rc2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.0-rc2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0-rc2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0-rc2")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.12")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    implementation(kotlin("stdlib"))
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(mapOf("group" to "org.junit.vintage", "module" to "junit-vintage-engine"))
    }
}

tasks.test {
    useJUnitPlatform()
}