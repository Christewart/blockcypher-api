package com.blockcypher.api.marshallers


import com.blockcypher.api.protocol.BlockCypherInput
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.scalacoin.marshallers.BitcoinAddressProtocol._
import org.scalacoin.protocol.BitcoinAddress
import spray.json._
import scala.collection.breakOut

/**
 * Created by chris on 12/20/15.
 */
trait MarshallerUtil {

  def blockHashKey = "block_hash"
  def blockHeightKey = "block_height"
  def hashKey = "hash"
  def totalKey = "total"
  def feesKey = "fees"
  def sizeKey = "size"
  def preferenceKey = "preference"
  def relayedByKey = "relayed_by"
  def receivedKey = "received"
  def confirmedKey = "confirmed"
  def verKey = "ver"
  def lockTimeKey = "lock_time"
  def doubleSpendKey = "double_spend"
  def vinSizeKey = "vin_sz"
  def voutSizeKey = "vout_sz"
  def confirmationsKey = "confirmations"
  def confidenceKey = "confidence"
  def inputsKey = "inputs"
  def outputsKey = "outputs"


  def valueKey = "value"
  def scriptKey = "script"
  def addressesKey = "addresses"
  def scriptTypeKey = "script_type"

  def convertToAddressList(value : JsValue) : Seq[BitcoinAddress] = {
    import DefaultJsonProtocol._
    value match {
      case ja: JsArray => {
        ja.elements.toList.map(
          e => BitcoinAddress(e.convertTo[String]))
      }
      case _ => throw new RuntimeException("This Json type is not valid for parsing a list of bitcoin addresses")
    }
  }

  def convertToJsArray[T](addresses : Seq[T])(implicit formatter : JsonWriter[T]) : JsArray  = {
    JsArray(addresses.map(p =>
      formatter.write(p))(breakOut): Vector[JsValue])
  }



  def parseDateTime(str : String) : DateTime = {

    //need to parse date time of this format
    //2015-05-22T05:10:00.305308666Z

    DateTime.parse(str,DateTimeFormat.forPattern(dateTimePattern))
  }
  def dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS"
}
