package com.powerswitchnet.txtracker

import java.beans.Transient
import java.util.concurrent.LinkedBlockingQueue
import com.powerswitchnet.base.BaseLogger
import com.powerswitchnet.configuration.GlobalProperty
import twitter4j._

/**
  * Created by mattymain-dt on 9/1/16.
  */

class DataStream() extends BaseLogger with GlobalProperty {

  /* Initialize the Queue to process tweets */
  val queue: LinkedBlockingQueue[Status] = new LinkedBlockingQueue[Status]()

  /**
    * Initialize stream listener
    * @return : StatusListener
    */
  def streamListener: StatusListener = {
    def baseStatusListener = new StatusListener() {
      def onStatus(status: Status) {queue.offer(status)}
      def onDeletionNotice(DeletionNotice: StatusDeletionNotice) {}
      def onTrackLimitationNotice(numberLimited: Int) {}
      def onException(x: Exception) {LOG.error(x) ; x.printStackTrace()}
      def onScrubGeo(userId: Long, upToStatusId: Long) {}
      def onStallWarning(warning: StallWarning) {}
    }
    baseStatusListener
  }

  /**
    * Set the Twitter authorization criteria
    * @return : Configuration
    */
  def getOAuthConfiguration = {
    new twitter4j.conf.ConfigurationBuilder()
      .setOAuthConsumerKey(API_CONSUMER_KEY)
      .setOAuthConsumerSecret(API_SECRET_KEY)
      .setOAuthAccessToken(API_ACCESS_TOKEN)
      .setOAuthAccessTokenSecret(API_SECRET_TOKEN)
      .setJSONStoreEnabled(true)
      .build()
  }

  /**
    * Run the Twitter data stream
    * @param trackTarget - The Twitter trend to process
    * @return : Unit
    */
  def run(trackTarget: Option[String] = None): TwitterStream = {
    val target = trackTarget.getOrElse(API_TRACK_TARGET)
    val twitterStream = new TwitterStreamFactory(getOAuthConfiguration).getInstance
    val query: FilterQuery = new FilterQuery().track(target)
    LOG.info(s"Initializing twitter stream with target: '$target'")
    twitterStream.addListener(streamListener)
    twitterStream.filter(query)
    twitterStream
  }

  /**
    * Get the next in the status queue
    * @return : Status
    */
  def nextInQueue = {
    queue.poll()
  }
}

/**
  * DataStream
  */
object DataStream {
  @Transient lazy val tracker = new DataStream()
  val initializedTracker = tracker.run()
}
