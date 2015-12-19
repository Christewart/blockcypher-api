package com.blockcypher.api.protocol

import org.scalacoin.protocol.BitcoinAddress

/**
 * Created by chris on 12/19/15.
 */
trait BlockCypherOutput {

  def value : Long
  def script : String
  def addresses : Seq[BitcoinAddress]
  def scriptType : String
}

case class BlockCypherOutputImpl(override val value : Long, override val script : String,
  override val addresses : Seq[BitcoinAddress], override val scriptType : String) extends BlockCypherOutput
