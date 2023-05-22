package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.integration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.test.context.ActiveProfiles
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.HmppsCiagCareersInductionApi
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.helpers.JwtAuthHelper

@SpringBootTest(
  webEnvironment = RANDOM_PORT,
  classes = arrayOf(
    HmppsCiagCareersInductionApi::class,
  ),
)
@ActiveProfiles("test")
abstract class IntegrationTestBase internal constructor() {
  @Autowired
  lateinit var restTemplate: TestRestTemplate

  @Autowired
  lateinit var jwtAuthHelper: JwtAuthHelper
  internal fun setAuthorisation(
    user: String = "test-client",
    roles: List<String> = listOf(),
  ): (HttpHeaders) {
    return jwtAuthHelper.setAuthorisationForUnitTests(user, roles)
  }
  companion object {
    val log: Logger = LoggerFactory.getLogger(this::class.java)
  }
}
