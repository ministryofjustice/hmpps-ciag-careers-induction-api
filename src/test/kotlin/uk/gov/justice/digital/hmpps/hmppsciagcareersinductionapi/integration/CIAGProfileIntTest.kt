package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.integration

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.TestData
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.CapturedSpringMapperConfiguration
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.ErrorResponse
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileListDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileOffenderIdListRequestDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.CIAGProfileRequestDTO
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.repository.CIAGProfileRepository

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CIAGProfileIntTest : IntegrationTestBase() {

  @Autowired
  lateinit var objectMapper: ObjectMapper

  @Autowired
  lateinit var ciagProfileRepository: CIAGProfileRepository

  @Value("\${server.port}")
  private val serverPort = 0

  @AfterEach
  internal fun tearDown() {
//    ciagProfileRepository.deleteAll()
  }

  @BeforeEach
  internal fun setUp() {
//    ciagProfileRepository.deleteAll()
  }

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
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createProfileJsonRequest,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(result).isNotNull
  }

  @Test
  fun `Post a CIAG profile for an offender with prison name and Prison Id`() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createProfileWithPrsionDetailsJsonRequest,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AC", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(result).isNotNull

    val storedProfile = restTemplate.exchange("/ciag/induction/A1234AC", HttpMethod.GET, HttpEntity<HttpHeaders>(setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(result).isNotNull

    assertThat(storedProfile.body).extracting(TestData.prisonIdString, TestData.prisonNameString)
      .contains(result.body.prisonId, result.body.prisonName)
  }

  @Test
  fun `Modify a CIAG profile for an offender`() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
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
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createEmptyReasonToNotGetWork,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage.equals("Field: ciagProfileRequestDTO.reasonToNotGetWorkOther, Value: null,  Error: Value cannot be empty")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with invalid ReasonNotToGetWorkOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createInvalidReasonToNotGetWork,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage.equals("Field: ciagProfileRequestDTO.reasonToNotGetWorkOther, Value: sample,  Error: Field cannot be populated")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with empty abilityToWork `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createEmptyAbilityToWork,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage.equals("Field: ciagProfileRequestDTO.abilityToWorkOther, Value: null,  Error: Value cannot be empty")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with invalid abilityToWork `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createInvalidAbilityToWork,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage.equals("Field: ciagProfileRequestDTO.abilityToWorkOther, Value: sample,  Error: Field cannot be populated")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with empty workInterestsOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createEmptyWorkInterestsOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage.equals("Field: ciagProfileRequestDTO.workExperience.workInterests.workInterestsOther, Value: null,  Error: Value cannot be empty")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with invalid workInterestsOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createInvalidWorkInterestsOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage?.contains("Field: ciagProfileRequestDTO.workExperience.workInterests.workInterestsOther, Value: sample,  Error: Field cannot be populated")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with empty skillsOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createSkillsOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage?.contains("Field: ciagProfileRequestDTO.skillsAndInterests.skillsOther, Value: null,  Error: Value cannot be empty")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with invalid skillsOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createInvalidSkillsOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage?.contains("Field: ciagProfileRequestDTO.skillsAndInterests.skillsOther, Value: chumma,  Error: Field cannot be populated")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with empty personalInterestsOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createPersonalInterestsOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage?.contains("Field: ciagProfileRequestDTO.skillsAndInterests.personalInterestsOther, Value: null,  Error: Value cannot be empty")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with invalid personalInterestsOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createInvalidPersonalInterestsOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage?.contains("Field: ciagProfileRequestDTO.skillsAndInterests.personalInterestsOther, Value: chumma,  Error: Field cannot be populated")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with empty inPrisonEducationOOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createInPrisonEducationOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage?.contains("Field: ciagProfileRequestDTO.inPrisonInterests.inPrisonEducationOther, Value: null,  Error: Value cannot be empty")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with invalid InPrisonEducationOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createInvalidInPrisonEducationOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage?.contains("Field: ciagProfileRequestDTO.inPrisonInterests.inPrisonEducationOther, Value: chumma,  Error: Field cannot be populated")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with empty inPrisonWorkOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createInPrisonWorkOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage?.contains("Field: ciagProfileRequestDTO.inPrisonInterests.inPrisonWorkOther, Value: null,  Error: Value cannot be empty")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with invalid inPrisonWorkOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createInvalidInPrisonWorkOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage?.contains("Field: ciagProfileRequestDTO.inPrisonInterests.inPrisonWorkOther, Value: chumma,  Error: Field cannot be populated")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with empty additionalTrainingOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createAdditionalTrainingOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage?.contains("Field: ciagProfileRequestDTO.qualificationsAndTraining.additionalTrainingOther, Value: null,  Error: Value cannot be empty")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with invalid additionalTrainingOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createInvalidAdditionalTrainingOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage?.contains("Field: ciagProfileRequestDTO.qualificationsAndTraining.additionalTrainingOther, Value: chumma,  Error: Field cannot be populated")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with empty typeOfWorkExperienceOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createTypeOfWorkExperienceOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage?.contains("Field: ciagProfileRequestDTO.workExperience.typeOfWorkExperienceOther, Value: null,  Error: Value cannot be empty")).isTrue()
  }

  @Test
  fun `Post a CIAG profile for an offender with invalid typeOfWorkExperienceOther `() {
    val actualCIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createInvalidTypeOfWorkExperienceOther,
      object : TypeReference<CIAGProfileRequestDTO>() {},
    )
    val result = restTemplate.exchange("/ciag/induction/A1234AB", HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(actualCIAGProfileRequestDTO, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), ErrorResponse::class.java)
    assertThat(result).isNotNull
    assertThat(result.statusCode.equals(HttpStatus.BAD_REQUEST)).isTrue()
    assertThat(result.body.userMessage?.contains("Field: ciagProfileRequestDTO.workExperience.typeOfWorkExperienceOther, Value: chumma,  Error: Field cannot be populated")).isTrue()
  }

  @Test
  fun `Post multiple CIAG profile for different Offenders and retreive it as a list`() {
    val validCIAGProfileRequestDTO_A1234AC: CIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createValidProfile_A1234AC,
      CIAGProfileRequestDTO::class.java,
    )
    val result_A1234AC = restTemplate.exchange("/ciag/induction/" + TestData.offenderId_A1234AC, HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(validCIAGProfileRequestDTO_A1234AC, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(result_A1234AC).isNotNull
    assertThat(result_A1234AC.body).isNotNull

    val validCIAGProfileRequestDTO_A1234AB: CIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createValidProfile_A1234AB,
      CIAGProfileRequestDTO::class.java,
    )
    val result_A1234AB = restTemplate.exchange("/ciag/induction/" + TestData.offenderId_A1234AB, HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(validCIAGProfileRequestDTO_A1234AB, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(result_A1234AB).isNotNull
    assertThat(result_A1234AB.body).isNotNull

    val validCIAGProfileRequestDTO_A1234AD: CIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createValidProfile_A1234AD,
      CIAGProfileRequestDTO::class.java,
    )
    val result_A1234AD = restTemplate.exchange("/ciag/induction/" + TestData.offenderId_A1234AD, HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(validCIAGProfileRequestDTO_A1234AD, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(result_A1234AD).isNotNull
    assertThat(result_A1234AD.body).isNotNull

    val validOffenderIdList: CIAGProfileOffenderIdListRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.correctOffenderIdList,
      CIAGProfileOffenderIdListRequestDTO::class.java,
    )
    val result_List = restTemplate.exchange("/ciag/induction/list", HttpMethod.POST, HttpEntity<CIAGProfileOffenderIdListRequestDTO>(validOffenderIdList, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileListDTO::class.java)
    assertThat(result_List).isNotNull
    assertThat(result_List.body).isNotNull
    assertThat(result_List.body.ciagProfileList?.size).isEqualTo(3)
  }

  @Test
  fun `Post multiple CIAG profile for different Offenders and throw an exception while retreiving it with Invalid data`() {
    val validCIAGProfileRequestDTO_A1234AC: CIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createValidProfile_A1234AC,
      CIAGProfileRequestDTO::class.java,
    )
    val result_A1234AC = restTemplate.exchange("/ciag/induction/" + TestData.offenderId_A1234AC, HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(validCIAGProfileRequestDTO_A1234AC, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(result_A1234AC).isNotNull
    assertThat(result_A1234AC.body).isNotNull

    val validCIAGProfileRequestDTO_A1234AB: CIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createValidProfile_A1234AB,
      CIAGProfileRequestDTO::class.java,
    )
    val result_A1234AB = restTemplate.exchange("/ciag/induction/" + TestData.offenderId_A1234AB, HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(validCIAGProfileRequestDTO_A1234AB, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(result_A1234AB).isNotNull
    assertThat(result_A1234AB.body).isNotNull

    val validCIAGProfileRequestDTO_A1234AD: CIAGProfileRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.createValidProfile_A1234AD,
      CIAGProfileRequestDTO::class.java,
    )
    val result_A1234AD = restTemplate.exchange("/ciag/induction/" + TestData.offenderId_A1234AD, HttpMethod.POST, HttpEntity<CIAGProfileRequestDTO>(validCIAGProfileRequestDTO_A1234AD, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileDTO::class.java)
    assertThat(result_A1234AD).isNotNull
    assertThat(result_A1234AD.body).isNotNull

    val validOffenderIdList: CIAGProfileOffenderIdListRequestDTO = CapturedSpringMapperConfiguration.OBJECT_MAPPER.readValue(
      TestData.inCorrectOffenderIdList,
      CIAGProfileOffenderIdListRequestDTO::class.java,
    )
    val result_List = restTemplate.exchange("/ciag/induction/list", HttpMethod.POST, HttpEntity<CIAGProfileOffenderIdListRequestDTO>(validOffenderIdList, setAuthorisation(roles = listOf("ROLE_EDUCATION_WORK_PLAN_EDITOR", "ROLE_EDUCATION_WORK_PLAN_VIEWER"))), CIAGProfileListDTO::class.java)
    assertThat(result_List).isNotNull
    assertThat(result_List.body).isNotNull
    assertThat(result_List.body.ciagProfileList?.size).isEqualTo(1)
  }
}
