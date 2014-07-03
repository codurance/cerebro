package com.codurance.cerebro

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._


object GooglePlusJSONResponseToUser {

	def toName(googlePlusJSONResponse: String): Name = {
		val json: JsValue = Json.parse(googlePlusJSONResponse)
		new Name(valueOf(json, "givenName"),
				 valueOf(json, "familyName"),
				 valueOf(json, "displayName"))
	}

	def toUser(googlePlusJSONResponse: String): User = {
		val json: JsValue = Json.parse(googlePlusJSONResponse)
		new User(toName(googlePlusJSONResponse),
				 valueOf(json, "language"),
				 valueOf(json, "domain"),
				 toEmails(json))
	}

	private def toEmails(json: JsValue): Seq[Email] = {
		(json \ "emails").as[Seq[Email]]
	}

	private def valueOf(json: JsValue, field: String) = {
		(json \\ field)(0).as[String]
	}

	implicit val emailReads: Reads[Email] = (
		(JsPath \ "value").read[String] and
		(JsPath \ "type").read[String]
	)(Email.apply _)

}
