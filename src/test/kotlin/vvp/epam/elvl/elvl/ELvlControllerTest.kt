package vvp.epam.elvl.elvl

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import vvp.epam.elvl.API_ROOT
import vvp.epam.elvl.quote.Quote
import vvp.epam.elvl.quote.QuoteProcessResponse
import java.math.BigDecimal
import kotlin.random.Random

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ELvlControllerTest(
    @Autowired val restTemplate: TestRestTemplate,
    @LocalServerPort val port: Int
) {

    @Test
    fun testElvlProcessTime() {
        var time = -System.currentTimeMillis()
        runBlocking {
            (0..4).map { async { postRandomQuotes(100) } }.awaitAll()
        }
        time += System.currentTimeMillis()
        println("TIME: $time")
        Assertions.assertThat(time).isLessThan(4000)
    }

    val isins = arrayOf("AAA", "BBB", "CCC")
    fun postRandomQuotes(count: Int) {
        for (i in 0..count) {
            val isin = isins[Random.nextInt(0, isins.size - 1)]
            val bid = Random.nextDouble(0.0, 100.0)
            val ask = bid + Random.nextDouble(0.0, 100.0)
            Quote(isin, bid.BD, ask.BD).withCurrentTimestamp()
//                .apply { println(this) }
                .post()
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
}

val Int.BD: BigDecimal get() = BigDecimal(this)
val Double.BD: BigDecimal get() = BigDecimal(this)
