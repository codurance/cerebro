import javax.servlet.ServletContext

import com.codurance.cerebro.controllers.MainController
import com.codurance.cerebro.security.AuthenticationFilter
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {
	override def init(context: ServletContext) {
		context.mount(new AuthenticationFilter, "/*")
		context.mount(new MainController, "/*")
	}
}
