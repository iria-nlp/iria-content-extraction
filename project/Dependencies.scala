import sbt._

object Dependencies {

    val slf4jVersion = "1.7.32"
    val logbackVersion = "1.2.10"
    val java8CompatVersion = "0.9.1"
    val betterFilesVersion = "3.8.0"
    val typesafeConfigVersion = "1.4.1"
    val log4jOverrideVersion = "2.17.1"

    val iriaCoreVersion = "0.0.1-SNAPSHOT"

    val cdr4sVersion = "3.0.9"
    val tikaVersion = "2.3.0"

    val sparkVersion = "3.1.2"
    val sparkArangoVersion = "1.0.0"

    val jacksonVersion = "2.13.1"
    val jsonValidatorVersion = "1.5.1"


    val scalatraVersion = "2.8.2"
    val servletApiVersion = "3.1.0"

    val scalaTestVersion = "3.1.4"
    val mockitoVersion = "1.16.0"

    val kafkaVersion = "2.2.1"

    val jettyVersion = "9.4.46.v20220331"

    val embeddedKafkaVersion = "2.8.1"


    val iriaCore = Seq( "app.iria" %% "iria-document-model" % iriaCoreVersion,
                        "app.iria" %% "iria-utils" % iriaCoreVersion,
                        "app.iria" %% "iria-test-base" % iriaCoreVersion % Test )

    val iriaIntegration = Seq( "app.iria" %% "iria-kafka" % iriaCoreVersion )

    val tika = Seq( "org.apache.tika" % "tika-core" % tikaVersion,
                    "org.apache.tika" % "tika-parsers-standard-package" % tikaVersion )

    val cdr4s = Seq( "com.twosixlabs.cdr4s" %% "cdr4s-core" % cdr4sVersion,
                     "com.twosixlabs.cdr4s" %% "cdr4s-dart-json" % cdr4sVersion )

    val kafka = Seq( "org.apache.kafka" %% "kafka" % kafkaVersion,
                     "org.apache.kafka" % "kafka-clients" % kafkaVersion )

    val betterFiles = Seq( "com.github.pathikrit" %% "better-files" % betterFilesVersion )

    val embeddedKafka = Seq( "io.github.embeddedkafka" %% "embedded-kafka" % embeddedKafkaVersion % Test,
                             "io.github.embeddedkafka" %% "embedded-kafka-streams" % embeddedKafkaVersion % Test,
                             "jakarta.ws.rs" % "jakarta.ws.rs-api" % "2.1.2" % Test ) //https://github.com/sbt/sbt/issues/3618

    val scalatra = Seq( "org.scalatra" %% "scalatra" % scalatraVersion,
                        "org.scalatra" %% "scalatra-jetty" % scalatraVersion,
                        "org.eclipse.jetty" % "jetty-webapp" % jettyVersion,
                        "javax.servlet" % "javax.servlet-api" % servletApiVersion,
                        "org.scalatra" %% "scalatra-scalatest" % scalatraVersion % Test )

    val log4shellOverrides = Seq( "org.apache.logging.log4j" % "log4j-api" % log4jOverrideVersion,
                                  "org.apache.logging.log4j" % "log4j-core" % log4jOverrideVersion,
                                  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jOverrideVersion )

}
