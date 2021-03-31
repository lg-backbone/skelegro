import org.gradle.kotlin.dsl.named

plugins {
  id("org.leafygreens.skelegro.kotlin-common-conventions")
  id("com.github.johnrengelman.shadow")
  `maven-publish`
}

tasks {
  named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    @Suppress("DEPRECATION")
    classifier = "shadow"
  }
}

publishing {
  repositories {
    maven {
      name = "GithubPackages"
      url = uri("https://maven.pkg.github.com/lg-backbone/skelegro")
      credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
      }
    }
  }
  publications {
    create<MavenPublication>("library") {
      from(components["kotlin"])
      artifact(tasks["shadowJar"])
    }
  }
}
