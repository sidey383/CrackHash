plugins {
    application
    java
    id("io.spring.dependency-management")
    id("org.springframework.boot") version("3.4.2")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":Core"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("jakarta.validation:jakarta.validation-api:3.0.0")
    implementation("org.jetbrains:annotations:24.0.0")
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
}

application {
    mainClass.set("ru.sidey383.crackhash.manager.ManagerApplication")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.bootBuildImage {
    imageName.set("crackhash-manager:latest")
    environment.set(
        mapOf(
            "SPRING_PROFILES_ACTIVE" to "docker",
            "WORKER_ADDRESS_0" to "http://worker-1:8081",
            "WORKER_ADDRESS_1" to "http://worker-2:8081",
            "BP_JVM_VERSION" to "21",
            "BP_JVM_TYPE" to "jdk"
        )
    )
}
