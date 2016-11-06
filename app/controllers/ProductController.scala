package controllers

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
import scala.concurrent.ExecutionContext



class ProductController @Inject() (ws: WSClient)(implicit context: ExecutionContext) extends Controller{
  
   
  def listAllProducts = Action.async {
    val request: WSRequest = ws.url("http://localhost:8000/api/products/");
   
    request.get().map {
       rep => Ok(Json.prettyPrint(rep.json));
      };
    
  }
  
  def getProductById(id: Int) = Action.async{
    val request: WSRequest = ws.url("http://localhost:8000/api/products/"+id);
   
    request.get().map {
       rep => Ok(Json.prettyPrint(rep.json));
      };
  }
  
  def getProductInfoById(id: Int): Future[JsValue] = {
    val request: WSRequest = ws.url("http://localhost:8000/api/products/"+id);
   
    request.get().map {
       rep => rep.json;
      };
  }
  
  
  
  
  
}