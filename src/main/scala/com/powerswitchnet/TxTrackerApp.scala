package com.powerswitchnet

import com.powerswitchnet.base.BaseLogger
import com.powerswitchnet.configuration.GlobalProperty
import com.powerswitchnet.kafka_producer.TxKafkaProducer
import com.powerswitchnet.txtracker.DataStream

/**
 * @author ${user.name}
 */

object TxTrackerApp extends BaseLogger with GlobalProperty {

  /* Initialize the data stream */
  val stream = DataStream

  /* Initialize the producer */
  val txproducer = TxKafkaProducer[String](TOPIC)

  /**
    * Main
    * @param args - args
    */
  def main(args : Array[String]): Unit = {
    while(true) {
      val tweet = stream.tracker.nextInQueue
      if(tweet == null) {
        Thread.sleep(2000)
      }
      else {
        txproducer.send(API_DEFAULT_SCHEMA.setSchema(tweet).toString())
        Thread.sleep(5000)
      }
    }
    Thread.sleep(5000)
    stream.initializedTracker.shutdown()
    LOG.info("forced shutdown on data stream")
  }
}
