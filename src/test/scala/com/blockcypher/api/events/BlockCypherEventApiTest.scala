package com.blockcypher.api.events

import com.blockcypher.api.config.{BlockCypherTestNet, ActorSystemConfig}
import com.blockcypher.api.util.TestUtil
import org.scalacoin.protocol.BitcoinAddress
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, MustMatchers}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import spray.json._
import DefaultJsonProtocol._
import spray.httpx.SprayJsonSupport._
/**
 * Created by chris on 12/2/15.
 */
class BlockCypherEventApiTest extends FlatSpec with MustMatchers
  with ScalaFutures with BlockCypherEventApi with BlockCypherTestNet with ActorSystemConfig {

  val txConfirmationResponse =
    """
      |{"block_hash":"00000000000dccabccac0d795b3d3faf11023e01a2de1fd94c6f30db80aba5e8",
      |"block_height":626996,"block_index":10,"hash":"fbce5568e05cccf5e2caf25f85ed5a61e8899f2d382cd41d8a949132719ef3e4",
      |"addresses":["mhgDFW3fh6QTm5j1BtUEaUFjregYkqqcNh","mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq",
      |"mtQLgLiqmytKkgE9sVGwypAFsLvkxBQ6XX"],"total":7554642,"fees":22679,"size":225,"preference":"high",
      |"relayed_by":"148.251.92.108:18333","confirmed":"2015-12-19T20:06:58Z","received":"2015-12-19T19:47:36.956Z",
      |"ver":1,"lock_time":626985,"double_spend":false,"vin_sz":1,"vout_sz":2,"confirmations":2,
      |"confidence":0,"inputs":[{"prev_hash":"679efcfecadfbcca283c8f307ba703199662de48c3f1fbf8bd4489d05b403f40",
      |"output_index":1,
      |"script":"47304402204b8e1053639878b579442ab0a5e8f060fffe8752774f775bde6b481ebbe5d80e0220684b85a0cbe0950017900f014b8b29cafba3e8e3e92602d7aeda2e64064cba5701210381c82dc267a958be06f1c920dc635bcd191d698c167e67a45a882a551c57ce1d",
      |"output_value":7577321,"sequence":4294967294,"addresses":["mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq"],
      |"script_type":"pay-to-pubkey-hash"}],"outputs":[{"value":7454642,
      |"script":"76a9148d5968ad26f9e277849ff9f8f39920f28944467388ac",
      |"addresses":["mtQLgLiqmytKkgE9sVGwypAFsLvkxBQ6XX"],"script_type":"pay-to-pubkey-hash"},
      |{"value":100000,"script":"76a91417b07ad7f6303e381b856034001bacf2c750e9ac88ac",
      |"addresses":["mhgDFW3fh6QTm5j1BtUEaUFjregYkqqcNh"],"script_type":"pay-to-pubkey-hash"}]}
    """.stripMargin
  import actorSystem._
  "BlockCypherEventApi" must "query the example from blockcypher's api docs" in {
    //'{"event": "unconfirmed-tx",
    // "address": "15qx9ug952GWGTNn7Uiv6vode4RcGrRemh", "url": "https://my.domain.com/callbacks/new-tx"}'
    val event = BlockCypherEventImpl(None,"unconfirmed-tx",None, None, None,
      Some(BitcoinAddress("15qx9ug952GWGTNn7Uiv6vode4RcGrRemh")),None,None,None,Some("https://my.domain.com/callbacks/new-tx"),0)

    val result : Future[BlockCypherEvent] = sendEvent(event)

    whenReady(result, timeout( 5 seconds), interval(5 millis)) { event =>
      event.address must be (Some(TestUtil.bitcoinAddress))
      event.url must be (Some("https://my.domain.com/callbacks/new-tx"))
      event.event must be ("unconfirmed-tx")

    }
  }

  it must "notify us when an address transaction receives another confirmation" in {
    val address = BitcoinAddress("15qx9ug952GWGTNn7Uiv6vode4RcGrRemh")
    val result : Future[BlockCypherEvent] = txConfirmation(address)

    whenReady(result, timeout(5 seconds), interval(5 millis)) { event =>
      event.address must be (Some(address))
      event.confirmations must be (Some(3))
    }
  }


  it must "parse a tx-confirmation response from the blockcypher api" in {
    import com.blockcypher.api.marshallers.BlockCypherEventProtocol._
    val json = txConfirmationResponse.parseJson
    val event : BlockCypherEvent = BlockCypherFormat.read(json)
    event.address.get must be ("mhgDFW3fh6QTm5j1BtUEaUFjregYkqqcNh")

  }

}
