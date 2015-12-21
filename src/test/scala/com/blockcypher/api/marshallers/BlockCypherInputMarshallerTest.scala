package com.blockcypher.api.marshallers

import com.blockcypher.api.protocol.BlockCypherInput
import org.scalacoin.currency.Satoshis
import org.scalacoin.protocol.BitcoinAddress
import org.scalatest.{FlatSpec, MustMatchers}

import spray.json._

/**
 * Created by chris on 12/20/15.
 */
class BlockCypherInputMarshallerTest extends FlatSpec with MustMatchers {


  val expectedInput = """{"prev_hash":"679efcfecadfbcca283c8f307ba703199662de48c3f1fbf8bd4489d05b403f40",
                        |"output_index":1,
                        |"script":"47304402204b8e1053639878b579442ab0a5e8f060fffe8752774f775bde6b481ebbe5d80e0220684b85a0cbe0950017900f014b8b29cafba3e8e3e92602d7aeda2e64064cba5701210381c82dc267a958be06f1c920dc635bcd191d698c167e67a45a882a551c57ce1d",
                        |"output_value":7577321,"sequence":4294967294,"addresses":["mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq"],
                        |"script_type":"pay-to-pubkey-hash"}""".stripMargin

  val json = expectedInput.parseJson
  "BlockCypherInputMarshaller" must "parse a input received from blockcypher's api" in {
    val input : BlockCypherInput  = BlockCypherInputMarshaller.BlockCypherInputFormatter.read(json)
    input.prevHash must be ("679efcfecadfbcca283c8f307ba703199662de48c3f1fbf8bd4489d05b403f40")
    input.outputIndex must be (1)
    input.script must be ("47304402204b8e1053639878b579442ab0a5e8f060fffe8752774f775bde6b481ebbe5d80e0220684b85a0cbe0950017900f014b8b29cafba3e8e3e92602d7aeda2e64064cba5701210381c82dc267a958be06f1c920dc635bcd191d698c167e67a45a882a551c57ce1d")
    input.outputValue must be (Satoshis(7577321))
    input.sequence must be ("4294967294".toLong)
    input.addresses must be (Seq(BitcoinAddress("mokS58yDd4DwC4AESQavBfP6EVg9TX6jjq")))
    input.scriptType must be ("pay-to-pubkey-hash")


  }
}
