package com.codurance.cerebro

import java.net.URL
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.{SC_OK, SC_UNAUTHORIZED}
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
		jade("main", "user" -> request.getSession.getAttribute("user"))
	}

	get("/main") {
		contentType = "text/html"
		jade("main", "user" -> request.getSession.getAttribute("user"))
	}

	get("/signin") {
		contentType = "text/html"
		jade("signin", "originalUri" -> request.getParameter("originalUri"))
	}

	get("/not-authorised") {
		contentType = "text/html"
		jade("not-authorised")
	}

	post("/authorise") {
		val authCode: String = params.getOrElse("authCode", halt(400))
		val tokenResponse: GoogleTokenResponse =
			new GoogleAuthorizationCodeTokenRequest(
				TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, authCode, "postmessage"
			).execute
		val url = new URL("https://www.googleapis.com/plus/v1/people/me?fields=aboutMe%2Ccover%2FcoverPhoto%2CdisplayName%2Cdomain%2Cemails%2Clanguage%2Cname&access_token=" + tokenResponse.getAccessToken)
		val userInfo = Await.result(GET(url).apply, 10.seconds) //this will throw if the response doesn't return within 1 second
		val user = GooglePlusJSONResponseToUser.toUser(userInfo.bodyString)

		user.domain match {
			case Some(Domain("codurance.com")) => {
				request.getSession.setAttribute("user", user)
				request.getSession.setAttribute("token", tokenResponse.toString)
				response.setStatus(SC_OK)
			}
			case _ => response.setStatus(SC_UNAUTHORIZED)
		}
		response.getWriter.print(new Gson().toJson("Successfully connected user: " + tokenResponse.getAccessToken))
	}


}
