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
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.assertj:assertj-core:3.27.2")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("ru.sidey383.crackhash.worker.WorkerApplication")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.bootBuildImage {
    imageName.set("crackhash-worker:latest")
    environment.set(mapOf("BP_JVM_VERSION" to "21", "BP_JVM_TYPE" to "jdk"))
}
