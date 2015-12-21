package com.blockcypher.api.protocol

import org.scalacoin.currency.{CurrencyUnit}
import org.scalacoin.protocol.BitcoinAddress

/**
 * Created by chris on 12/19/15.
 */
trait BlockCypherInput {
  def prevHash : String
  def outputIndex : Int
  def outputValue : CurrencyUnit
  def script : String
  def sequence : Long
  def addresses : Seq[BitcoinAddress]
  def scriptType : String
}

case class BlockCypherInputImpl(override val prevHash : String, override val outputIndex : Int,
                                override val outputValue : CurrencyUnit, override val script : String,
                                override val sequence : Long,
                                override val addresses : Seq[BitcoinAddress], override val scriptType : String)
  extends BlockCypherInput
