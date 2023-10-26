package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.DpsPrincipal
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.entity.CIAGProfile
import java.time.Instant
import java.time.ZoneOffset

@Service
class OutboundEventsService(
  var outboundEventsPublisher: OutboundEventsPublisher?,
) {

  val principal: DpsPrincipal by lazy {
    SecurityContextHolder.getContext().authentication?.principal as DpsPrincipal
  }
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
        userId = principal.name,
        userDisplayName = principal.displayName,
      ),
      occurredAt = instant,
      version = 1,
    )
}
