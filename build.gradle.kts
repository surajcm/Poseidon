buildscript {
    repositories {
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.2.4.RELEASE")
        classpath("gradle.plugin.com.github.spotbugs:spotbugs-gradle-plugin:2.0.0")
    }
}

plugins {
    java
    idea
    application
    id("org.springframework.boot") version "2.2.4.RELEASE"
    id("net.ltgt.errorprone") version "0.8.1"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

apply("gradle/dependencies.gradle")
apply("gradle/staticCodeAnalysis.gradle")

//println org.gradle.internal.jvm.Jvm.current()

val jar by tasks.getting(Jar::class) {
    archiveBaseName.set("poseidon")
    archiveVersion.set("0.0.1-SNAPSHOT")
}

application {
    applicationDefaultJvmArgs = listOf("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005")
}

java {
    sourceCompatibility = JavaVersion.VERSION_13
    targetCompatibility = JavaVersion.VERSION_13
}

repositories {
    mavenLocal()
    mavenCentral()
}
