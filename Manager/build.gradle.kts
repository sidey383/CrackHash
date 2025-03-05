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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.jetbrains:annotations:24.0.0")
    implementation("org.hibernate.validator:hibernate-validator:8.0.2.Final")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.2")
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
