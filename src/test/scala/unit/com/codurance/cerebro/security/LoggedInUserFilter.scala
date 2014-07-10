package unit.com.codurance.cerebro.security

import com.codurance.cerebro.security.{Domain, Language, Name, User}
import org.scalatra.ScalatraFilter
import unit.com.codurance.cerebro.security.LoggedInUserFilter.LOGGED_IN_USER

class LoggedInUserFilter extends ScalatraFilter {

	before() {
		val user: User = session.getAttribute("user").asInstanceOf[User]
		if (user == null) {
			session.setAttribute("user", LOGGED_IN_USER)
		}
	}

}

object LoggedInUserFilter {

	val LOGGED_IN_USER = User(Name("Sandro", "Mancuso", Some("Sandro Mancuso")),
								Language("en"),
								Some(Domain("codurance.com")),
								List(),
								"")

}
