package vvp.epam.elvl.elvl

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.getForObject
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import vvp.epam.elvl.API_ROOT
import vvp.epam.elvl.quote.Quote
import vvp.epam.elvl.quote.QuoteProcessResponse
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ELvlControllerTest(
    @Autowired val restTemplate: TestRestTemplate,
    @LocalServerPort val port: Int
) {

    val isins = arrayOf("AAA", "AAA", "AAA", "BBB", "BBB", "BBB", "CCC", "CCC", "CCC")


    @Test
    fun getElvl() = runBlocking {
        for (i in 0..4) {
            launch { runIsins(100) }
        }
    }

    @Test
    fun getAllElvl() {
        Quote("AAA", null, 2.BD).withCurrentTimestamp().post().let { println(it) }
        Quote("AAA", 1.9.BD, 2.5.BD).withCurrentTimestamp().post().let { println(it) }

        Quote("BBB", 1.BD, null).withCurrentTimestamp().post().let { println(it) }
        Quote("BBB", 1.5.BD, null).withCurrentTimestamp().post().let { println(it) }

        Quote("CCC", 1.BD, 2.BD).withCurrentTimestamp().post().let { println(it) }
        Quote("CCC", 1.5.BD, 2.BD).withCurrentTimestamp().post().let { println(it) }
        Quote("CCC", 1.BD, 1.3.BD).withCurrentTimestamp().post().let { println(it) }
        Quote("CCC", 1.5.BD, 1.3.BD).withCurrentTimestamp().post().let { println(it) }

        Quote("DDD", null, null).withCurrentTimestamp().post().let { println(it) }

        val expected = setOf(
            Elvl("AAA", 2.BD),
            Elvl("BBB", 1.5.BD),
            Elvl("CCC", 1.3.BD)
        )

        val response = restTemplate.getForObject<Array<Elvl>>("http://localhost:$port$API_ROOT/elvls/")!!.toSet()
        response.forEach { println(it) }

        Assertions.assertThat(response).isEqualTo(expected)
    }

    fun Quote.post() = restTemplate.exchange(
        "http://localhost:$port$API_ROOT/quotes/",
        HttpMethod.POST,
        HttpEntity(this),
        QuoteProcessResponse::class.java
    )


    val Int.BD: BigDecimal get() = BigDecimal(this)
    val Float.BD: BigDecimal get() = BigDecimal(this.toDouble())
    val Double.BD: BigDecimal get() = BigDecimal(this)


    suspend fun runIsins(count: Int) {
//        val port = 8080
        for (i in 0..count) {
            delay(10)
            val isin = isins[i % isins.size]
            restTemplate.getForEntity<String>("http://localhost:$port$API_ROOT/elvl/$isin")
                .let { println(it.body) }
        }
    }
}