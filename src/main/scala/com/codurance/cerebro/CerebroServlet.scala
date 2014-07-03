package com.codurance.cerebro

import java.net.URL
import javax.servlet.http.HttpServletResponse
import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeTokenRequest, GoogleTokenResponse}
import com.google.api.client.json.jackson.JacksonFactory
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.gson.Gson
import scala.Predef._

import com.stackmob.newman._
import com.stackmob.newman.dsl._

import scala.concurrent.Await
import scala.concurrent.duration._


class CerebroServlet extends CerebroStack {

	implicit val httpClient = new ApacheHttpClient

	val CLIENT_ID: String = "1091582186456-sluhikl6ifksd1iifc8h3m5837dr1aq8.apps.googleusercontent.com"
	val CLIENT_SECRET = "RZbRAJz3oG6IbcIsazrrYa-C"
	val API_KEY = "AIzaSyDdwYp7F4NutbxQHEnBaR_mGrUnc_6WwfE"
	val APPLICATION_NAME = "Cerebro"
	val JSON_FACTORY = new JacksonFactory()
	val TRANSPORT = new NetHttpTransport()


	get("/") {
		contentType = "text/html"
		jade("hello")
	}

	get("/signin") {
		contentType = "text/html"
		jade("signin", "originalUri" -> request.getParameter("originalUri"))
	}

	get("/non-authorised") {
		contentType = "text/html"
		jade("non-authorised")
	}

	post("/authenticate") {
		val authCode: String = params.getOrElse("authCode", halt(400))
		println("authCode: " + authCode)
		val tokenResponse: GoogleTokenResponse =
			new GoogleAuthorizationCodeTokenRequest(
				TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, authCode, "postmessage"
			).execute
		println("tokenResponse: " + tokenResponse)
		println("******** ACCESS TOKEN *******: " + tokenResponse.getAccessToken)
		request.getSession.setAttribute("token", tokenResponse.toString)
		println("*** authenticate")
		val url = new URL("https://www.googleapis.com/plus/v1/people/me?fields=aboutMe%2Ccover%2FcoverPhoto%2CdisplayName%2Cdomain%2Cemails%2Clanguage%2Cname&access_token=" + tokenResponse.getAccessToken)
		val userInfo = Await.result(GET(url).apply, 10.seconds) //this will throw if the response doesn't return within 1 second
		println("userInfo: " + userInfo.bodyString)
//		response.setStatus(HttpServletResponse.SC_OK)
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
		response.getWriter.print(new Gson().toJson("Successfully connected user: " + tokenResponse.getAccessToken))

	}


}
