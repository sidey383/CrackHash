plugins {
    application
    java
    id("io.spring.dependency-management")
    id("org.springframework.boot")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":Core"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.jetbrains:annotations:24.0.0")
    implementation("org.hibernate.validator:hibernate-validator:8.0.2.Final")
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
    environment.set(
        mapOf(
            "SPRING_PROFILES_ACTIVE" to "docker",
            "MANAGER_ADDRESS" to "http://manager:8080",
            "BP_JVM_VERSION" to "21",
            "BP_JVM_TYPE" to "jdk"
        )
    )
}
