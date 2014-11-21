package com.codurance.cerebro.security

import org.scalatra.ScalatraFilter

class AuthenticationFilter extends ScalatraFilter {
	before() {
		if (isProtectedUrl && userIsNotAuthenticated) {
			redirect("/signin?originalUri=" + originalURL)
		}
	}

	def originalURL(): String = {
		val url = Option(request.getRequestURI).getOrElse("/main")
		if (url.startsWith("/signin")) "/main" else url
	}

	def userIsNotAuthenticated: Boolean = {
		request.getSession.getAttribute("user") == null
	}
	
	def isProtectedUrl(): Boolean = {
		val url = request.getRequestURI();
		!(url.equals("/signin") || url.equals("/authorise") || url.equals("/not-authorised"))
	}

}
