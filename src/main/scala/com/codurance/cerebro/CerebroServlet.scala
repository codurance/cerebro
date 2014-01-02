package com.codurance.cerebro

import javax.servlet.http.HttpServletResponse
import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeTokenRequest, GoogleTokenResponse}
import com.google.api.client.json.jackson.JacksonFactory
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.gson.Gson
import scala.Predef._

class CerebroServlet extends CerebroStack {
  val CLIENT_ID : String = "21429338001-c3csvh6qicntvcsrm86360fdv3o173v8.apps.googleusercontent.com"
  val CLIENT_SECRET = "Rno1hfGUTTe4byL7IsnHCv8l"
  val APPLICATION_NAME = "Cerebro"
  val JSON_FACTORY = new JacksonFactory()
  val TRANSPORT = new NetHttpTransport()

  get("/hello") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }


  post("/authenticate") {
    val authCode:String = params.getOrElse("authCode", halt(400))
    val tokenResponse: GoogleTokenResponse =
      new GoogleAuthorizationCodeTokenRequest(
        TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, authCode, "postmessage"
      ).execute

    request.getSession.setAttribute("token", tokenResponse.toString)
    response.setStatus(HttpServletResponse.SC_OK)
    response.getWriter.print(new Gson().toJson("Successfully connected user: " + tokenResponse.getAccessToken))
  }
}
