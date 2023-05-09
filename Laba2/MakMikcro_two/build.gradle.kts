import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "ru.psu.java.LaBa2"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {

    //
    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")
    implementation("org.postgresql:postgresql")
    //Библиотеки, используемые в автотестах.
    testImplementation("org.springframework.boot:spring-boot-starter-test"){
        exclude("junit:junit") //Вырезаем JUnit версии 4, который идёт по-умолчанию
        exclude("org.mockito:mockito-core") //Вместо mockito используется springmockk (для Kotlin)
    }
    testImplementation("com.ninja-squad:springmockk:3.1.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1") //Добавляем 5ю версию тестов.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testImplementation("com.h2database:h2:2.1.214")
    //
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    //

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
