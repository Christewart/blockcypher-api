package com.blockcypher.api.marshallers

import com.blockcypher.api.events.BlockCypherEventImpl
import com.blockcypher.api.protocol.{BlockCypherInputImpl, BlockCypherInput}
import org.scalacoin.protocol.BitcoinAddress
import spray.json._
import scala.collection.breakOut

/**
 * Created by chris on 12/19/15.
 */
object  BlockCypherInputMarshaller extends DefaultJsonProtocol {

  implicit object BlockCypherInputFormatter extends RootJsonFormat[BlockCypherInput] {

    /*
    trait BlockCypherInput {
  def prevHash : String
  def outputIndex : Int
  def outputValue : Long
  def sequence : Long
  def addresses : Seq[BitcoinAddress]
  def scriptType : String
}
     */
    private val prevHashKey = "prev_hash"
    private val outputIndexKey = "output_index"
    private val outputValueKey = "output_value"
    private val scriptKey = "script"
    private val sequenceKey = "sequence"
    private val addressesKey = "addresses"
    private val scriptTypeKey = "script_type"
    override def read(value : JsValue) : BlockCypherInput = {
      val obj = value.asJsObject
      val Seq(prevHash, outputIndex, outputValue, script, sequence, addresses, scriptType) = obj.getFields(
        prevHashKey, outputIndexKey, outputValueKey, scriptKey, sequenceKey, addressesKey, scriptTypeKey
      )
    // convert JsArray to List[ BitcoinAdress ]
      val addressList = convertToAddressList(addresses)
      BlockCypherInputImpl(prevHash.convertTo[String], outputIndex.convertTo[Int],
        outputValue.convertTo[Long],
        script.convertTo[String],
        sequence.convertTo[Long],
        addressList, scriptType.convertTo[String])
    }


    override def write(input : BlockCypherInput) : JsValue = {
      import org.scalacoin.marshallers.BitcoinAddressProtocol._
      val addresses : Vector[JsValue] = input.addresses.map(p =>
        bitcoinAddressFormat.write(p))(breakOut): Vector[JsValue]
      val m : Map[String,JsValue] = Map(
        prevHashKey -> JsString(input.prevHash),
        outputIndexKey -> JsNumber(input.outputIndex),
        outputValueKey -> JsNumber(input.outputValue),
        scriptKey -> JsString(input.script),
        sequenceKey -> JsNumber(input.sequence),
        addressesKey -> JsArray(addresses),
        scriptTypeKey -> JsString(input.scriptType)
      )

      JsObject(m)

    }

    private def convertToAddressList(value : JsValue) : Seq[BitcoinAddress] = {
      value match {
        case ja: JsArray => {
          ja.elements.toList.map(e => BitcoinAddress(e.convertTo[String]))
        }
        case _ => throw new RuntimeException("This Json type is not valid for parsing a list of bitcoin addresses")
      }
    }
  }
}
