package models
import java.text.SimpleDateFormat
import java.util.Calendar
import play.api.libs.json._
import play.api.libs.functional.syntax._


case class Purchase(id_pur:Int , id_prod: Int,username: String, date: String) {
  var id: Int = id_pur;
  var id_product: Int = id_prod;
  var user: String = username;
  var DatePurchase: String = date;
  
  
  
}

object ListPurchaseMaker
{

   
  def MakeListPurchases(json:JsValue): List[Purchase] = {
    
   implicit val purchaseReads: Reads[models.Purchase] = (
  (JsPath \ "id").read[Int] and
  (JsPath \ "productId").read[Int] and
  (JsPath \ "username").read[String] and
  (JsPath \ "date").read[String]
   )(Purchase.apply _)
   
    val l =List();
    val ListPurchase = (json \ "purchases").as[List[Purchase]];
    
    return ListPurchase;
  }
}
  
