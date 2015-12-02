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
   * Simplifies listening to confirmations on all transactions for a given address up to a provided threshold. S
   * ends first the unconfirmed transaction and then the transaction for each confirmation.
   * Use the confirmations property within the Event to manually specify the number of confirmations desired
   * (maximum 10, defaults to 6).
   * The payload is a TX.
   * @param tx
   * @return
   */
  def txConfirmation(event : BlockCypherEventImpl) = {
    import BlockCypherEventProtocol._
    val pipeline: HttpRequest => Future[BlockCypherEventImpl] = sendReceive ~> unmarshal[BlockCypherEventImpl]
    pipeline(Post(webHooks,event))
  }
}
