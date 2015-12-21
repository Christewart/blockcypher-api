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
      |   "block_hash":"00000000000dccabccac0d795b3d3faf11023e01a2de1fd94c6f30db80aba5e8",
      |   "block_height":626996,
      |   "block_index":10,
      |   "hash":"fbce5568e05cccf5e2caf25f85ed5a61e8899f2d382cd41d8a949132719ef3e4",
      |   "addresses":[
      |      "mhgDFW3fh6QTm5j1BtUEaUFjregYkqqcNh",
      |      "mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq",
      |      "mtQLgLiqmytKkgE9sVGwypAFsLvkxBQ6XX"
      |   ],
      |   "total":7554642,
      |   "fees":22679,
      |   "size":225,
      |   "preference":"high",
      |   "relayed_by":"148.251.92.108:18333",
      |   "confirmed":"2015-12-19T20:06:58Z",
      |   "received":"2015-12-19T19:47:36.956Z",
      |   "ver":1,
      |   "lock_time":626985,
      |   "double_spend":false,
      |   "vin_sz":1,
      |   "vout_sz":2,
      |   "confirmations":2,
      |   "confidence":0,
      |   "inputs":[
      |      {
      |         "prev_hash":"679efcfecadfbcca283c8f307ba703199662de48c3f1fbf8bd4489d05b403f40",
      |         "output_index":1,
      |         "script":"47304402204b8e1053639878b579442ab0a5e8f060fffe8752774f775bde6b481ebbe5d80e0220684b85a0cbe0950017900f014b8b29cafba3e8e3e92602d7aeda2e64064cba5701210381c82dc267a958be06f1c920dc635bcd191d698c167e67a45a882a551c57ce1d",
      |         "output_value":7577321,
      |         "sequence":4294967294,
      |         "addresses":[
      |            "mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq"
      |         ],
      |         "script_type":"pay-to-pubkey-hash"
      |      }
      |   ],
      |   "outputs":[
      |      {
      |         "value":7454642,
      |         "script":"76a9148d5968ad26f9e277849ff9f8f39920f28944467388ac",
      |         "addresses":[
      |            "mtQLgLiqmytKkgE9sVGwypAFsLvkxBQ6XX"
      |         ],
      |         "script_type":"pay-to-pubkey-hash"
      |      },
      |      {
      |         "value":100000,
      |         "script":"76a91417b07ad7f6303e381b856034001bacf2c750e9ac88ac",
      |         "addresses":[
      |            "mhgDFW3fh6QTm5j1BtUEaUFjregYkqqcNh"
      |         ],
      |         "script_type":"pay-to-pubkey-hash"
      |      }
      |   ]
      |}
    """.stripMargin

  val json = expectedTx.parseJson

  "BlockCypherTransactionMarshaller" must "parse a transaction from the blockcypher api" in {
    val tx : BlockCypherTransaction = BlockCypherTransactionMarshaller.BlockCypherTransactionFormat.read(json)
    tx.blockHeight  must equal (Some(626996))
    tx.blockHash must be (Some("00000000000dccabccac0d795b3d3faf11023e01a2de1fd94c6f30db80aba5e8"))
    tx.addresses must be (Seq(BitcoinAddress("mhgDFW3fh6QTm5j1BtUEaUFjregYkqqcNh"),
      BitcoinAddress("mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq"), BitcoinAddress("mtQLgLiqmytKkgE9sVGwypAFsLvkxBQ6XX")))
    tx.total must be (7554642)
    tx.fees must be (22679)
    tx.size must be (225)
    tx.preference must be ("high")
    tx.relayedBy must be ("148.251.92.108:18333")

    tx.received.getDayOfMonth must be (19)
    tx.received.getMonthOfYear must be (DateTimeConstants.DECEMBER)
    tx.received.getHourOfDay must be (19)

    tx.confirmed.get.getDayOfMonth must be (19)
    tx.confirmed.get.getHourOfDay must be (20)
    tx.confirmed.get.getMonthOfYear must be (DateTimeConstants.DECEMBER)
    tx.ver must be (1)
    tx.lockTime must be(626985)
    tx.doubleSpend must be (false)
    tx.vinSize must be (1)
    tx.voutSize must be (2)
    tx.confirmations must be (2)




  }
}
