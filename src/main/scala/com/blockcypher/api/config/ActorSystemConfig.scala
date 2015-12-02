package com.blockcypher.api.config

import akka.actor.ActorSystem
import akka.util.Timeout
import scala.concurrent.duration.DurationInt
/**
 * Created by chris on 12/2/15.
 */
trait ActorSystemConfig {
  implicit val timeout = Timeout(5 seconds)
  implicit lazy val actorSystem = ActorSystem("BlockCypher-Api")
}
