package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.data.common.FutureIntrests
import javax.persistence.Embeddable

@Embeddable
data class FutureIntrestsDetail(
  var definedIntrests: FutureIntrests?,
  var otherIntrests: String?,
)
