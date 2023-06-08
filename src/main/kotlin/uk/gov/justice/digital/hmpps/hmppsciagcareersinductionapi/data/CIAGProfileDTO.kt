package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data

import io.swagger.v3.oas.annotations.media.Schema
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.WorkDesire
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.Profile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.ciag.PersonalDetails
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.ciag.PreviousWork
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.jsonprofile.ciag.PreviousWorkDetail
import java.time.LocalDateTime

data class CIAGProfileDTO(

  @Schema(description = "Author of original profile", example = "whilesp")
  val createdBy: String,

  @Schema(description = "Created date time", type = "string", pattern = "yyyy-MM-dd'T'HH:mm:ss", example = "2018-12-01T13:45:00", required = true)
  val createdDateTime: LocalDateTime,

  @Schema(description = "Author of last modification", example = "whilesp")
  val modifiedBy: String,

  @Schema(description = "Last modified date time", type = "string", pattern = "yyyy-MM-dd'T'HH:mm:ss", example = "2018-12-01T13:45:00", required = true)
  val modifiedDateTime: LocalDateTime,

  @Schema(description = "Version of the JSON schema", example = "1.1.1")
  val schemaVersion: String,

  @Schema(
    description = "personal id",
    example = "{\n" +
      "  \"personalId\":\"ABC12345\",\n" +
      "  \"personalName\":\"Judas\",\n" +
      "}",
  )
  val personalDetail: PersonalDetails,

  @Schema(description = "Booking Id", example = "1234567")
  val desireToWork: WorkDesire,

  @Schema(description = "Previous works schema", example = "1.1.1")
  val previousWork: PreviousWork,

  @Schema(description = "Previous work details schema", example = "1.1.1")
  var previousWorkDetail: List<PreviousWorkDetail>,

  @Schema(description = "Work readiness profile JSON data", example = "{...}")
  val profileData: Profile,
) {
  /*constructor(profileEntity: CIAGProfile) : this(
    offenderId = profileEntity.offenderId
      )*/
}
