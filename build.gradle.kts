buildscript {
    repositories {
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.1.7.RELEASE")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.21")
        classpath("gradle.plugin.com.github.spotbugs:spotbugs-gradle-plugin:2.0.0")
    }
}

plugins {
    java
    idea
    application
    id("org.springframework.boot") version "2.1.8.RELEASE"
    id("net.ltgt.errorprone") version "0.8.1"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("de.aaschmid.cpd") version "2.0"
}

apply("gradle/dependencies.gradle")
apply("gradle/staticCodeAnalysis.gradle")

//println org.gradle.internal.jvm.Jvm.current()

tasks.jar {
    baseName = "poseidon"
    version = "0.0.1-SNAPSHOT"
}
application {
    applicationDefaultJvmArgs = listOf("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005")
}
java {
    sourceCompatibility = JavaVersion.VERSION_12
    targetCompatibility = JavaVersion.VERSION_12
}
repositories {
    mavenLocal()
    mavenCentral()
}
