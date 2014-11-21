package unit.com.codurance.cerebro

import com.codurance.cerebro.security.GooglePlusJSONResponseParser._
import com.codurance.cerebro.security._
import unit.SpecificationWithMockito

class GooglePlusJSONResponseParserSpec extends SpecificationWithMockito {

	val TOKEN = "2345678hshjflasjdf234"

	val GOOGLE_PLUS_PEOPLE_API_RESPONSE = """
		{
			"emails": [
				{
					"value": "sandro@codurance.com",
					"type": "account"
				}
			],
            "domain": "codurance.com",
			"displayName": "Sandro Mancuso",
			"name": {
				"familyName": "Mancuso",
				"givenName": "Sandro"
			},
			"language": "en_GB"
		}
		"""

	val USER_WITH_NO_DOMAIN =
		"""
			{
		  	    "emails": [
		            {
						"value": "sandro@codurance.com",
						"type": "account"
					}
				],
				"displayName": "Sandro Mancuso",
				"name": {
					"familyName": "Mancuso",
					"givenName": "Sandro"
				},
				"language": "en_GB"
			}
		"""

	"GooglePlusJSONResponseToUser" should {

		val user = toUser(GOOGLE_PLUS_PEOPLE_API_RESPONSE, TOKEN)

		"populate token" in {
			user.googleToken must be_==(TOKEN)
		}

		"populate name" in {
			user.name must be_==(Name("Sandro", "Mancuso", Some("Sandro Mancuso")))
		}

		"populate domain" in {
			user.domain must be_==(Some(Domain("codurance.com")))
		}

		"populate empty domain" in {
			val userWithNoDomain = toUser(USER_WITH_NO_DOMAIN, TOKEN)
			userWithNoDomain.domain must be_==(None)
		}

		"populate language" in {
			user.language must be_==(Language("en_GB"))
		}

		"populate emails" in {
			user.emails must be_==(List(Email("sandro@codurance.com", "account")))
		}

	}



}
