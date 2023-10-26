package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging

import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.CapturedSpringConfigValues
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import java.time.Instant
import java.time.ZoneOffset

@Service
class OutboundEventsService(
  var outboundEventsPublisher: OutboundEventsPublisher?,
) {

  fun createAndPublishEventMessage(ciagProfile: CIAGProfile, eventType: EventType) {
    val outboundEvent = createValidCiagInductionEvent(
      ciagProfile.offenderId,
      ciagProfile.prisonId,
      eventType,
      ciagProfile.modifiedDateTime?.toInstant(
        ZoneOffset.UTC,
      )!!,
    )
    outboundEvent?.let { outboundEventsPublisher?.publishToTopic(it) }
  }
  fun createValidCiagInductionEvent(
    reference: String,
    prisonName: String?,
    eventType: EventType,
    instant: Instant,
  ): OutboundEvent =
    OutboundEvent(
      eventType = eventType,
      personReference = PersonReference(listOf(Identifier("NOMS", reference))),
      additionalInformation = AdditionalInformation(
        reference = reference,
        prisonId = prisonName,
        userId = CapturedSpringConfigValues.getDPSPrincipal().name,
        userDisplayName = CapturedSpringConfigValues.getDPSPrincipal().displayName,
      ),
      occurredAt = instant,
      version = 1,
    )
}
