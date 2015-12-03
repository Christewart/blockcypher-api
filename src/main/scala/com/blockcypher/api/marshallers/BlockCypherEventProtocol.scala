package com.blockcypher.api.marshallers

import com.blockcypher.api.events.{BlockCypherEvent, BlockCypherEventImpl}
import org.scalacoin.protocol.BitcoinAddress
import spray.json._

/**
 * Created by chris on 12/2/15.
 */
object BlockCypherEventProtocol extends DefaultJsonProtocol {

  implicit object BlockCypherFormat extends RootJsonFormat[BlockCypherEventImpl] {

/*    case class BlockCypherEventImpl(override val id : String, override val event : String,
  override val hash : Option[String],
  override val walletName : Option[String], override val token : Option[String],
  override val address : Option[BitcoinAddress],
  override val confirmations : Option[Int], override val confidence : Option[Float],
  override val script : Option[String], override val url : Option[String],
  override val callbackErrors : Int) extends BlockCypherEvent*/

    val idKey = "id"
    val eventKey = "event"
    val hashKey = "hash"
    val walletNameKey = "wallet_name"
    val tokenKey = "token"
    val addressKey = "address"
    val confirmationsKey = "confirmations"
    val confidenceKey = "confidence"
    val scriptKey = "scriptKey"
    val urlKey = "url"
    val callBackErrorsKey="callback_errors"

    override def read(value : JsValue) = {
      val obj = value.asJsObject
      val Seq(event,callbackErrors) = obj.getFields(
        eventKey, callBackErrorsKey
      )
      val id = parseString(obj.fields.getOrElse(idKey,JsNull))
      val hash = parseString(obj.fields.getOrElse(hashKey, JsNull))
      val walletName = parseString(obj.fields.getOrElse(walletNameKey,JsNull))
      val token = parseString(obj.fields.getOrElse(tokenKey,JsNull))
      val parsedAddress = parseString(obj.fields.getOrElse(addressKey,JsNull))
      val address : Option[BitcoinAddress] = if (parsedAddress.isDefined) Some(BitcoinAddress(parsedAddress.get)) else None
      val confirmations = parseInt(obj.fields.getOrElse(confirmationsKey,JsNull))
      val confidence = parseFloat(obj.fields.getOrElse(confidenceKey,JsNull))
      val url = parseString(obj.fields.getOrElse(urlKey,JsNull))
      val script = parseString(obj.fields.getOrElse(scriptKey,JsNull))

      BlockCypherEventImpl(None, event.convertTo[String], hash,
        walletName, token,
        address, confirmations,
        confidence, script, url,
        callbackErrors.convertTo[Int])
    }


    override def write(event : BlockCypherEventImpl) : JsObject = {
      val m : Map[String,JsValue] = Map(
        idKey -> (if (event.id.isDefined) JsString(event.id.get) else JsNull),
        eventKey -> JsString(event.event),
        hashKey -> (if(event.hash.isDefined) JsString(event.hash.get) else JsNull),
        walletNameKey -> (if(event.walletName.isDefined) JsString(event.walletName.get) else JsNull),
        tokenKey -> (if(event.token.isDefined) JsString(event.token.get) else JsNull),
        addressKey -> (if (event.address.isDefined) JsString(event.address.get.bitcoinAddress) else JsNull),
        confirmationsKey -> (if(event.confirmations.isDefined) JsNumber(event.confirmations.get) else JsNull),
        confidenceKey -> (if(event.confidence.isDefined) JsNumber(event.confidence.get) else JsNull),
        scriptKey -> (if(event.script.isDefined) JsString(event.script.get) else JsNull),
        urlKey -> (if(event.url.isDefined) JsString(event.url.get) else JsNull),
        callBackErrorsKey -> JsNumber(event.callbackErrors)
      )

      JsObject(m)

    }

    private def parseString(value : JsValue ) : Option[String] = {
      value match {
        case JsString(s) => Some(s)
        //fix for a bug where we can have the case Some(null) but apparently cannot match explicitly on it
        case _ => None
      }
    }

    private def parseInt(value : JsValue) : Option[Int] = {
      value match {
        case JsNumber(number) => Some(number.toInt)
        case _ => None
      }
    }

    private def parseFloat(value : JsValue) : Option[Float] = {
      value match {
        case JsNumber(number) => Some(number.toFloat)
        case _ => None
      }
    }
  }
}
