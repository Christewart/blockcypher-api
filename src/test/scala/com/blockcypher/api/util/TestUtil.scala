package com.blockcypher.api.util

import com.blockcypher.api.events.BlockCypherEventImpl
import org.scalacoin.protocol.BitcoinAddress

/**
 * Created by chris on 12/2/15.
 */
object TestUtil {
  def event = BlockCypherEventImpl(Some("id"),"event",None,None,None,None,None,None,None,None,0)
  def bitcoinAddress = BitcoinAddress("15qx9ug952GWGTNn7Uiv6vode4RcGrRemh")
}
