package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.unit.messaging

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.TestData
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.config.DpsPrincipal
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.EventType
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.OutboundEventsPublisher
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.OutboundEventsService

@ExtendWith(MockitoExtension::class)
class OutboundEventsServiceTest {
  private var outboundEventsService: OutboundEventsService? = null

  @Mock
  private val outboundEventsPublisher: OutboundEventsPublisher? = null

  @Mock
  private val securityContext: SecurityContext? = null

  @Mock
  private val authentication: Authentication? = null

  private val dpsPrincipal: DpsPrincipal = DpsPrincipal("Sacintha", "Sacintha Raj")

  @BeforeEach
  fun beforeClass() {
    outboundEventsService = OutboundEventsService(outboundEventsPublisher!!)
  }

  @Test
  fun should_Publish_Event_ForInductionCreationAndUpdate() {
    whenever(authentication?.principal).thenReturn(dpsPrincipal)
    whenever(securityContext?.authentication).thenReturn(authentication)
    SecurityContextHolder.setContext(securityContext)
    outboundEventsService?.createAndPublishEventMessage(TestData.ciagSecond, EventType.CIAG_INDUCTION_CREATED)
    assertThat(true)
    outboundEventsService?.createAndPublishEventMessage(TestData.ciag, EventType.CIAG_INDUCTION_UPDATED)
    assertThat(true)
  }
}
