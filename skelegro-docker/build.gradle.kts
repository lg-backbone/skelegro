plugins {
  kotlin("jvm")
  id("io.bkbn.sourdough.library.jvm")
  id("org.jetbrains.kotlinx.kover")
  id("io.gitlab.arturbosch.detekt")
  id("com.adarshr.test-logger")
  id("maven-publish")
  id("java-library")
  id("signing")
}

sourdoughLibrary {
  libraryName.set("Skelegro Docker")
  libraryDescription.set("Kotlin DSL to generated Dockerfiles️")
}

dependencies {
  implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = "1.7.0")
}

testing {
  suites {
    named("test", JvmTestSuite::class) {
      useJUnitJupiter()
      dependencies {
        implementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
        implementation("io.mockk:mockk:1.13.5")
        implementation("com.google.truth:truth:1.1.3")
      }
    }
  }
}
