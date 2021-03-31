package org.leafygreens.skelegro.terragro

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.leafygreens.skelegro.terragro.DeclarationEntityExtensions.entityMap
import org.leafygreens.skelegro.terragro.DeclarationEntityExtensions.keyVal
import org.leafygreens.skelegro.terragro.DeclarationEntityExtensions.objectEntity
import org.leafygreens.skelegro.terragro.DeclarationExtensions.resourceDeclaration
import org.leafygreens.skelegro.terragro.DeclarationExtensions.variableDeclaration
import org.leafygreens.skelegro.terragro.TestData.getFileSnapshot

internal class DeclarationTest {

  @Test
  fun `Can build variable declaration`() {
    // when
    val declaration = Declaration.Variable("github_token", type = VariableType.STRING, sensitive = true)

    // do
    val result = declaration.toString()

    // expect
    val expected = """
      variable "github_token" {
        type = string
        sensitive = true
      }
    """.trimIndent()
    assertThat(result).isEqualTo(expected)
  }

  @Test
  fun `Can build a resource declaration`() {
    // when
    val entities = mutableListOf<DeclarationEntity>(
      DeclarationEntity.Object(
        "metadata", values = mutableListOf(
          DeclarationEntity.Simple("name", "potato-app"),
          DeclarationEntity.Map(
            "labels", mutableListOf(
              DeclarationEntity.Simple("application", "potato-app"),
              DeclarationEntity.Simple("owner", "big-boss")
            )
          )
        )
      ),
      DeclarationEntity.Object("spec")
    )
    val declaration = Declaration.Resource("kubernetes_deployment", "backbone_generator", entities)

    // do
    val result = declaration.toString()

    // expect
    val expected = """
      resource "kubernetes_deployment" "backbone_generator" {
        metadata {
          name = "potato-app"
          labels = {
            application = "potato-app"
            owner = "big-boss"
          }
        }
        spec {
        }
      }
    """.trimIndent()
    assertThat(result).isEqualTo(expected)
  }

  @Test
  fun `Can build a variable declaration using a DSL`() {
    // when
    val manifest = terraformManifest {
      variableDeclaration("github_token") {
        type = VariableType.STRING
        sensitive = true
      }

      resourceDeclaration("kubernetes_deployment", "backbone_generator") {
        objectEntity("metadata") {
          keyVal("name", "potato-app")
          entityMap<String>("labels") {
            keyVal("application", "potato-app")
            keyVal("owner", "big-boss")
          }
        }
      }
    }

    // do
    val result = manifest.toString()

    // expect
    val expected = """
      variable "github_token" {
        type = string
        sensitive = true
      }
      resource "kubernetes_deployment" "backbone_generator" {
        metadata {
          name = "potato-app"
          labels = {
            application = "potato-app"
            owner = "big-boss"
          }
        }
      }
    """.trimIndent()
    assertThat(result).isEqualTo(expected)
  }

  @Test
  fun `Can build a full deployment manifest`() {
    // when
    val manifest = terraformManifest {
      variableDeclaration("github_token") {
        type = VariableType.STRING
        sensitive = true
      }
      resourceDeclaration("kubernetes_deployment", "potato_app") {
        objectEntity("metadata") {
          keyVal("name", "potato-app")
          entityMap<String>("labels") {
            keyVal("application", "potato-app")
            keyVal("owner", "big-boss")
          }
        }

        objectEntity("spec") {
          keyVal("replicas", 3)

          objectEntity("selector") {
            entityMap<String>("match_labels") {
              keyVal("application", "potato-app")
              keyVal("owner", "big-boss")
            }
          }
          objectEntity("template") {
            objectEntity("metadata") {
              entityMap<String>("labels") {
                keyVal("application", "potato-app")
                keyVal("owner", "big-boss")
              }
            }
            objectEntity("spec") {
              objectEntity("image_pull_secrets") {
                keyVal("name", "ghcr")
              }
              objectEntity("container") {
                keyVal("image", "my-image:latest")
                keyVal("name", "potato-app")
                keyVal("image_pull_policy", "Always")
                objectEntity("port") {
                  keyVal("container_port", 8080)
                  keyVal("protocol", "TCP")
                }
                objectEntity("env") {
                  keyVal("name", "MY_SPECIAL_ENV_VAR")
                  keyVal("value", "potato")
                }
                objectEntity("env") {
                  keyVal("name", "GITHUB_TOKEN")
                  keyVal("value", VariableReference("github_token"))
                }
                objectEntity("resources") {
                  entityMap<String>("limits") {
                    keyVal("cpu", "1")
                    keyVal("memory", "1024Mi")
                  }
                  entityMap<String>("requests") {
                    keyVal("cpu", "0.5")
                    keyVal("memory", "512Mi")
                  }
                }
                objectEntity("liveness_probe") {
                  objectEntity("http_get") {
                    keyVal("path", "/")
                    keyVal("port", 8080)
                  }
                  keyVal("initial_delay_seconds", 30)
                  keyVal("period_seconds", 30)
                }
              }
            }
          }
        }
      }
    }

    // do
    val result = manifest.toString()

    // expect
    val expected = getFileSnapshot("deployment.tf")
    assertThat(result).isEqualTo(expected.trim())
  }

}
