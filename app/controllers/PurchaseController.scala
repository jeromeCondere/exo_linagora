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
import models._

class PurchaseController @Inject() (ws: WSClient)(implicit context: ExecutionContext) extends Controller{
  
 
   def getRecentPurchases(username: String) = Action.async {
     //on recupère les données sur les achats dans une liste
    val request: WSRequest = ws.url("http://localhost:8000/api/purchases/by_user/"+username+"?limit=5");
    val futureListPurchasesUser =request.get().map { reponse => ListPurchaseMaker.MakeListPurchases(reponse.json) }
     
    val resultAllRequest= futureListPurchasesUser.map {
       list_purchase_user => 
     val listFutureJson =     list_purchase_user.map {
           // pour chaque achat on recupere les personnes ayant à voir avec cet achat
           purchase => val JsResultPurchase = constructPurchaseObject(purchase);
           
           JsResultPurchase
      
         }
       
         // on cree un JsArray à Partir de la liste des données sur les achats
  val futureListJson=  Future.sequence(listFutureJson).map {
    listJson =>  TransformToJsonArray(listJson)
    }
  
    futureListJson.map { x =>  
        
       }
     futureListJson
     }
    
    resultAllRequest.flatMap(identity).map { 
      x =>  var result: JsObject = Json.obj("recent_purchases" -> x);
         Ok(Json.prettyPrint(result.as[JsValue]));
        
    }
     
  }
   def TransformToJsonArray(list: List[JsObject]): JsArray = {
    list.foldLeft(JsArray())((acc, x) => acc ++ Json.arr(x))
  }
   def constructPurchaseObject(p:Purchase): Future[JsObject]={
     val id_product = p.id_product;
     val productInfo =  ws.url("http://localhost:8000/api/products/"+ id_product).get();
     
    val result= (productInfo.map 
     { 
       product_info => val product_json=product_info.json
       var singleResult: JsObject = product_json.as[JsObject];
       singleResult= singleResult ++ Json.obj("recent" -> Json.arr());
       val purchase_product= getPurchasesForSingleProduct(id_product);
       
      val futureResultJson= purchase_product.map { 
            purchase_for_product_list =>
                   
             val user_array: JsArray =( singleResult \ "recent").as[JsArray] ;
             purchase_for_product_list.foreach {
             purchase => val user_array_append=user_array.append(JsString(purchase.user));
             //on met à jour l'array
              singleResult= singleResult++Json.obj("recent" -> user_array_append);
              
                     }
      val singleResultJson=singleResult;
      singleResultJson
     }
     futureResultJson
   }).flatMap { identity }
   
    result
   }
   
   
  
    def getPurchasesForSingleProduct(id: Int) : Future[List[Purchase]] = 
    {
      val request: WSRequest = ws.url("http://localhost:8000/api/purchases/by_product/"+id);
      
      val futureResult : Future[List[Purchase]] = request.get().map 
      { requestResult => 
        val jsonObj = requestResult.json;
        ListPurchaseMaker.MakeListPurchases(jsonObj);
      }
      futureResult;
    }
    

   // def makeJson(product
}