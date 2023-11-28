plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "5.7.0"
  id("org.openapi.generator") version "7.0.1"
  kotlin("plugin.spring") version "1.9.10"
  kotlin("plugin.jpa") version "1.9.10"
  kotlin("plugin.lombok") version "1.9.10"
}
val springdocOpenapiVersion = "2.2.0"
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

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      jvmTarget = "19"
    }
  }
  withType<JavaCompile> {
    sourceCompatibility = "19"
  }
}

dependencies {
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  // Spring boot dependencies
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("javax.xml.bind:jaxb-api:2.3.1")

  implementation("commons-codec:commons-codec:1.16.0")
  implementation("org.ehcache:ehcache:3.10.8")
  implementation("com.zaxxer:HikariCP:5.1.0")

  implementation("io.swagger:swagger-annotations:1.6.12")

  implementation("org.apache.commons:commons-lang3:3.13.0")
  implementation("commons-io:commons-io:2.15.0")
  implementation("com.google.guava:guava:32.1.3-jre")
  implementation("org.apache.commons:commons-text:1.11.0")
  implementation("com.oracle.database.jdbc:ojdbc10:19.21.0.0")
  implementation("org.hibernate.orm:hibernate-community-dialects")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocOpenapiVersion")

  implementation("javax.servlet:javax.servlet-api:3.0.1")

  implementation("org.apache.commons:commons-collections4:4.0")
  implementation("uk.gov.justice.service.hmpps:hmpps-sqs-spring-boot-starter:2.0.1")

  // Enable kotlin reflect
  implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.20")

  implementation("io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations:1.29.0")

  // Database dependencies
  implementation("org.flywaydb:flyway-core")
  runtimeOnly("org.postgresql:postgresql:42.4.0")

  implementation("io.arrow-kt:arrow-core:1.1.2")

  // OpenAPI
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

  implementation("com.google.code.gson:gson:2.9.0")

  // Test dependencies
  testImplementation("org.springframework.security:spring-security-test")
  testImplementation("io.jsonwebtoken:jjwt:0.9.1")
  testImplementation("net.javacrumbs.json-unit:json-unit-assertj:2.35.0")
  testImplementation("io.swagger.parser.v3:swagger-parser-v2-converter:2.0.33")
  testImplementation("org.mockito:mockito-inline:4.6.1")
  testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
  testImplementation("org.testcontainers:localstack:1.18.1")
  testImplementation("com.h2database:h2")
  implementation(kotlin("stdlib"))
}
repositories {
  mavenCentral()
}

dependencyCheck {
  suppressionFiles.add("$rootDir/dependencyCheck/suppression.xml")
}
