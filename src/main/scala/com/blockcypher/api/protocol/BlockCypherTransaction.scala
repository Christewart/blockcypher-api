package com.blockcypher.api.protocol

import org.joda.time.DateTime
import org.scalacoin.protocol.BitcoinAddress

/**
 * Created by chris on 12/19/15.
 */
trait BlockCypherTransaction {

  def blockHash : Option[String]
  def blockHeight : Option[Long]
  def hash : String
  def addresses : Seq[BitcoinAddress]
  def total : Long
  def fees : Long
  def size : Long
  def preference : String
  def relayedBy : String
  def confirmed : Option[DateTime]
  def received : DateTime
  def ver : Int
  def lockTime : Long
  def doubleSpend : Boolean
  def vinSize : Int
  def voutSize : Int
  def confirmations : Int
  def confidence : Option[Double]
  def inputs : Seq[BlockCypherInput]
  def outputs : Seq[BlockCypherOutput]

}

case class BlockCypherTransactionImpl(override val blockHash : Option[String], override val blockHeight : Option[Long],
  override val hash : String, override val addresses : Seq[BitcoinAddress], override val total : Long,
  override val fees : Long, override val size : Long, override val preference : String,
  override val relayedBy : String, override val received : DateTime, override val confirmed : Option[DateTime],
  override val ver : Int, override val lockTime : Long, override val doubleSpend : Boolean,
  override val vinSize : Int, override val voutSize : Int, override val confirmations : Int,
  override val confidence : Option[Double], override val inputs : Seq[BlockCypherInput],
  override val outputs : Seq[BlockCypherOutput]) extends BlockCypherTransaction