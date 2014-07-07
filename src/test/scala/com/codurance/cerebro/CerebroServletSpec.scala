package com.codurance.cerebro

import com.codurance.cerebro.controllers.MainController
import org.scalatra.test.specs2._

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class CerebroServletSpec extends ScalatraSpec {
	def is =
		"GET / on CerebroServlet" ^
				"should return status 200" ! root200 ^
				end

	addServlet(classOf[MainController], "/*")

	def root200 = get("/") {
		status must_== 200
	}
}
