package com.powerswitchnet.kafka_producer

import java.util.Properties
import com.powerswitchnet.configuration.GlobalProperty
import kafka.producer.{KeyedMessage, Producer, ProducerConfig}

/**
  * Created by mattymain-dt on 9/1/16.
  */

case class TxKafkaProducer[A](topic: String) extends GlobalProperty {

  /* Set the Kafka server configuration */
  protected val config = new ProducerConfig(getKafkaProperties)

  /* Kafka producer Obj */
  private lazy val producer = new Producer[A,A](config)

  /**
    * KeyedMessage initiates base structure of
    * producer message
    * @param topic - Target topic
    * @param message - Target message
    * @return : KeyedMessage
    */
  private def keyedMessage(topic: String, message: A): KeyedMessage[A,A] = {
    new KeyedMessage[A,A](topic, message)
  }

  /**
    * Pair the producer obj with the message
    * @param producer - Kafka producer
    * @param message - Target message
    */
  private def sendMessage(producer: Producer[A,A], message: KeyedMessage[A,A]) = {
    producer.send(message)
  }

  /**
    * Send the message
    * @param message - Target message
    */
  def send(message: A) = {
    sendMessage(producer, keyedMessage(topic, message))
  }
}

/**
  * TxKafkaProducer
  */
object TxKafkaProducer {
  def apply[T](topic: String, props: Properties) = new TxKafkaProducer[T](topic) {
    override val config = new ProducerConfig(props)
  }
}
