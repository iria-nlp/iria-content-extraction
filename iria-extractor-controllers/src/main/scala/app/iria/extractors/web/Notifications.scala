package app.iria.extractors.web

import app.iria.integration.kafka.Kafka
import app.iria.model.Doc

trait Notification {
    val `type` : String
    val name : String

    def send( doc : Doc ) : Unit
}

abstract class KafkaNotification( override val `type` : String = "kafka", override val name : String, kafka : Kafka ) extends Notification {}

// TODO -- implement REST notification
abstract class RestNotification( override val `type` : String = "rest", override val name : String, client : Object ) extends Notification {}
