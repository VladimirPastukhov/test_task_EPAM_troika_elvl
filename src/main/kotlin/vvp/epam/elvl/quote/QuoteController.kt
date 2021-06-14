package vvp.epam.elvl.quote

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import vvp.epam.elvl.API_ROOT
import java.math.BigDecimal.ONE
import java.math.BigDecimal.TEN

@RestController
@RequestMapping("$API_ROOT/quotes")

class QuoteController(
    val quoteService: QuoteService,
) {
    @PostMapping("/")
    fun addQuote(@RequestBody quote: Quote) = quoteService.process(quote)

    @GetMapping("/example")
    fun example() = Quote("ISINISINISIN", ONE, TEN, 0L).withCurrentTimestamp()
}