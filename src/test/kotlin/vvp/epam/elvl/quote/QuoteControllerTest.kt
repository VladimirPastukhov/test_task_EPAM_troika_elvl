package vvp.epam.elvl.quote

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import vvp.epam.elvl.API_ROOT
import java.math.BigDecimal.ONE
import java.math.BigDecimal.TEN

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class QuoteControllerTest {

    @MockBean
    lateinit var quoteRepository: QuoteRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun postValid() {
        val quote = Quote("AAABBBCCCDDD", ONE, TEN).withCurrentTimestamp()
        validateValidation(quote, QuoteStatus.OK)
    }

    @Test
    fun postLongIsin() {
        val quote = Quote("AAABBBCCCDDDEEEEEEEE", ONE, TEN).withCurrentTimestamp()
        validateValidation(quote, QuoteStatus.TOO_LONG_ISIN)
    }

    @Test
    fun postBidNotLessThenAsk() {
        val quote = Quote("AAABBBCCCDDDEEEEEEEE", ONE, ONE).withCurrentTimestamp()
        validateValidation(quote, QuoteStatus.BID_NOT_LESS_THEN_ASK)
    }

    fun validateValidation(quote: Quote, quoteStatus: QuoteStatus){
        Mockito.`when`(quoteRepository.save(anyObject())).thenReturn(quote)

        mockMvc.post("$API_ROOT/quotes/") {
            contentType = MediaType.APPLICATION_JSON
            content = quote.toJson()
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.validation", `is`(quoteStatus.name)) }
        }
    }
    fun Any.toJson() = objectMapper.writeValueAsString(this)
}

private fun <T> anyObject(): T = Mockito.anyObject<T>()