package com.codurance.cerebro.controllers

import javax.servlet.http.{HttpServletResponse, HttpServletRequest}

class BaseController extends CerebroStack {

	def display(page: String, attributes: (String, Any)*)(implicit request: HttpServletRequest, response: HttpServletResponse): String = {
		contentType = "text/html"
		val all_attributes = attributes :+ ("user", session.getAttribute("user"))
		jade(page, all_attributes: _*)
	}

}
