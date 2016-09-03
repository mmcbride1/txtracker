package com.powerswitchnet.configuration

import java.util.{Properties, ResourceBundle}

/**
  * Created by mattymain-dt on 9/1/16.
  */

trait GlobalProperty {

  /** TWITTER SPECIFIC PROPERTIES **/
  final val twitterProperties = getProperties("twitter")

  /* Authorization properties */
  final val API_CONSUMER_KEY = twitterProperties.getString("api.auth.consumer.key")
  final val API_SECRET_KEY = twitterProperties.getString("api.auth.secret.key")
  final val API_ACCESS_TOKEN = twitterProperties.getString("api.auth.access.token")
  final val API_SECRET_TOKEN = twitterProperties.getString("api.auth.secret.token")

  /* Tracking properties */
  final val API_TRACK_TARGET = twitterProperties.getString("api.track.target")
  final val API_DEFAULT_SCHEMA = new Schema("id", "date", "text")

  /** KAFKA SPECIFIC PROPERTIES **/
  final val kafkaProperties = getProperties("kafka")

  /* Server properties */
  final val BROKER_LIST = kafkaProperties.getString("metadata.broker.list")
  final val SERIALIZER = kafkaProperties.getString("serializer.class")
  final val TOPIC = kafkaProperties.getString("kafka.producer.twitter.topic")

  /* Kafka server properties */
  def getKafkaProperties = {
    val properties = new Properties()
    properties.put("metadata.broker.list", BROKER_LIST)
    properties.put("serializer.class", SERIALIZER)
    properties
  }

  /**
    * Retrieve the resource
    * @param name - Properties bundle name
    * @return : ResourceBundle
    */
  def getProperties(name: String): ResourceBundle = {
    ResourceBundle.getBundle(name)
  }
}
