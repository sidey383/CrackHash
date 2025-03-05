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
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.4")
    implementation("org.jetbrains:annotations:24.0.0")
    implementation("org.hibernate.validator:hibernate-validator:8.0.2.Final")
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
            "BP_JVM_VERSION" to "21",
            "BP_JVM_TYPE" to "jdk"
        )
    )
}
