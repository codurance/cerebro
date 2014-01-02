package com.codurance.cerebro

import javax.servlet.http.{HttpServletResponse, HttpServletRequest}

trait GoogleAuthSupport {
  protected def googleAuth()(implicit request: HttpServletRequest, response: HttpServletResponse) = {
    if (request.getSession.getAttribute("token") == null) {
      response.sendRedirect("/signin")
    }
  }
}
