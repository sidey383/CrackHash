import java.util.Locale

plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.unbroken-dome.xjc") version "2.0.0"
}

repositories {
    mavenCentral()
}

dependencies {

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.4.2")
        }
    }

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("jakarta.validation:jakarta.validation-api:3.0.0")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.4.2")

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
}

xjc {
    xjcVersion = "3.0"
    docLocale.set(Locale.ENGLISH)
}

tasks {
    bootJar {
        enabled = false
    }
    bootBuildImage {
        enabled = false
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
