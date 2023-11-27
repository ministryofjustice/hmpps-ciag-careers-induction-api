plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "5.9.0"
  kotlin("plugin.spring") version "1.9.20"
  kotlin("plugin.jpa") version "1.9.20"
  kotlin("plugin.lombok") version "1.9.20"
  id("jacoco")
  id("jvm-test-suite")
}

configurations {
  implementation {
    exclude(module = "commons-logging")
    exclude(module = "log4j")
    exclude(module = "c3p0")
    exclude(module = "logback-classic")
  }
  testImplementation {
    exclude(group = "org.junit.vintage")
    exclude(module = "logback-classic")
  }
}

ext["springdoc.openapi.version"] = "2.2.0"
sourceSets {
  main {
    java {
      setSrcDirs(listOf("src/main"))
    }
  }
}
dependencies {
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  annotationProcessor("org.projectlombok:lombok:1.18.30")
  testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
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
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("springdoc.openapi.version")}")

  compileOnly("org.projectlombok:lombok:1.18.30")
  testImplementation("jakarta.servlet:jakarta.servlet-api:6.0.0")

  implementation("org.apache.commons:commons-collections4:4.0")
  implementation("uk.gov.justice.service.hmpps:hmpps-sqs-spring-boot-starter:2.0.1")

  // Enable kotlin reflect
  implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.20")

  implementation("io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations:1.29.0")

  // Database dependencies
  implementation("org.flywaydb:flyway-core")
  runtimeOnly("org.postgresql:postgresql:42.4.0")

  implementation("io.arrow-kt:arrow-core:1.1.2")

  implementation("com.google.code.gson:gson:2.9.0")

  // Test dependencies
  testImplementation("com.github.tomakehurst:wiremock-standalone:2.27.2")
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

tasks {
  test {
    useJUnitPlatform {
      exclude("**/*IntTest*")
    }
    minHeapSize = "128m"
    maxHeapSize = "2048m"
  }

  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjvm-default=all")
      jvmTarget = "21"
    }
  }
  withType<JavaCompile> {
    sourceCompatibility = "21"
  }
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      useJUnitJupiter()
      sourceSets {
        test {
          java {
            setSrcDirs(listOf("src/test"))
          }
        }
      }
      dependencies {
        implementation(project())
      }
    }
  }
}

repositories {
  mavenCentral()
}

/*kotlin {
  jvmToolchain {
    this.languageVersion.set(JavaLanguageVersion.of("21"))
  }
}

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}*/

dependencyCheck {
  suppressionFiles.add("$rootDir/dependencyCheck/suppression.xml")
}
