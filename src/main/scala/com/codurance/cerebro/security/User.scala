package com.codurance.cerebro.security

case class User(name: Name, language: Language, domain: Option[Domain], emails: List[Email], googleToken: String)

case class Name(givenName: String, familyName: String, displayName: Option[String])

case class Email(address: String, emailType: String)

case class Language(code: String)

case class Domain(name: String)