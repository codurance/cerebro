package com.codurance.cerebro.controllers

import javax.servlet.http.HttpServletResponse.{SC_OK, SC_UNAUTHORIZED}

import com.codurance.cerebro.security.{Domain, GooglePlus}
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson.JacksonFactory
import com.stackmob.newman._

import scala.Predef._


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
		val user = GooglePlus.userFor(authCode)
		user.domain match {
			case Some(Domain("codurance.com")) => {
				request.getSession.setAttribute("user", user)
				response.setStatus(SC_OK)
			}
			case _ => response.setStatus(SC_UNAUTHORIZED)
		}
	}


}
