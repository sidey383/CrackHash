import java.util.Locale

plugins {
    java
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
    implementation("jakarta.validation:jakarta.validation-api:3.0.0")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.4.2")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
    implementation("org.glassfish.jaxb:jaxb-runtime:4.0.3")
    implementation("org.glassfish.jaxb:jaxb-core:4.0.3")

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
}

xjc {
    xjcVersion = "3.0"
    docLocale.set(Locale.ENGLISH)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
