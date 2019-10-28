package com.jimmy.learning

import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.alpakka.slick.scaladsl._
import akka.stream.scaladsl._
import slick.jdbc.GetResult
import slick.lifted

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

object App {

  implicit val system = ActorSystem()
  implicit val ec = system.dispatcher
  implicit val mat = ActorMaterializer()

  implicit val session = SlickSession.forConfig("slick-postgres")

  import session.profile.api._
  system.registerOnTermination(() => session.close())


    class Users(tag: Tag) extends Table[(Int, String)](tag, "test_table") {
      def id = column[Int]("id")
      def name = column[String]("name")
      def * = (id, name)
    }

  case class User(id: Int, name: String)

  val users = (1 to 4).map(i => User(i, s"Name$i"))

  implicit val getUserResult = GetResult(r => User(r.nextInt, r.nextString))

  def main(args: Array[String]): Unit = {

    val done: Future[Done] =
      Source(users)
        .runWith(Slick.sink(user => sqlu"INSERT INTO test_table VALUES(${user.id}, ${user.name})"))

    done.onComplete {
      case Success(value) => {
        println("Returned: " + value)
        system.terminate
      }
      case Failure(e) => {
        throw new Exception(s"Failed with: $e")
        system.terminate
      }
    }
  }
}
