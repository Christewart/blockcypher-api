package com.blockcypher.api.marshallers

import com.blockcypher.api.protocol.{BlockCypherOutputImpl, BlockCypherOutput}
import spray.json._
/**
 * Created by chris on 12/20/15.
 */
object BlockCypherOutputMarshaller extends DefaultJsonProtocol with MarshallerUtil {
  implicit object BlockCypherOutputFormat extends RootJsonFormat[BlockCypherOutput] {

    override def read(jsValue : JsValue) : BlockCypherOutput = {
      val o : JsObject = jsValue.asJsObject
      val Seq(value,script,addresses,scriptType) = o.getFields(valueKey,scriptKey,addressesKey,scriptTypeKey)

      val convertedAddresses = convertToAddressList(addresses)
      BlockCypherOutputImpl(value.convertTo[Long], script.convertTo[String],
        convertedAddresses, scriptType.convertTo[String])
    }

    override def write(output : BlockCypherOutput) : JsObject = {
      val m : Map[String,JsValue] = Map(valueKey -> JsNumber(output.value),
        scriptKey -> JsString(output.script),  addressesKey -> convertToJsArray(output.addresses),
        scriptTypeKey -> JsString(output.scriptType))
      JsObject(m)
    }


  }

}
