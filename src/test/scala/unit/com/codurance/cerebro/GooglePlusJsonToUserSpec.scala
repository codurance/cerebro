package unit.com.codurance.cerebro

import com.codurance.cerebro.{Name, Email}
import com.codurance.cerebro.GooglePlusJSONResponseToUser._
import unit.SpecificationWithMockito

class GooglePlusJsonToUserSpec extends SpecificationWithMockito {
	

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

	"GooglePlusJSONResponseToUser" should {

		val name = toName(GOOGLE_PLUS_PEOPLE_API_RESPONSE);

		"populate display name" in {
			name.displayName must be_==("Sandro Mancuso")
		}

		"populate family and given name" in {
			name.familyName must be_==("Mancuso")
			name.givenName must be_==("Sandro")
		}

	}

	"GooglePlusJSONResponseToUser" should {

		val user = toUser(GOOGLE_PLUS_PEOPLE_API_RESPONSE)

		"populate name" in {
			user.name must be_==(Name("Sandro", "Mancuso", "Sandro Mancuso"))
		}

		"populate domain" in {
			user.domain must be_==("codurance.com")
		}

		"populate language" in {
			user.language must be_==("en_GB")
		}

		"populate emails" in {
			user.emails must be_==(List(Email("sandro@codurance.com", "account")))
		}

	}



}
