buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.1.5.RELEASE")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.21")
    }
}

plugins {
    java
    idea
    application
    id("org.springframework.boot") version "2.1.5.RELEASE"
    id("net.ltgt.errorprone") version "0.7.1"
}

apply(plugin = "io.spring.dependency-management")
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
    sourceCompatibility = JavaVersion.VERSION_1_10
    targetCompatibility = JavaVersion.VERSION_1_10
}
repositories {
    mavenLocal()
    mavenCentral()
}