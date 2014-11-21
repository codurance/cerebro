import sbt._
import Keys._
import org.scalatra.sbt._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object CerebroBuild extends Build {
	val Organization = "com.codurance"
	val Name = "Cerebro"
	val Version = "0.1.0-SNAPSHOT"
	val ScalaVersion = "2.10.4"
	val ScalatraVersion = "2.3.0"
	val JettyVersion = "9.2.5.v20141112"

	lazy val project = Project(
		"cerebro",
		file("."),
		settings = seq(com.typesafe.sbt.SbtStartScript.startScriptForClassesSettings: _*) ++
				Defaults.defaultSettings ++ ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ Seq(
			organization := Organization,
			name := Name,
			version := Version,
			scalaVersion := ScalaVersion,
			resolvers += Classpaths.typesafeReleases,
			libraryDependencies ++= Seq(
				"org.scalatra" %% "scalatra" % ScalatraVersion,
				"org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
				"org.scalatra" %% "scalatra-auth" % ScalatraVersion,
				"org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
				"org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
				"com.google.apis" % "google-api-services-oauth2" % "v2-rev59-1.17.0-rc",
				"com.google.apis" % "google-api-services-plus" % "v1-rev115-1.17.0-rc",
				"com.google.http-client" % "google-http-client-jackson" % "1.17.0-rc",
				"com.google.code.gson" % "gson" % "2.2.4",
				"com.stackmob" % "newman_2.10" % "1.3.5",
				"org.json4s" %% "json4s-native" % "3.2.9",
				"org.json4s" %% "json4s-jackson" % "3.2.9",
				"ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
				"org.eclipse.jetty" % "jetty-webapp" % JettyVersion % "compile;container",
				"org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "compile;container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
			),
			scalateTemplateConfig in Compile <<= (sourceDirectory in Compile) {
				base =>
					Seq(
						TemplateConfig(
							base / "webapp" / "WEB-INF" / "templates",
							Seq.empty, /* default imports should be added here */
							Seq(
								Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
							), /* add extra bindings here */
							Some("templates")
						)
					)
			}
		)
	)
}
