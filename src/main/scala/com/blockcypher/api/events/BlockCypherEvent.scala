package com.blockcypher.api.events

import org.scalacoin.protocol.BitcoinAddress

/**
 * Created by chris on 12/2/15.
 */
trait BlockCypherEvent {
  def id : Option[String]
  def event : String
  def hash : Option[String]
  def walletName : Option[String]
  def token : Option[String]
  def address : Option[BitcoinAddress]
  def confirmations : Option[Int]
  def confidence : Option[Float]
  def script : Option[String]
  def url : Option[String]
  def callbackErrors : Int
}

case class BlockCypherEventImpl(override val id : Option[String], override val event : String, override val hash : Option[String],
  override val walletName : Option[String], override val token : Option[String], override val address : Option[BitcoinAddress],
  override val confirmations : Option[Int], override val confidence : Option[Float],
  override val script : Option[String], override val url : Option[String],
  override val callbackErrors : Int) extends BlockCypherEvent
