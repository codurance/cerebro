import javax.servlet.ServletContext

import com.codurance.cerebro.controllers.CerebroServlet
import com.codurance.cerebro.security.AuthenticationFilter
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {
	override def init(context: ServletContext) {
		context.mount(new AuthenticationFilter, "/*")
		context.mount(new CerebroServlet, "/*")
	}
}
