package vvp.epam.elvl.elvl

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.web.server.LocalServerPort
import vvp.epam.elvl.API_ROOT

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