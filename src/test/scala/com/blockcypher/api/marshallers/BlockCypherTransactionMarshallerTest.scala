package com.blockcypher.api.marshallers

import com.blockcypher.api.protocol.BlockCypherTransaction
import org.joda.time.DateTimeConstants
import org.scalacoin.protocol.BitcoinAddress
import org.scalatest.{MustMatchers, FlatSpec}
import spray.json._
/**
 * Created by chris on 12/21/15.
 */
class BlockCypherTransactionMarshallerTest extends FlatSpec with MustMatchers {

  val expectedTx =
    """
      |{
      |  "block_height": -1,
      |  "hash": "4e6dfb1415b4fba5bd257c129847c70fbd4e45e41828079c4a282680528f3a50",
      |  "addresses": [
      |    "mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq",
      |    "mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq"
      |  ],
      |  "total": 4988000,
      |  "fees": 12000,
      |  "size": 226,
      |  "preference": "high",
      |  "relayed_by": "73.162.198.68",
      |  "received": "2015-05-22T05:10:00.305308666",
      |  "ver": 1,
      |  "lock_time": 0,
      |  "double_spend": false,
      |  "vin_sz": 1,
      |  "vout_sz": 2,
      |  "confirmations": 0,
      |  "inputs": [
      |    {
      |      "prev_hash": "c8ea8b221580ebb2f1cabc8b40797bffec742b97c82a329df96d93121db43519",
      |      "output_index": 0,
      |      "script": "483045022100921fc36b911094280f07d8504a80fbab9b823a25f102e2bc69b14bcd369dfc7902200d07067d47f040e724b556e5bc3061af132d5a47bd96e901429d53c41e0f8cca012102152e2bb5b273561ece7bbe8b1df51a4c44f5ab0bc940c105045e2cc77e618044",
      |      "output_value": 5000000,
      |      "sequence": 4294967295,
      |      "addresses": [
      |        "mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq"
      |      ],
      |      "script_type": "pay-to-pubkey-hash",
      |      "age": 576
      |    }
      |  ],
      |  "outputs": [
      |    {
      |      "value": 1000000,
      |      "script": "76a9145fb1af31edd2aa5a2bbaa24f6043d6ec31f7e63288ac",
      |      "addresses": [
      |        "mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq"
      |      ],
      |      "script_type": "pay-to-pubkey-hash"
      |    },
      |    {
      |      "value": 3988000,
      |      "script": "76a914efec6de6c253e657a9d5506a78ee48d89762fb3188ac",
      |      "addresses": [
      |        "mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq"
      |      ],
      |      "script_type": "pay-to-pubkey-hash"
      |    }
      |  ]
      |}
    """.stripMargin

  val json = expectedTx.parseJson

  "BlockCypherTransactionMarshaller" must "parse a transaction from the blockcypher api" in {
    val tx : BlockCypherTransaction = BlockCypherTransactionMarshaller.BlockCypherTransactionFormat.read(json)
    tx.blockHeight  must equal (None)
    tx.blockHash must be (None)
    tx.addresses must be (Seq(BitcoinAddress("mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq"), BitcoinAddress("mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq")))
    tx.total must be (4988000)
    tx.fees must be (12000)
    tx.size must be (226)
    tx.preference must be ("high")
    tx.relayedBy must be ("73.162.198.68")
    tx.received.getDayOfMonth must be (22)
    tx.received.getMonthOfYear must be (DateTimeConstants.MAY)
    tx.received.getHourOfDay must be (5)

    tx.confirmed must be (None)
    tx.ver must be (1)
    tx.lockTime must be(0)
    tx.doubleSpend must be (false)
    tx.vinSize must be (1)
    tx.voutSize must be (2)
    tx.confirmations must be (0)




  }
}
