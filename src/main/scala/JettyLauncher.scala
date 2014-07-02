import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.webapp.WebAppContext

object JettyLauncher extends App {
	val port = if (System.getenv("PORT") != null) System.getenv("PORT").toInt else 8080

	val server = new Server(port)
	val context = new WebAppContext()
	context setContextPath "/"
	context.setResourceBase("src/main/webapp")
	context.addServlet(classOf[com.codurance.cerebro.CerebroServlet], "/*")
	context.addServlet(classOf[DefaultServlet], "/")

	server.setHandler(context)

	server.start
	server.join
}