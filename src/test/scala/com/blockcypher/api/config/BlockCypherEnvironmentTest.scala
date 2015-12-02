package com.blockcypher.api.config

import org.scalatest.{FlatSpec, MustMatchers}

/**
 * Created by chris on 12/2/15.
 */
class BlockCypherEnvironmentTest extends FlatSpec with MustMatchers with BlockCypherMainNet {


  "BlockCypherEnvironment" must "build a correct base url" in {
    val expectedUrl = "https://api.blockcypher.com/v1/btc/main"
    baseUrl must be (expectedUrl)
  }

  it must "build a correct web hook url" in {
    val expectedUrl = "https://api.blockcypher.com/v1/btc/main/hooks?token="

    webHooks must be (expectedUrl)
  }

}
