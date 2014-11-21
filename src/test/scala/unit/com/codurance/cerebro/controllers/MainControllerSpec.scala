package unit.com.codurance.cerebro.controllers

import com.codurance.cerebro.controllers.MainController
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import unit.base_specs.ControllerSpec
import unit.com.codurance.cerebro.security.LoggedInUserFilter.LOGGED_IN_USER

@RunWith(classOf[JUnitRunner])
class MainControllerSpec extends ControllerSpec {

	val mainController = new MainController with ScalateSupportMock

	addServlet(mainController, "/*")

	"MainController" should "display the main page" in {
		get("/") {
			status should equal (200)
		}
		jadePath must equal ("main")
	}

	it should "contains the logged in user in the session" in {
		get("/") {}
		jadeAttributes.contains(("user" -> LOGGED_IN_USER)) must be (true)
	}

}
