package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.integration

import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName
import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.integration.IntegrationTestBase.Companion.log
import java.io.IOException
import java.net.ServerSocket

object LocalStackContainer {
  val instance by lazy { startLocalstackIfNotRunning() }

  fun setLocalStackProperties(localStackContainer: LocalStackContainer, registry: DynamicPropertyRegistry) {
    registry.add("hmpps.sqs.localstackUrl") { localStackContainer.getEndpointOverride(LocalStackContainer.Service.SNS) }
    registry.add("hmpps.sqs.region") { localStackContainer.region }
  }

  private fun startLocalstackIfNotRunning(): LocalStackContainer? {
    if (localstackIsRunning()) return null
    val logConsumer = Slf4jLogConsumer(log).withPrefix("localstack")
    return LocalStackContainer(
      // Unable to upgrade beyond localstack 1.3 as it expects the AWS SDK V2
      DockerImageName.parse("localstack/localstack").withTag("1.3"),
    ).apply {
      withServices(LocalStackContainer.Service.SNS, LocalStackContainer.Service.SQS)
      withEnv("HOSTNAME_EXTERNAL", "localhost")
      withEnv("DEFAULT_REGION", "eu-west-2")
      waitingFor(
        Wait.forLogMessage(".*Ready.*", 1),
      )
      start()
      followOutput(logConsumer)
    }
  }

  private fun localstackIsRunning(): Boolean =
    try {
      val serverSocket = ServerSocket(4566)
      serverSocket.localPort == 0
    } catch (e: IOException) {
      true
    }
}
