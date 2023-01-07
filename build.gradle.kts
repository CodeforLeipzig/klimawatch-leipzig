group = "de.l.oklab.klimawatch"
version = "1.0.0-SNAPSHOT"

plugins {
    id ("org.jetbrains.kotlin.plugin.noarg") version ("1.8.0")
    id("org.springframework.boot") version("3.0.1")
    id("org.jetbrains.kotlin.plugin.spring") version("1.8.0")
    idea
    kotlin("jvm") version "1.8.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

springBoot {
    mainClass.value("de.l.oklab.klimawatch.Application")
}

tasks.bootJar {
    archiveFileName.set("app.jar")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.0.1"))
    implementation("org.springframework.boot:spring-boot-starter:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.1")
    implementation("org.springframework:spring-web:6.0.3")
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.14.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.14")
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    implementation(kotlin("stdlib"))
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(mapOf("group" to "org.junit.vintage", "module" to "junit-vintage-engine"))
    }
}
noArg {
    annotation("javax.persistence.Entity")
}
tasks.test {
    useJUnitPlatform()
}
