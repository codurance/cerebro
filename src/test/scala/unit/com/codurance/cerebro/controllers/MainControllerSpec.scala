package unit.com.codurance.cerebro.controllers

import com.codurance.cerebro.controllers.MainController
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import unit.base_specs.ControllerSpec

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

}
