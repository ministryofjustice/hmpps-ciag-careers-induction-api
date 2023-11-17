package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.telemetry

import com.microsoft.applicationinsights.TelemetryClient
import org.springframework.stereotype.Component
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.EventType
import java.time.ZoneOffset

@Component
class TelemetryService(
  private val telemetryClient: TelemetryClient,
) {

  fun createAndPublishTelemetryEventMessage(ciagProfile: CIAGProfile, eventType: EventType) {
    val logMap = createTelemetryEventMap_induction_created_updated(ciagProfile)
    if (eventType.equals(EventType.CIAG_INDUCTION_CREATED)) {
      telemetryClient.trackEvent(TelemetryEvent.INDUCTION_CREATED.toString(), logMap, null)
      telemetryClient.trackEvent(TelemetryEvent.DESIRE_TO_WORK.toString() + "_" + ciagProfile.desireToWork, logMap, null)
      telemetryClient.trackEvent(TelemetryEvent.HOPE_TO_GET_WORK.toString() + "_" + ciagProfile.hopingToGetWork, logMap, null)
      ciagProfile.abilityToWork?.let {
        it.forEach { e ->
          telemetryClient.trackEvent(TelemetryEvent.ABILITY_TO_WORK.toString() + "_" + e.toString(), logMap, null)
        }
      }

      ciagProfile.skillsAndInterests?.personalInterests?.let {
        it.forEach { e ->
          telemetryClient.trackEvent(TelemetryEvent.WORK_TYPE.toString() + "_" + e.toString(), logMap, null)
        }
      }
    } else {
      telemetryClient.trackEvent(TelemetryEvent.INDUCTION_UPDATED.toString(), logMap, null)
    }
  }

  fun createTelemetryEventMap_induction_created_updated(ciagProfile: CIAGProfile): MutableMap<String, String> {
    val logMap: MutableMap<String, String> = HashMap()
    logMap["prisonId"] = ciagProfile.prisonId.toString()
    logMap["userId"] = ciagProfile.modifiedBy.toString()
    logMap["timestamp"] = ciagProfile.modifiedDateTime?.toInstant(
      ZoneOffset.UTC,
    )!!.toString()
    return logMap
  }
}
