package com.blockcypher.api.events

import com.blockcypher.api.config.{BlockCypherTestNet, ActorSystemConfig}
import com.blockcypher.api.util.TestUtil
import org.scalacoin.protocol.BitcoinAddress
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, MustMatchers}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
/**
 * Created by chris on 12/2/15.
 */
class BlockCypherEventApiTest extends FlatSpec with MustMatchers
  with ScalaFutures with BlockCypherEventApi with BlockCypherTestNet with ActorSystemConfig {
  import actorSystem._
  "BlockCypherEventApi" must "query the example from blockcypher's api docs" in {
    //'{"event": "unconfirmed-tx",
    // "address": "15qx9ug952GWGTNn7Uiv6vode4RcGrRemh", "url": "https://my.domain.com/callbacks/new-tx"}'
    val event = BlockCypherEventImpl(None,"unconfirmed-tx",None, None, None,
      Some(BitcoinAddress("15qx9ug952GWGTNn7Uiv6vode4RcGrRemh")),None,None,None,Some("https://my.domain.com/callbacks/new-tx"),0)

    val result : Future[BlockCypherEvent] = txConfirmation(event)

    whenReady(result, timeout( 5 seconds), interval(5 millis)) { event =>
      event.address must be (Some(TestUtil.bitcoinAddress))
      event.url must be (Some("https://my.domain.com/callbacks/new-tx"))
      event.event must be ("unconfirmed-tx")

    }
  }

}
