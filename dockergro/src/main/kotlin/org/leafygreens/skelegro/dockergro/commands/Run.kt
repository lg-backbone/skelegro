package org.leafygreens.skelegro.dockergro.commands

import org.leafygreens.skelegro.dockergro.Dockerfile

typealias RunCommands = List<String>

class Run(
    var commands: RunCommands = emptyList(),
    var separator: String = " ",
    comment: String? = null
) : Command(comment) {
  override fun tag() = "RUN"
  override fun toString() = "${tag()} ${commands.joinToString(separator = separator)}"
}

fun Dockerfile.RUN(init: Run.() -> Unit): Run {
  val run = Run().apply(init)
  steps.add(run)
  return run
}
