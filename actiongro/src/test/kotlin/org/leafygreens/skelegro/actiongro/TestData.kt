package org.leafygreens.skelegro.actiongro

import java.io.File

object TestData {
  // TODO This needs to be in a test utils folder
  fun getFileSnapshot(fileName: String): String {
    val snapshotPath = "src/test/resources/snapshots"
    val file = File("$snapshotPath/$fileName")
    return file.readText()
  }
}
