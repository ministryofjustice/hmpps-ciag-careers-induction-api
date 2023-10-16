plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "5.5.1"
  kotlin("plugin.spring") version "1.9.10"
  kotlin("plugin.jpa") version "1.8.22"
  id("jacoco")
}

allOpen {
  annotations(
    "jakarta.persistence.Entity",
    "jakarta.persistence.MappedSuperclass",
    "jakarta.persistence.Embeddable"
  )
}
configurations {
  testImplementation { exclude(group = "org.junit.vintage") }
}

val integrationTest = task<Test>("integrationTest") {
  description = "Integration tests"
  group = "verification"
  shouldRunAfter("test")
}

tasks.named<Test>("integrationTest") {
  useJUnitPlatform()
  filter {
    includeTestsMatching("*.Int.*")
  }
}

tasks.named<Test>("test") {
  filter {
    excludeTestsMatching("*.Int.*")
  }
}

tasks.named("check") {
  setDependsOn(
    dependsOn.filterNot {
      it is TaskProvider<*> && it.name == "detekt"
    }
  )
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  // Spring boot dependencies
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.apache.commons:commons-collections4:4.0")
  implementation("com.microsoft.azure:applicationinsights-logging-logback:2.6.4")

  // GOVUK Notify:
  implementation("io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations")
  // Enable kotlin reflect
  runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.6.20")
  // Database dependencies
  runtimeOnly("org.flywaydb:flyway-core")
  runtimeOnly("org.postgresql:postgresql:42.6.0")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
  implementation("io.arrow-kt:arrow-core:1.1.2")
  testRuntimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
  testRuntimeOnly("com.fasterxml.jackson.core:jackson-databind:2.15.2")
  testRuntimeOnly("javax.xml.bind:jaxb-api:2.2.3")
  testRuntimeOnly("org.jetbrains.kotlin:kotlin-stdlib:1.6.0-M1")
  testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.6.0-M1")
  testRuntimeOnly("org.flywaydb:flyway-core")
  testRuntimeOnly("org.postgresql:postgresql:42.6.0")
  // OpenAPI
  implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
  implementation("org.springdoc:springdoc-openapi-data-rest:1.6.9")
  implementation("org.springdoc:springdoc-openapi-kotlin:1.6.9")

  implementation("com.google.code.gson:gson:2.9.0")

  // Test dependencies
  testImplementation("org.springframework.security:spring-security-test")
  testImplementation("io.jsonwebtoken:jjwt:0.9.1")
  testImplementation("net.javacrumbs.json-unit:json-unit-assertj:2.35.0")
  testImplementation("io.swagger.parser.v3:swagger-parser-v2-converter:2.0.33")
  testImplementation("org.testcontainers:localstack:1.18.1")
  testImplementation("org.mockito:mockito-inline:4.6.1")
  testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
  testImplementation("com.h2database:h2")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.testcontainers:localstack:1.18.1")
}
repositories {
  mavenCentral()
}

jacoco {
  // You may modify the Jacoco version here
  toolVersion = "0.8.8"
}

kotlin {
  jvmToolchain {
    this.languageVersion.set(JavaLanguageVersion.of("20"))
  }
}

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(20))
}

dependencyCheck {
  suppressionFiles.add("$rootDir/dependencyCheck/suppression.xml")
}
