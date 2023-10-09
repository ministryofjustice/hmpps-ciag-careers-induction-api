package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.messaging

import com.amazonaws.services.sns.model.PublishRequest
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import uk.gov.justice.hmpps.sqs.HmppsQueueService
import java.util.function.Supplier

private val log = KotlinLogging.logger {}
private const val NOTIFICATION = "Notification"

@Component
class OutboundEventsPublisher(

  hmppsQueueService: HmppsQueueService,
  private val objectMapper: ObjectMapper,
) {
  companion object {
    const val TOPIC_ID = "domainevents"
    val log: Logger = LoggerFactory.getLogger(this::class.java)
  }

  private val hmppsDomainTopic by lazy {
    hmppsQueueService.findByTopicId(TOPIC_ID) ?: throw IllegalStateException("domainevents not found")
  }
  private val topicArn by lazy { hmppsDomainTopic.arn }
  private val topicSnsClient by lazy { hmppsDomainTopic.snsClient }

  fun publishToTopic(outboundEvent: OutboundEvent) {
    try {
      topicSnsClient.publish(
        PublishRequest(
          topicArn,
          objectMapper.writeValueAsString(outboundEvent),
        ),

      )
    } catch (e: Throwable) {
      val message = "Failed (publishToTopic) to publish Event $outboundEvent.eventType to $TOPIC_ID"
      log.error(message, e)
      throw PublishEventException(message, e)
    }
  }
}
class PublishEventException(message: String? = null, cause: Throwable? = null) :
  RuntimeException(message, cause),
  Supplier<PublishEventException> {
  override fun get(): PublishEventException {
    return PublishEventException(message, cause)
  }
}
