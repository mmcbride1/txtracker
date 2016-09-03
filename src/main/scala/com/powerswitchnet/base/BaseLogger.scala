package com.powerswitchnet.base

import org.apache.log4j.Logger

/**
  * Created by mattymain-dt on 9/1/16.
  */

/**
  * BaseLogger
  */
trait BaseLogger extends Serializable {
  @transient lazy val LOG = Logger.getLogger(this.getClass)

  final val LEVEL_INFO = "INFO"
  final val LEVEL_DEBUG = "DEBUG"
  final val LEVEL_WARN = "WARN"
  final val LEVEL_ERROR = "ERROR"
  final val LEVEL_FATAL = "FATAL"
}
