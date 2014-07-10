package com.codurance.cerebro.security

import org.scalatra.ScalatraFilter

class AuthenticationFilter extends ScalatraFilter {
	before() {
		if (notSigninPage && notAuthPage && notNotAuthPage && notAuthenticated) {
			redirect("/signin?originalUri=" + originalURL)
		}
	}

	def originalURL(): String = {
		val url = Option(request.getRequestURI).getOrElse("/main")
		if (url.startsWith("/signin")) "/main" else url
	}

	def notAuthenticated: Boolean = {
		request.getSession.getAttribute("user") == null
	}

	def notSigninPage(): Boolean = {
		!request.getRequestURI.equals("/signin")
	}

	def notAuthPage(): Boolean = {
		!request.getRequestURI.equals("/authorise")
	}

	def notNotAuthPage(): Boolean = {
		!request.getRequestURI.equals("/not-authorised")
	}
}