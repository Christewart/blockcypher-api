package com.blockcypher.api.marshallers

import com.blockcypher.api.protocol.BlockCypherOutput
import org.scalacoin.currency.Satoshis
import org.scalacoin.protocol.BitcoinAddress
import org.scalatest.{FlatSpec, MustMatchers}
import spray.json._
/**
 * Created by chris on 12/20/15.
 */
class BlockCypherOutputMarshallerTest extends FlatSpec with MustMatchers {

  val expectedOutput = """{"value":7454642,
                         |"script":"76a9148d5968ad26f9e277849ff9f8f39920f28944467388ac",
                         |"addresses":["mtQLgLiqmytKkgE9sVGwypAFsLvkxBQ6XX"],
                         |"script_type":"pay-to-pubkey-hash"}""".stripMargin
  val json = expectedOutput.parseJson
  "BlockCypherOutputMarshaller" must "parse an output from the blockcypher api" in {

    val output : BlockCypherOutput = BlockCypherOutputMarshaller.BlockCypherOutputFormat.read(json)
    output.value must be (Satoshis(7454642))
    output.script must be ("76a9148d5968ad26f9e277849ff9f8f39920f28944467388ac")
    output.addresses must be (Seq(BitcoinAddress("mtQLgLiqmytKkgE9sVGwypAFsLvkxBQ6XX")))
    output.scriptType must be ("pay-to-pubkey-hash")
  }
}
