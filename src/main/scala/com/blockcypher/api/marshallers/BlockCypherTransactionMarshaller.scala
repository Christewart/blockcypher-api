package com.blockcypher.api.marshallers

import com.blockcypher.api.protocol.{BlockCypherOutput, BlockCypherInput, BlockCypherTransactionImpl, BlockCypherTransaction}
import org.joda.time.DateTime
import org.scalacoin.marshallers.BitcoinAddressProtocol
import spray.json._
import BitcoinAddressProtocol._
import BlockCypherInputMarshaller._
import BlockCypherOutputMarshaller._
/**
 * Created by chris on 12/21/15.
 */
object BlockCypherTransactionMarshaller extends DefaultJsonProtocol with MarshallerUtil {
  implicit object BlockCypherTransactionFormat extends RootJsonFormat[BlockCypherTransaction] {

    override def read(value : JsValue) : BlockCypherTransaction = {
      val obj = value.asJsObject
      val fields = obj.fields
      val blockHash : Option[String] = if (fields.contains(blockHashKey)) Some(fields(blockHashKey).convertTo[String])
        else None
      val blockHeight : Option[Long] = if (!fields.contains(blockHeightKey)) None
        else if (fields(blockHeightKey).convertTo[Long] == -1) None
        else Some(fields(blockHeightKey).convertTo[Long])
      val hash = fields(hashKey).convertTo[String]
      val total = fields(totalKey).convertTo[Long]
      val fees = fields(feesKey).convertTo[Long]
      val size = fields(sizeKey).convertTo[Long]
      val preference = fields(preferenceKey).convertTo[String]
      val relayedBy = fields(relayedByKey).convertTo[String]
      val receivedDateTime : DateTime = parseDateTime(fields(receivedKey).convertTo[String])
      val confirmedDateTime : Option[DateTime] = if(fields.contains(confirmedKey))
        Some(parseDateTime(fields(confirmedKey).convertTo[String])) else None
      val ver = fields(verKey).convertTo[Int]
      val lockTime = fields(lockTimeKey).convertTo[Long]
      val doubleSpend = fields(doubleSpendKey).convertTo[Boolean]

      val vinSize = fields(vinSizeKey).convertTo[Int]

      val voutSize = fields(voutSizeKey).convertTo[Int]
      val confirmations = fields(confirmationsKey).convertTo[Int]
      val confidence : Option[Double] = if (fields.contains(confidenceKey))
        Some(fields(confidenceKey).convertTo[Double]) else None


      val addressList = convertToAddressList(fields(addressesKey))
      val inputs : Seq[BlockCypherInput] = obj.fields("inputs").convertTo[Seq[BlockCypherInput]]

      val outputs : Seq[BlockCypherOutput] = obj.fields("outputs").convertTo[Seq[BlockCypherOutput]]

      BlockCypherTransactionImpl(blockHash, blockHeight,
        hash, addressList, total, fees,
        size, preference, relayedBy,
        receivedDateTime, confirmedDateTime, ver, lockTime,
        doubleSpend, vinSize, voutSize,
        confirmations, confidence,inputs,outputs)
    }

    override def write(tx : BlockCypherTransaction) : JsValue = {
      val addressArray = convertToJsArray(tx.addresses)(BitcoinAddressProtocol.bitcoinAddressFormat)
      val inputs = convertToJsArray(tx.inputs)(BlockCypherInputMarshaller.BlockCypherInputFormatter)
      val outputs = convertToJsArray(tx.outputs)(BlockCypherOutputMarshaller.BlockCypherOutputFormat)
      val m : Map[String,JsValue] = Map(
        blockHashKey -> (if (tx.blockHash.isDefined) JsString(tx.blockHash.get) else JsNull),
        blockHeightKey -> (if(tx.blockHeight.isDefined) JsNumber(tx.blockHeight.get) else JsNull),
        hashKey -> JsString(tx.hash),
        addressesKey -> addressArray, totalKey -> JsNumber(tx.total), feesKey -> JsNumber(tx.fees),
        sizeKey -> JsNumber(tx.size), preferenceKey -> JsString(tx.preference), relayedByKey -> JsString(tx.relayedBy),
        receivedKey -> JsString(tx.received.toString),
        confirmedKey -> (if(tx.confirmed.isDefined) JsString(tx.confirmed.get.toString) else JsNull),
        verKey -> JsNumber(tx.ver), lockTimeKey -> JsNumber(tx.lockTime), doubleSpendKey -> JsBoolean(tx.doubleSpend),
        vinSizeKey -> JsNumber(tx.vinSize), voutSizeKey -> JsNumber(tx.voutSize),
        confirmationsKey -> JsNumber(tx.confirmations),
        confidenceKey -> (if (tx.confidence.isDefined) JsNumber(tx.confidence.get) else JsNull),
        inputsKey -> inputs, outputsKey -> outputs
        )

      JsObject(m)
    }




  }

}
