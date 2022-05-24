import Dependencies._
import sbt.Tests.Argument
import sbt._

organization in ThisBuild := "app.iria"
name := "iria-content-extraction"
version in ThisBuild := "0.0.1-SNAPSHOT"
scalaVersion in ThisBuild := "2.12.7"

resolvers in ThisBuild ++= Seq( "Maven Central" at "https://repo1.maven.org/maven2/",
                                ( "Clulab Artifactory" at "http://artifactory.cs.arizona.edu:8081/artifactory/sbt-release" ).withAllowInsecureProtocol( true ),
                                "Local Ivy Repository" at s"file://${System.getProperty( "user.home" )}/.ivy2/local/default" )

lazy val disablePublish = Seq( skip.in( publish ) := true )

lazy val root = {
    ( project in file( "." ) ).aggregate( api, controllers )
      .settings( disablePublish )
}

lazy val api = {
    ( project in file( "iria-extractor-api" ) ).settings( libraryDependencies ++= iriaCore
                                                                                  ++ tika
                                                                                  ++ betterFiles,
                                                          dependencyOverrides ++= log4shellOverrides )
}

lazy val controllers = {
    ( project in file( "iria-extractor-controllers" ) )
      .dependsOn( api )
      .settings( libraryDependencies ++= scalatra
                                         ++ iriaCore
                                         ++ iriaIntegration,
                 dependencyOverrides ++= log4shellOverrides )
}

parallelExecution in Test := false
fork in Test := false

testOptions in Test := Seq( Argument( "-oI" ) )

javacOptions in ThisBuild ++= Seq( "-source", "8", "-target", "8" )
scalacOptions in ThisBuild += "-target:jvm-1.8"
