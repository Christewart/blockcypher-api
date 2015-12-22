package com.blockcypher.api.events

import akka.actor.ActorSystem
import com.blockcypher.api.config.{ActorSystemConfig, BlockCypherEnvironment}
import com.blockcypher.api.marshallers.BlockCypherEventProtocol
import org.scalacoin.protocol.BitcoinAddress
import spray.client.pipelining._
import spray.http.HttpRequest
import spray.httpx.SprayJsonSupport._
import scala.concurrent.Future

/**
 * Created by chris on 12/2/15.
 */
trait BlockCypherEventApi { this : BlockCypherEnvironment with ActorSystemConfig =>
  import actorSystem._


  /**
   * Simplifies listening to confirmations on all transactions for a given address up to a provided threshold.
   * Sends first the unconfirmed transaction and then the transaction for each confirmation.
   * Use the confirmations property within the Event to manually specify the number of confirmations desired
   * (maximum 10, defaults to 6).
   * The payload is a TX.
   * @param tx
   * @return
   */
  def txConfirmation(address : BitcoinAddress, callBackUrl : String, numberOfConfirmations : Int = 3) : Future[BlockCypherEvent] = {
    val event = BlockCypherEventImpl(None,"tx-confirmation",None,None,Some(token),Some(address),
      Some(numberOfConfirmations),None,None,Some(callBackUrl),0)
    sendEvent(event)
  }


  /**
   * Sends the event to blockcypher and returns the response
   * @param event
   * @return
   */
  def sendEvent(event : BlockCypherEventImpl) = {
    import BlockCypherEventProtocol._
    val pipeline: HttpRequest => Future[BlockCypherEventImpl] = sendReceive ~> unmarshal[BlockCypherEventImpl]
    pipeline(Post(webHooks,event))
  }
}
