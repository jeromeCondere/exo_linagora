package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.http.HttpEntity
import akka.util.{ ByteString, ByteStringBuilder }
import play.api.routing._




/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def show(page: String) = Action {
   Ok("It works!"+page)
  }

  def index = Action {
  Ok("bienvenue");
}
}


