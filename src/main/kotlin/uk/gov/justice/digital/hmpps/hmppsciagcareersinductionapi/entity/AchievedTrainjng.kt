package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.Training
import javax.persistence.Embeddable

@Embeddable
data class AchievedTrainjng(
  var training: Training?,
  var customTraining: String?,
)
