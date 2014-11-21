package com.codurance.cerebro.security

import java.net.URL
import javax.servlet.http.{HttpSession, HttpServletResponse, HttpServletRequest}
import javax.servlet.http.HttpServletResponse._

import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeTokenRequest, GoogleTokenResponse}
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson.JacksonFactory
import com.stackmob.newman._
import com.stackmob.newman.dsl._

import scala.concurrent.Await
import scala.concurrent.duration._

object CoduranceAuthorisation {

	implicit val httpClient = new ApacheHttpClient

	val GOOGLE_PLUS_PEOPLE_URL = "https://www.googleapis.com/plus/v1/people/me?fields=aboutMe%2Ccover%2FcoverPhoto%2CdisplayName%2Cdomain%2Cemails%2Clanguage%2Cname&access_token="
	val CLIENT_ID: String = "1091582186456-sluhikl6ifksd1iifc8h3m5837dr1aq8.apps.googleusercontent.com"
	val CLIENT_SECRET = "RZbRAJz3oG6IbcIsazrrYa-C"
	val API_KEY = "AIzaSyDdwYp7F4NutbxQHEnBaR_mGrUnc_6WwfE"
	val APPLICATION_NAME = "Cerebro"
	val JSON_FACTORY = new JacksonFactory()
	val TRANSPORT = new NetHttpTransport()

	def authorise(authCode: String)(implicit session: HttpSession, response: HttpServletResponse): Unit = {
		val user = CoduranceAuthorisation.userFor(authCode)
		user.domain match {
			case Some(Domain("codurance.com")) => {
				session.setAttribute("user", user)
				response.setStatus(SC_OK)
			}
			case _ => response.setStatus(SC_UNAUTHORIZED)
		}
	}

	def userFor(authCode: String): User = {
		val tokenResponse: GoogleTokenResponse =
			new GoogleAuthorizationCodeTokenRequest(
				TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, authCode, "postmessage"
			).execute
		val url = new URL(GOOGLE_PLUS_PEOPLE_URL + tokenResponse.getAccessToken)
		val userInfo = Await.result(GET(url).apply, 10.seconds)
		GooglePlusJSONResponseParser.toUser(userInfo.bodyString, tokenResponse.toString)
	}

}
