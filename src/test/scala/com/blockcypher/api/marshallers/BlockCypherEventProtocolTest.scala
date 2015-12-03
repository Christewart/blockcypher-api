package com.blockcypher.api.marshallers

import com.blockcypher.api.events.BlockCypherEvent
import org.scalacoin.protocol.BitcoinAddress
import org.scalatest.{MustMatchers, FlatSpec}
import spray.json._



/**
 * Created by chris on 12/2/15.
 */
class BlockCypherEventProtocolTest extends FlatSpec with MustMatchers {

  val json =
    """
      |{
      |  "id": "509aa259-ac56-4f31-96a9-69e2a681d018",
      |  "token": "bf678aeee7389499b233fa9cc351582c",
      |  "url": "https://my.domain.com/callbacks/new-tx",
      |  "callback_errors": 0,
      |  "address": "15qx9ug952GWGTNn7Uiv6vode4RcGrRemh",
      |  "event": "unconfirmed-tx",
      |  "filter": "addr=15qx9ug952GWGTNn7Uiv6vode4RcGrRemh\u0026event=unconfirmed-tx",
      |  "confirmations":3
      |}
    """.stripMargin
  "BlockCypherEventProtocol" must "parse a blockcypher event object" in {
    val jsonObject = json.parseJson
    val event : BlockCypherEvent = BlockCypherEventProtocol.BlockCypherFormat.read(jsonObject)
    event.callbackErrors must be (0)
    event.token must be (Some("bf678aeee7389499b233fa9cc351582c"))
    event.address must be (Some(BitcoinAddress("15qx9ug952GWGTNn7Uiv6vode4RcGrRemh")))
    event.event must be ("unconfirmed-tx")
    event.confirmations must be (Some(3))
  }
}
