package com.codurance.cerebro

import org.json4s._
import org.json4s.jackson.JsonMethods._

object GooglePlusJSONResponseToUser {

	implicit val formats = DefaultFormats

	def toUser(googlePlusJSONResponse: String): User = {
		val json = parse(googlePlusJSONResponse) transformField {
			case ("value", x) => ("address", x)
			case ("type", x)  => ("emailType", x)
		}
		val emails = toEmails(json)
		val name = toName(json)
		val domain = toDomain(json)
		val language = toLanguage(json)

		User(name, language, domain, emails)
	}

	private def toLanguage(json: JValue) = {
		Language((json \ "language").extract[String])
	}

	private def toDomain(json: JValue): Option[Domain] = {
		val domainValue = (json \ "domain")
		if (domainValue != JNothing)
			 Some(Domain(domainValue.extract[String]))
		else None
	}

	private def toEmails(json: JValue) = {
		val emailJson = (json \ "emails") transformField {
			case ("value", x) => ("address", x)
			case ("type", x)  => ("emailType", x)
		}
		(emailJson).extract[List[Email]]
	}

	private def toName(json: JValue): Name = {
		val displayName = Some((json \ "displayName").extract[String])
		val familyName = (json \ "name" \ "familyName").extract[String]
		val givenName = (json \ "name" \ "givenName").extract[String]
		Name(givenName, familyName, displayName)
	}

}
