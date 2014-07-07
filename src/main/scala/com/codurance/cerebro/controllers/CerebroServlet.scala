package com.codurance.cerebro.controllers

import javax.servlet.http.HttpServletResponse.{SC_OK, SC_UNAUTHORIZED}

import com.codurance.cerebro.security.{Domain, GooglePlus}

import scala.Predef._

class CerebroServlet extends BaseController {

	get("/") {
		display("main", "user" -> request.getSession.getAttribute("user"))
	}

	get("/main") {
		display("main", "user" -> request.getSession.getAttribute("user"))
	}

	get("/signin") {
		display("signin", "originalUri" -> request.getParameter("originalUri"))
	}

	get("/not-authorised") {
		display("not-authorised")
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
