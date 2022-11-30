plugins {
  kotlin("jvm")
  id("io.bkbn.sourdough.library.jvm")
  id("io.gitlab.arturbosch.detekt")
  id("com.adarshr.test-logger")
  id("maven-publish")
  id("java-library")
  id("signing")
}

sourdoughLibrary {
  githubOrg.set("bkbnio")
  githubRepo.set("skelegro")
  libraryName.set("Skelegro Docker")
  libraryDescription.set("Kotlin DSL to generated Dockerfiles️")
  licenseName.set("MIT License")
  licenseUrl.set("https://mit-license.org")
  developerId.set("unredundant")
  developerName.set("Ryan Brink")
  developerEmail.set("admin@bkbn.io")
}

dependencies {
  implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = "1.6.4")
}

testing {
  suites {
    named("test", JvmTestSuite::class) {
      useJUnitJupiter()
      dependencies {
        implementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
        implementation("io.mockk:mockk:1.13.3")
        implementation("com.google.truth:truth:1.1.3")
      }
    }
  }
}
