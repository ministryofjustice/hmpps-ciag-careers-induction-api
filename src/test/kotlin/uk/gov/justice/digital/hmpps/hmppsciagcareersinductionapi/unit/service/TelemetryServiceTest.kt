package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.unit.service

import com.microsoft.applicationinsights.TelemetryClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.TestData
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.EventType
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.telemetry.TelemetryService
import java.time.ZoneOffset

class TelemetryServiceTest {
  private val telemetryClient: TelemetryClient = mock()

  private lateinit var telemetryService: TelemetryService

  @BeforeEach
  fun beforeEach() {
    telemetryService = TelemetryService(
      telemetryClient,
    )
  }

  @Test
  fun `makes a call to the telemetryclient  when a induction is created`() {
    telemetryService.createAndPublishTelemetryEventMessage(TestData.ciagSecond, EventType.CIAG_INDUCTION_CREATED)
    verify(telemetryClient, Mockito.times(1)).trackEvent(
      "INDUCTION_CREATED",
      mapOf(
        "prisonId" to "sacintha",
        "userId" to "sacintha",
        "timestamp" to TestData.ciagSecond.modifiedDateTime?.toInstant(
          ZoneOffset.UTC,
        )!!.toString(),
      ),
      null,
    )
  }

  @Test
  fun `makes a call to the telemetryclient  when a induction is updated`() {
    telemetryService.createAndPublishTelemetryEventMessage(TestData.ciagSecond, EventType.CIAG_INDUCTION_UPDATED)
    verify(telemetryClient, Mockito.times(1)).trackEvent(
      "INDUCTION_UPDATED",
      mapOf(
        "prisonId" to "sacintha",
        "userId" to "sacintha",
        "timestamp" to TestData.ciagSecond.modifiedDateTime?.toInstant(
          ZoneOffset.UTC,
        )!!.toString(),
      ),
      null,
    )
  }
}
