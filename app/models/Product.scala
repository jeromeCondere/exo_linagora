package models

import javax.inject.Inject
import scala.concurrent.Future
import scala.concurrent.duration._

import play.api.mvc._
import play.api.libs.ws._
import play.api.http.HttpEntity

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString
import play.api.libs.json._
class Product (id_product:Int,face_p: String,price_p:Int)
{
  var id:Int =id_product;
  var face:String =face_p;
  var price:Int =price_p;
  
}