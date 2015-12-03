package com.blockcypher.api.config

/**
 * Created by chris on 12/2/15.
 */
trait BlockCypherEnvironment {

  def token = ???
  private def blockCypherUrl = "https://api.blockcypher.com/"
  def version = "v1/"
  def coin : String
  def network : String

  def baseUrl = blockCypherUrl + version + coin + network

  def webHooks = baseUrl + "/hooks?token="+ token
}


trait BlockCypherMainNet extends BlockCypherEnvironment {
  override def coin = "btc/"
  override def network = "main"
}

trait BlockCypherTestNet extends BlockCypherEnvironment {
  override def coin = "btc/"
  override def network = "test3"
}