package com.codurance.cerebro

case class User(name: Name, language: String, domain: String, emails: Seq[Email])

case class Name(givenName: String, familyName: String, displayName: String)

case class Email(address: String, emailType: String)
