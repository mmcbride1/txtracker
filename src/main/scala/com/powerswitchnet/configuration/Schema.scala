package com.powerswitchnet.configuration

import twitter4j._

/**
  * Created by mattymain-dt on 9/1/16.
  */

class Schema(fields: String*) {

  /**
    * Create the schema to be mapped as JSON
    * @param status - Twitter4j Status.type
    * @return : JSONObject
    */
  def setSchema(status: Status): JSONObject = {

    /* Collect the schema */
    val schema = new JSONObject()

    /* Map input params as schema selection */
    fields.map(field => field match {
      case "id"    => schema.put(field, status.getId)
      case "date"  => schema.put(field, status.getCreatedAt)
      case "name"  => schema.put(field, status.getUser.getName)
      case "text"  => schema.put(field, status.getText)
      case "geo"   => schema.put(field, status.getGeoLocation)
      case "lang"  => schema.put(field, status.getLang)
      case "count" => schema.put(field, status.getRetweetCount)
      case _       => None
    })
    schema
  }

}
