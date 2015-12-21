package com.blockcypher.api.marshallers


import org.scalacoin.protocol.BitcoinAddress
import spray.json._
import DefaultJsonProtocol._
import scala.collection.breakOut

/**
 * Created by chris on 12/20/15.
 */
trait MarshallerUtil {

  def valueKey = "value"
  def scriptKey = "script"
  def addressesKey = "addresses"
  def scriptTypeKey = "script_type"

  def convertToAddressList(value : JsValue) : Seq[BitcoinAddress] = {
    value match {
      case ja: JsArray => {
        ja.elements.toList.map(
          e => BitcoinAddress(e.convertTo[String]))
      }
      case _ => throw new RuntimeException("This Json type is not valid for parsing a list of bitcoin addresses")
    }
  }

  def convertToJsArray(addresses : Seq[BitcoinAddress]) : JsArray  = {
    import org.scalacoin.marshallers.BitcoinAddressProtocol._
    JsArray(addresses.map(p =>
      bitcoinAddressFormat.write(p))(breakOut): Vector[JsValue])
  }
}
