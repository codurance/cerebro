package unit.com.codurance.cerebro.security

import com.codurance.cerebro.security.{Domain, Language, Name, User}
import org.scalatra.ScalatraFilter

class LoggedInUserFilter extends ScalatraFilter {

	val valid_user = User(Name("Sandro", "Mancuso", Some("Sandro Mancuso")),
		Language("en"),
		Some(Domain("codurance.com")),
		List(),
		"")

	before() {
		val user: User = session.getAttribute("user").asInstanceOf[User]
		if (user == null) {
			session.setAttribute("user", valid_user)
		}
	}

}
