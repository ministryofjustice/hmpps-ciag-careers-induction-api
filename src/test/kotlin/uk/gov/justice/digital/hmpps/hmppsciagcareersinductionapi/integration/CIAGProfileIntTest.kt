package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.integration

import com.fasterxml.jackson.core.type.TypeReference
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
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.GET, HttpEntity<HttpHeaders>(setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    val erroResponse = result.body
    assert(erroResponse.status.equals(HttpStatus.NOT_FOUND.value()))
    assert(erroResponse.userMessage.equals("CIAG profile does not exist for offender A1234AB"))
  }

  @Test
  fun `Post a CIAG profile for an offender`() {
    val actualCIAGProfileRequestDTO = objectMapper.readValue(
      TestData.createProfileJsonRequest,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(result).isNotNull
  }

  @Test
  fun `Modify a CIAG profile for an offender`() {
    val actualCIAGProfileRequestDTO = objectMapper.readValue(
      TestData.createProfileJsonRequest,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(result).isNotNull
    val resultUpdate = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.PUT, HttpEntity<CIAGProfileRequestDTO>(TestData.ciagDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(resultUpdate).isNotNull
  }

  @Test
  fun `Delete a CIAG profile for an offender`() {
    val result = restTemplate.exchange("/ciag/induction/" + TestData.ciagDTO.offenderId, HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(TestData.ciagDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(result).isNotNull
    assertThat(result.body).isNotNull

    val deleteresult = restTemplate.exchange("/ciag/induction/" + TestData.ciagDTO.offenderId, HttpMethod.DELETE, HttpEntity<String>(TestData.ciagDTO.offenderId, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(deleteresult).isNotNull
    assert(deleteresult.statusCode.equals(HttpStatus.OK))
  }

  @Test
  fun `Post a CIAG profile for an offender with empty ReasonNotToGetWorkOther `() {
    val actualCIAGProfileRequestDTO = objectMapper.readValue(
      TestData.createEmptyReasonToNotGetWork,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST))
    assertThat(result.body.userMessage.equals("Field: ciagProfileRequestDTO.reasonToNotGetWorkOther, Value: null,  Error: Value cannot be empty"))
  }

  @Test
  fun `Post a CIAG profile for an offender with invalid ReasonNotToGetWorkOther `() {
    val actualCIAGProfileRequestDTO = objectMapper.readValue(
      TestData.createInvalidReasonToNotGetWork,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST))
    assertThat(result.body.userMessage.equals("Field: ciagProfileRequestDTO.reasonToNotGetWorkOther, Value: sample,  Error: Field cannot be populated"))
  }

  @Test
  fun `Post a CIAG profile for an offender with empty abilityToWork `() {
    val actualCIAGProfileRequestDTO = objectMapper.readValue(
      TestData.createEmptyAbilityToWork,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST))
    assertThat(result.body.userMessage.equals("Field: ciagProfileRequestDTO.abilityToWorkOther, Value: null,  Error: Value cannot be empty"))
  }

  @Test
  fun `Post a CIAG profile for an offender with invalid abilityToWork `() {
    val actualCIAGProfileRequestDTO = objectMapper.readValue(
      TestData.createInvalidAbilityToWork,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST))
    assertThat(result.body.userMessage.equals("Field: ciagProfileRequestDTO.abilityToWorkOther, Value: sample,  Error: Field cannot be populated"))
  }

  @Test
  fun `Post a CIAG profile for an offender with empty workInterestsOther `() {
    val actualCIAGProfileRequestDTO = objectMapper.readValue(
      TestData.createEmptyWorkInterestsOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST))
    assertThat(result.body.userMessage.equals("Field: ciagProfileRequestDTO.workExperience.workInterests.workInterestsOther, Value: null,  Error: Value cannot be empty"))
  }

  @Test
  fun `Post a CIAG profile for an offender with invalid workInterestsOther `() {
    val actualCIAGProfileRequestDTO = objectMapper.readValue(
      TestData.createInvalidWorkInterestsOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST))
    assertThat(result.body.userMessage.equals("Field: ciagProfileRequestDTO.workExperience.workInterests.workInterestsOther, Value: sample,  Error: Field cannot be populated"))
  }
}
