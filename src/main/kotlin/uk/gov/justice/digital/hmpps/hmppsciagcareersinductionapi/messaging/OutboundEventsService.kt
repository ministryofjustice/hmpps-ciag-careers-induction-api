package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging

import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.UserPrincipalAuditorAware
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import java.time.Instant

@Service
class OutboundEventsService(
  var outboundEventsPublisher: OutboundEventsPublisher,
) {
  fun createAndPublishEventMessage(ciagProfile: CIAGProfile, eventType: EventType) {
    val outboundEvent = createValidCiagInductionEvent(ciagProfile.offenderId, ciagProfile.prisonName, eventType)
    outboundEventsPublisher.publishToTopic(outboundEvent)
  }
  fun createValidCiagInductionEvent(
    reference: String,
    prisonName: String,
    eventType: EventType,
  ): OutboundEvent =
    OutboundEvent(
      eventType = eventType,
      personReference = PersonReference(listOf(Identifier("NOMS", reference))),
      additionalInformation = AdditionalInformation(
        reference = reference,
        prisonId = prisonName,
        userId = UserPrincipalAuditorAware.getCurrentAuditorName(),
        userDisplayName = UserPrincipalAuditorAware.getCurrentAuditorDisplayName(),
      ),
      occurredAt = Instant.now(),
      version = 1,
    )
}
