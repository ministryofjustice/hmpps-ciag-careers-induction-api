package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.integration

import org.awaitility.Awaitility
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.test.context.ActiveProfiles
import software.amazon.awssdk.services.sns.SnsAsyncClient
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.HmppsCiagCareersInductionApi
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.helpers.JwtAuthHelper
import uk.gov.justice.hmpps.sqs.HmppsQueueFactory
import uk.gov.justice.hmpps.sqs.HmppsQueueService
import uk.gov.justice.hmpps.sqs.HmppsSqsProperties
import uk.gov.justice.hmpps.sqs.HmppsTopicFactory
import uk.gov.justice.hmpps.sqs.MissingQueueException
import uk.gov.justice.hmpps.sqs.MissingTopicException
import java.util.concurrent.TimeUnit

@SpringBootTest(
  webEnvironment = RANDOM_PORT,
  classes = arrayOf(
    HmppsCiagCareersInductionApi::class,
  ),
)
@ActiveProfiles("test")
abstract class IntegrationTestBase internal constructor() {
  @Autowired
  lateinit var restTemplate: TestRestTemplate

  @Autowired
  lateinit var jwtAuthHelper: JwtAuthHelper

  @SpyBean
  lateinit var hmppsQueueService: HmppsQueueService

  internal fun setAuthorisation(
    user: String = "test-client",
    roles: List<String> = listOf(),
  ): (HttpHeaders) {
    return jwtAuthHelper.setAuthorisationForUnitTests(user, roles)
  }
  companion object {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

   /* private val localStackContainer = LocalStackContainer.instance

    @JvmStatic
    @DynamicPropertySource
    fun testcontainers(registry: DynamicPropertyRegistry) {
      localStackContainer?.also { setLocalStackProperties(it, registry) }
    }*/
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @TestConfiguration
    class SqsConfig(private val hmppsQueueFactory: HmppsQueueFactory, private val hmppsTopicFactory: HmppsTopicFactory) {

      @Bean("offenderqueue-sqs-client")
      fun offenderQueueSqsClient(
        hmppsSqsProperties: HmppsSqsProperties,
        @Qualifier("offenderqueue-sqs-dlq-client") offenderQueueSqsDlqClient: SqsAsyncClient,

      ): SqsAsyncClient =
        with(hmppsSqsProperties) {
          val config = queues["offenderqueue"]
            ?: throw MissingQueueException("HmppsSqsProperties config for offenderqueue not found")
          hmppsQueueFactory.createSqsAsyncClient(config, hmppsSqsProperties, offenderQueueSqsDlqClient)
        }

      @Bean("hmppsdomainqueue-sqs-client")
      fun hmppsDomainQueueSqsClient(
        hmppsSqsProperties: HmppsSqsProperties,
        @Qualifier("hmppsdomainqueue-sqs-dlq-client") hmppsDomainQueueSqsDlqClient: SqsAsyncClient,
      ): SqsAsyncClient =
        with(hmppsSqsProperties) {
          val config = queues["hmppsdomainqueue"]
            ?: throw MissingQueueException("HmppsSqsProperties config for hmppsdomainqueue not found")
          hmppsQueueFactory.createSqsAsyncClient(config, hmppsSqsProperties, hmppsDomainQueueSqsDlqClient)
        }

      @Bean("hmppseventtopic-sns-client")
      fun eventTopicSnsClient(
        hmppsSqsProperties: HmppsSqsProperties,
      ): SnsAsyncClient =
        with(hmppsSqsProperties) {
          val config = topics["hmppseventtopic"]
            ?: throw MissingTopicException("HmppsSqsProperties config for hmppseventtopic not found")
          hmppsTopicFactory.createSnsAsyncClient("hmppseventtopic", config, hmppsSqsProperties)
        }
    }
  }

  init {
    // set awaitility defaults
    Awaitility.setDefaultPollInterval(500, TimeUnit.MILLISECONDS)
    Awaitility.setDefaultTimeout(3, TimeUnit.SECONDS)
  }
}
