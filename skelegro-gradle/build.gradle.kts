plugins {
  kotlin("jvm")
  id("io.bkbn.sourdough.library.jvm") version "0.5.5"
  id("io.gitlab.arturbosch.detekt") version "1.19.0"
  id("com.adarshr.test-logger") version "3.1.0"
  id("org.jetbrains.dokka")
  id("maven-publish")
  id("java-library")
  id("signing")
}

sourdough {
  githubOrg.set("bkbnio")
  githubRepo.set("skelegro")
  libraryName.set("Skelegro Gradle")
  libraryDescription.set("Kotlin DSL to generate Gradle .kts files")
  licenseName.set("MIT License")
  licenseUrl.set("https://mit-license.org")
  developerId.set("unredundant")
  developerName.set("Ryan Brink")
  developerEmail.set("admin@bkbn.io")
}

dependencies {
  implementation("com.squareup:kotlinpoet:1.10.2")
}

testing {
  suites {
    named("test", JvmTestSuite::class) {
      useJUnitJupiter()
      dependencies {
        implementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
        implementation("io.mockk:mockk:1.12.2")
        implementation("com.google.truth:truth:1.1.3")
      }
    }
  }
}
