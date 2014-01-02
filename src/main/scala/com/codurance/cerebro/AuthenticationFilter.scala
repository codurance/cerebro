package com.codurance.cerebro

import org.scalatra.ScalatraFilter

class AuthenticationFilter extends ScalatraFilter {
  before() {
    if(notSigninPage && notAuthPage && notAuthenticated) {
        redirect("/signin")
    }
  }

  def notAuthenticated: Boolean = {
    request.getSession.getAttribute("token") == null
  }

  def notSigninPage(): Boolean = {
    !request.getRequestURI.equals("/signin")
  }
  
  def notAuthPage(): Boolean = {
    !request.getRequestURI.equals("/authenticate")
  }
}
