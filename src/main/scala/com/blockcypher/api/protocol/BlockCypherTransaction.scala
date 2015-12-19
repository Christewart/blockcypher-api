package com.blockcypher.api.protocol

import org.joda.time.DateTime
import org.scalacoin.protocol.BitcoinAddress

/**
 * Created by chris on 12/19/15.
 */
trait BlockCypherTransaction {

  def blockHash : String
  def blockHeight : String
  def hash : String
  def addresses : Seq[BitcoinAddress]
  def total : Long
  def fees : Long
  def size : Long
  def preference : String
  def relayedBy : String
  def confirmed : DateTime
  def received : DateTime
  def ver : Int
  def lockTime : Long
  def doubleSpend : Boolean
  def vinSize : Int
  def voutSize : Int
  def confirmations : Int
  def confidence : Double

  def inputs : Seq[BlockCypherInput]

  def outputs : Seq[BlockCypherOutput]


}
