group = "de.l.oklab.klimawatch"
version = "1.0.0-SNAPSHOT"

var mockkVersion = "1.13.3"
var kotlinVersion = "1.7.20"
var springVersion = "2.7.5"
var jacksonVersion = "2.14.1"
var junitVersion = "5.9.0"

idea {
    module {
        sourceDirs.remove(file("src/module-test/kotlin"))
        testSourceDirs.add(file("src/module-test/kotlin"))
    }
}

plugins {
    id ("org.jetbrains.kotlin.plugin.noarg") version ("1.7.21")
    id ("org.springframework.boot") version("2.7.5")
    id ("org.jetbrains.kotlin.plugin.spring") version("1.7.20")
    idea
    kotlin("jvm") version "1.7.20"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

springBoot {
    mainClass.value("de.l.oklab.klimawatch.Application")
    buildInfo {
        properties {
            group = "de.l.oklab.klimawatch"
            name = "app"
            version = "1.0.0-SNAPSHOT"
        }
    }
}

tasks.bootJar {
    archiveFileName.set("app.jar")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${springVersion}"))
    implementation("org.springframework.boot:spring-boot-starter:${springVersion}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${springVersion}")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:${springVersion}")
    implementation("org.springframework.boot:spring-boot-starter-web:${springVersion}")
    implementation("org.springframework:spring-web:5.3.23")
    implementation("com.fasterxml.jackson.core:jackson-core:${jacksonVersion}")
    implementation("com.fasterxml.jackson.core:jackson-annotations:${jacksonVersion}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${jacksonVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonVersion}")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.12")

    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testImplementation("io.mockk:mockk:${mockkVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group="junit", module="junit")
    }
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
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
