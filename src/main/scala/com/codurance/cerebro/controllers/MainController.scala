package com.codurance.cerebro.controllers

import com.codurance.cerebro.security.CoduranceAuthorisation.authorise

import scala.Predef._

class MainController extends BaseController {

	get("/") {
		display("main")
	}

	get("/main") {
		display("main")
	}

	get("/signin") {
		display("signin", "originalUri" -> request.getParameter("originalUri"))
	}

	get("/not-authorised") {
		display("not-authorised")
	}

	post("/authorise") {
		val authCode: String = params.getOrElse("authCode", halt(400))
		authorise(authCode)
	}

}
