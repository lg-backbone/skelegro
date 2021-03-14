package org.leafygreens.skelegro.dockergro.commands

import org.leafygreens.skelegro.dockergro.Dockerfile

class Entrypoint(
  var instructions: String = "",
  comment: String? = null
) : Command(comment) {
  override fun tag() = "ENTRYPOINT"
  override fun toString(): String {
    val explode = instructions
      .split(" ")
      .joinToString(prefix = "[\"", separator = "\", \"", postfix = "\"]")
    return "${tag()} $explode"
  }
}

fun Dockerfile.ENTRYPOINT(init: Entrypoint.() -> Unit): Entrypoint {
  val entrypoint = Entrypoint().apply(init)
  steps.add(entrypoint)
  return entrypoint
}
