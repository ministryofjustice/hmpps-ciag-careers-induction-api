package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.unit.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever
import software.amazon.awssdk.services.sns.SnsAsyncClient
import software.amazon.awssdk.services.sns.model.PublishRequest
import software.amazon.awssdk.services.sns.model.PublishResponse
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.AdditionalInformation
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.EventType
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.Identifier
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.OutboundEvent
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.OutboundEventsPublisher
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.PersonReference
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging.PublishEventException
import uk.gov.justice.hmpps.sqs.HmppsQueueService
import uk.gov.justice.hmpps.sqs.HmppsTopic
import java.lang.reflect.Modifier
import java.time.Instant
import java.util.concurrent.CompletableFuture

@ExtendWith(MockitoExtension::class)
class OutboundEventsPublisherTest {
  private var outboundEventsPublisher: OutboundEventsPublisher? = null

  @Mock
  private val objectMapper: ObjectMapper? = null

  @Mock
  private val snsClient: SnsAsyncClient? = null

  @Mock
  private val hmppsQueueService: HmppsQueueService? = null

  @Mock
  private val hmppsDomainTopic: HmppsTopic? = null

  @Captor
  private val publishRequestArgumentCaptor: ArgumentCaptor<PublishRequest>? = null

  @BeforeEach
  fun beforeClass() {
    outboundEventsPublisher = OutboundEventsPublisher(objectMapper!!, hmppsQueueService!!)
  }

  @Test
  fun shouldEmit_DomainEvent_ForInductionCreationAndUpdate() {
    whenever(hmppsQueueService!!.findByTopicId(OutboundEventsPublisher.TOPIC_ID)).thenReturn(hmppsDomainTopic)
    whenever(hmppsDomainTopic!!.snsClient).thenReturn(snsClient)

    Mockito.`when`(objectMapper!!.writeValueAsString(ArgumentMatchers.any()))
      .thenReturn(java.lang.String.valueOf(buildDomainEventMessage("MDI", "A8279DD", EventType.CIAG_INDUCTION_UPDATED)))
      .thenReturn(java.lang.String.valueOf(buildDomainEventMessage("MDJ", "A8279DE", EventType.CIAG_INDUCTION_CREATED)))

    val result = CompletableFuture.supplyAsync<PublishResponse> {
      PublishResponse.builder().messageId("messageId").build()
    }
    Mockito.`when`(snsClient!!.publish(ArgumentMatchers.any(PublishRequest::class.java))).thenReturn(result)
    outboundEventsPublisher?.publishToTopic(buildDomainEventMessage("MDI", "A8279DD", EventType.CIAG_INDUCTION_UPDATED))
    outboundEventsPublisher?.publishToTopic(buildDomainEventMessage("MDI", "A8279DE", EventType.CIAG_INDUCTION_CREATED))
    Mockito.verify(snsClient, Mockito.times(2)).publish(publishRequestArgumentCaptor!!.capture())
    val actualPublishedRequest1 = publishRequestArgumentCaptor.allValues[0]
    Assertions.assertThat(actualPublishedRequest1.messageAttributes().get("eventType")!!.dataType()).isEqualTo("String")
    Assertions.assertThat(actualPublishedRequest1.messageAttributes().get("eventType")!!.stringValue())
      .isEqualTo(EventType.CIAG_INDUCTION_UPDATED.eventType)
    Assertions.assertThat(actualPublishedRequest1.message()).contains("MDI")
    Assertions.assertThat(actualPublishedRequest1.message()).contains("A8279DD")

    val actualPublishedRequest2 = publishRequestArgumentCaptor.allValues[1]
    Assertions.assertThat(actualPublishedRequest2.messageAttributes().get("eventType")!!.dataType()).isEqualTo("String")
    Assertions.assertThat(actualPublishedRequest2.messageAttributes().get("eventType")!!.stringValue())
      .isEqualTo(EventType.CIAG_INDUCTION_CREATED.eventType)
    Assertions.assertThat(actualPublishedRequest2.message()).contains("MDJ")
    Assertions.assertThat(actualPublishedRequest2.message()).contains("A8279DE")
  }

  @Test
  fun shouldThrow_PublishEventException_For_Publish_Errors() {
    whenever(hmppsQueueService!!.findByTopicId(OutboundEventsPublisher.TOPIC_ID)).thenReturn(hmppsDomainTopic)
    whenever(hmppsDomainTopic!!.snsClient).thenReturn(snsClient)

    Mockito.`when`(objectMapper!!.writeValueAsString(ArgumentMatchers.any()))
      .thenReturn(java.lang.String.valueOf(buildDomainEventMessage("MDI", "A8279DD", EventType.CIAG_INDUCTION_UPDATED)))
      .thenReturn(java.lang.String.valueOf(buildDomainEventMessage("MDJ", "A8279DE", EventType.CIAG_INDUCTION_CREATED)))
    val result = CompletableFuture.supplyAsync<PublishResponse> {
      PublishResponse.builder().messageId("messageId").build()
    }
    Mockito.`when`(snsClient!!.publish(ArgumentMatchers.any(PublishRequest::class.java))).doThrow(PublishEventException("Failed (publishToTopic) to publish Event"))
    assertThrows<PublishEventException> { outboundEventsPublisher?.publishToTopic(buildDomainEventMessage("MDI", "A8279DD", EventType.CIAG_INDUCTION_UPDATED)) }
  }

  @Test
  fun shouldThrow_PublishEventException_For_TOPIC_NOT_FOUND() {
    val result = CompletableFuture.supplyAsync<PublishResponse> {
      PublishResponse.builder().messageId("messageId").build()
    }
    assertThrows<PublishEventException> { outboundEventsPublisher?.publishToTopic(buildDomainEventMessage("MDI", "A8279DD", EventType.CIAG_INDUCTION_UPDATED)) }
  }

  private fun buildDomainEventMessage(prisonid: String, reference: String, eventType: EventType): OutboundEvent {
    return OutboundEvent(
      eventType,
      PersonReference(listOf(Identifier("NOMS", reference))),
      AdditionalInformation(reference, prisonid, "sacintha-raj", "santhana raj"),
      Instant.now(),
      1,
    )
  }

  fun Any.mockPrivateFields(vararg mocks: Any): Any {
    mocks.forEach { mock ->
      javaClass.declaredFields
        .filter { it.modifiers.and(Modifier.PRIVATE) > 0 || it.modifiers.and(Modifier.PROTECTED) > 0 }
        .firstOrNull { it.type == mock.javaClass }
        ?.also { it.isAccessible = true }
        ?.set(this, mock)
    }
    return this
  }
}
