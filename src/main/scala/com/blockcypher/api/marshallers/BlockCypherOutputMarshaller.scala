package com.blockcypher.api.marshallers

import com.blockcypher.api.protocol.{BlockCypherOutputImpl, BlockCypherOutput}
import org.scalacoin.currency.Satoshis
import spray.json._
import org.scalacoin.marshallers.BitcoinAddressProtocol._
/**
 * Created by chris on 12/20/15.
 */
object BlockCypherOutputMarshaller extends DefaultJsonProtocol with MarshallerUtil {
  implicit object BlockCypherOutputFormat extends RootJsonFormat[BlockCypherOutput] {

    override def read(jsValue : JsValue) : BlockCypherOutput = {
      val o : JsObject = jsValue.asJsObject
      val Seq(value,script,addresses,scriptType) = o.getFields(valueKey,scriptKey,addressesKey,scriptTypeKey)

      val convertedAddresses = convertToAddressList(addresses)
      BlockCypherOutputImpl(Satoshis(value.convertTo[Long]), script.convertTo[String],
        convertedAddresses, scriptType.convertTo[String])
    }

    override def write(output : BlockCypherOutput) : JsObject = {
      val m : Map[String,JsValue] = Map(valueKey -> JsNumber(output.value.value),
        scriptKey -> JsString(output.script),  addressesKey -> convertToJsArray(output.addresses),
        scriptTypeKey -> JsString(output.scriptType))
      JsObject(m)
    }


  }

}
