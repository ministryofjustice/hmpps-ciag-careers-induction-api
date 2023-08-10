package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.integration

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.TestData
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.ErrorResponse
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileRequestDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.CIAGProfileRepository

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CIAGProfileIntTest : IntegrationTestBase() {

  @Autowired
  lateinit var ciagProfileRepository: CIAGProfileRepository

  @Autowired
  lateinit var objectMapper: ObjectMapper

  @Test
  fun `Get the exception for profile for a unknown offender`() {
    val result = restTemplate.exchange("/ciag/A1234AB", HttpMethod.GET, HttpEntity<HttpHeaders>(setAuthorisation(roles = listOf("ROLE_WORK_READINESS_EDIT", "ROLE_WORK_READINESS_VIEW"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    val erroResponse = result.body
    assert(erroResponse.status.equals(HttpStatus.BAD_REQUEST.value()))
    assert(erroResponse.userMessage.equals("CIAG previousWork does not exist for offender A1234AB"))
  }

  @Test
  fun `Post a CIAG profile for an offender`() {
    val result = restTemplate.exchange("/ciag/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(TestData.ciagDTO, setAuthorisation(roles = listOf("ROLE_WORK_READINESS_EDIT", "ROLE_WORK_READINESS_VIEW"))), CIAGProfileDTO::class.java)
    assertThat(result).isNotNull
  }

  @Test
  fun `Modify a CIAG profile for an offender`() {
    val result = restTemplate.exchange("/ciag/A1234AB", HttpMethod.PUT, HttpEntity<CIAGProfileRequestDTO>(TestData.ciagDTO, setAuthorisation(roles = listOf("ROLE_WORK_READINESS_EDIT", "ROLE_WORK_READINESS_VIEW"))), CIAGProfileDTO::class.java)
    assertThat(result).isNotNull
  }

  @Test
  fun `Delete a CIAG profile for an offender`() {
    val result = restTemplate.exchange("/ciag/A1234AB", HttpMethod.PUT, HttpEntity<CIAGProfileRequestDTO>(TestData.ciagDTO, setAuthorisation(roles = listOf("ROLE_WORK_READINESS_EDIT", "ROLE_WORK_READINESS_VIEW"))), CIAGProfileDTO::class.java)
    assertThat(result).isNotNull
    assertThat(result.body).isNotNull

    val deleteresult = restTemplate.exchange("/ciag/A1234AB", HttpMethod.DELETE, HttpEntity<String>(TestData.ciagDTO.offenderId, setAuthorisation(roles = listOf("ROLE_WORK_READINESS_EDIT", "ROLE_WORK_READINESS_VIEW"))), CIAGProfileDTO::class.java)
    assertThat(deleteresult).isNotNull
    assertThat(deleteresult.body).isNull()
  }
}
