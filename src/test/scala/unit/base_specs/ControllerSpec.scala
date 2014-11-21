package unit.base_specs

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import org.scalatest.OptionValues
import org.scalatest.matchers.ClassicMatchers
import org.scalatest.mock.MockitoSugar
import org.scalatra.ScalatraBase
import org.scalatra.scalate.ScalateSupport
import org.scalatra.test.scalatest.ScalatraFlatSpec
import unit.com.codurance.cerebro.security.LoggedInUserFilter

class ControllerSpec extends ScalatraFlatSpec
								with MockitoSugar
								with OptionValues
								with ClassicMatchers {

	addFilter(new LoggedInUserFilter, "/*")

	var jadeAttributes: Seq[(String, Any)] = null
	var jadePath: String = null

	trait ScalateSupportMock extends ScalatraBase with ScalateSupport {

		override protected def jade(path: String, attributes: (String, Any)*)(implicit request: HttpServletRequest, response: HttpServletResponse) = {
			jadePath = path
			jadeAttributes = attributes
			"index page"
		}

	}
}