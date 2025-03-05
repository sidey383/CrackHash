plugins {
    java
    id("io.spring.dependency-management") version("1.1.7")
    id("org.springframework.boot") version("3.4.3")
}

repositories {
    mavenCentral()
}

tasks {
    bootJar {
        enabled = false
    }
    bootBuildImage {
        enabled = false
    }
}

allprojects {
    group = "ru.sidey383.crackhash"
    version = "1.0.0"
}
