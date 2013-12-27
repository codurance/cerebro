package com.codurance.cerebro

import org.scalatra._
import scalate.ScalateSupport

class CerebroServlet extends CerebroStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }
  
}
