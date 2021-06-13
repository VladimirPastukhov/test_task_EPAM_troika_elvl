package vvp.epam.elvl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ElvlApplicationTests() {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun testHello() {
        restTemplate.getForEntity<String>("http://localhost:8080/api/v1.0/hello").apply {
            assertThat(statusCode).isEqualTo(HttpStatus.OK)
            assertThat(body).isEqualTo("HELLO")
        }
    }
}
