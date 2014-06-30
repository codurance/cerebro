package com.codurance.cerebro

import org.scalatra.ScalatraFilter

class AuthenticationFilter extends ScalatraFilter {
	before() {
		if (notSigninPage && notAuthPage && notAuthenticated) {
			redirect("/signin?originalUri=" + originalURL)
		}
	}

	def originalURL(): String = {
		val url = Option(request.getRequestURI).getOrElse("/hello")
		if (url.startsWith("/signin")) "/hello" else url
	}

	def notAuthenticated: Boolean = {
		request.getSession.getAttribute("token") == null
	}

	def notSigninPage(): Boolean = {
		!request.getRequestURI.equals("/signin")
	}

	def notAuthPage(): Boolean = {
		!request.getRequestURI.equals("/authenticate")
	}
}
