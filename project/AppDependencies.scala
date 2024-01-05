import sbt._

object AppDependencies {

  val compile: Seq[ModuleID] = Seq(
    "org.scalacheck" %% "scalacheck" % "1.17.0",
    "io.github.wolfendale" %% "scalacheck-gen-regexp" % "1.1.0"
  )
}
