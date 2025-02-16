plugins {
    java
    id("io.spring.dependency-management")
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
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
