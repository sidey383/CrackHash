plugins {
    id("java")
    id("org.springframework.boot") version("3.4.2")
    id("io.spring.dependency-management") version("1.1.7")
}

group = "ru.sidey383"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        toolchain {
            languageVersion = JavaLanguageVersion.of(23)
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("jakarta.validation:jakarta.validation-api:3.0.0")
    implementation("com.github.dpaukov:combinatoricslib3:3.4.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.assertj:assertj-core:3.27.2")
    testImplementation("com.carrotsearch:junit-benchmarks:0.7.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}