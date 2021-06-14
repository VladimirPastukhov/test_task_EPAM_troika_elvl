package vvp.epam.elvl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ElvlApplicationTests(
    @Autowired val restTemplate: TestRestTemplate,
    @LocalServerPort val port: Int
) {

    @Test
    fun testHello() {
        restTemplate.getForEntity<String>("http://localhost:$port$API_ROOT/hello").apply {
            assertThat(statusCode).isEqualTo(HttpStatus.OK)
            assertThat(body).isEqualTo("HELLO")
        }
    }
}
