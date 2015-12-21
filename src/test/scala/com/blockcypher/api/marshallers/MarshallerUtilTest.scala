package com.blockcypher.api.marshallers

import org.joda.time.DateTimeConstants
import org.scalatest.{FlatSpec, MustMatchers}

/**
 * Created by chris on 12/21/15.
 */
class MarshallerUtilTest extends FlatSpec with MustMatchers with MarshallerUtil {

  "MarshallerUtil" must "parse a date time correctly from blockcypher" in {
    val str = "2015-05-22T05:10:00.305Z"
    val dateTime = parseDateTime(str)
    dateTime.getYear must be (2015)
    dateTime.getMonthOfYear must be (DateTimeConstants.MAY)
    dateTime.getDayOfMonth must be (22)
    dateTime.getHourOfDay must be (5)
    dateTime.getMinuteOfHour must be (10)
  }

}
