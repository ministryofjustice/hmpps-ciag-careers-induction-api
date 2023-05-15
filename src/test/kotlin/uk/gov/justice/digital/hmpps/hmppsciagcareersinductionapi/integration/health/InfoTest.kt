package uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.integration.health

import uk.gov.justice.digital.hmpps.hmppsciagcareersinductionapi.integration.IntegrationTestBase

class InfoTest : IntegrationTestBase() {

  /*@Test
  fun `Info page is accessible`() {
    webTestClient.get()
      .uri("/info")
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("build.name").isEqualTo("hmpps-ciag-careers-induction-api")
  }

  @Test
  fun `Info page reports version`() {
    webTestClient.get().uri("/info")
      .exchange()
      .expectStatus().isOk
      .expectBody().jsonPath("build.version").value<String> {
        assertThat(it).startsWith(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
      }
  }*/
}
