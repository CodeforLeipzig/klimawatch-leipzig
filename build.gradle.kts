group = "de.l.oklab.klimawatch"
version = "1.0.0-SNAPSHOT"

plugins {
    id ("org.jetbrains.kotlin.plugin.noarg") version "1.8.0"
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.github.ben-manes.versions")
    id("org.jetbrains.kotlin.plugin.spring")
    idea
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

springBoot {
    mainClass.value("de.l.oklab.klimawatch.ApplicationKt")
}

tasks.bootJar {
    archiveFileName.set("app.jar")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
    implementation(platform(Spring.boms.dependencies))
    implementation("org.springframework.boot:spring-boot-starter:_")
    implementation(Spring.boot.data.jpa)
    implementation(Spring.boot.data.rest)
    implementation(Spring.boot.web)
    implementation("org.springframework:spring-web:_")
    implementation("com.fasterxml.jackson.core:jackson-core:_")
    implementation("com.fasterxml.jackson.core:jackson-annotations:_")
    implementation("com.fasterxml.jackson.core:jackson-databind:_")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:_")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:_")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:_")
    implementation("jakarta.platform:jakarta.jakartaee-web-api:_")
    implementation("org.hibernate:hibernate-validator:_")

    implementation("com.apicatalog:titanium-json-ld:_")
    implementation("org.glassfish:jakarta.json:_")

    annotationProcessor (Spring.boot.configurationProcessor)
    runtimeOnly("org.postgresql:postgresql:_")

    testImplementation(Testing.junit.jupiter.api)
    testRuntimeOnly(Testing.junit.jupiter.engine)
    implementation(kotlin("stdlib"))
    testImplementation(Spring.boot.test)
}
noArg {
    annotation("jakarta.persistence.Entity")
}
tasks.test {
    useJUnitPlatform()
}
