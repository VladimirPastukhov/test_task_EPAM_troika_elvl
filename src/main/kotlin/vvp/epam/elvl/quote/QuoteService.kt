package vvp.epam.elvl.quote

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import vvp.epam.elvl.elvl.ELvlService

@Service
class QuoteService(
    val repository: QuoteRepository,
    val eLvlService: ELvlService
) {

    fun process(quote: Quote): QuoteProcessResponse {
        val validation = quote.withCurrentTimestamp().validate()

        if (validation != QuoteStatus.OK)
            return QuoteProcessResponse(validation, quote)

        var savedQuote: Quote? = null
        runBlocking {
            launch { eLvlService.update(quote) }
            savedQuote = async { repository.save(quote) }.await()
        }
        return QuoteProcessResponse(validation, savedQuote!!)
//        eLvlService.update(quote)
//        val savedQuote = repository.save(quote)
//        return QuoteProcessResponse(validation, savedQuote)
    }
}

data class QuoteProcessResponse(val validation: QuoteStatus, val quote: Quote)

fun Quote.validate(): QuoteStatus {
    if (bid != null && ask != null && bid >= ask)
        return QuoteStatus.BID_NOT_LESS_THEN_ASK
    if (isin.length > 12)
        return QuoteStatus.TOO_LONG_ISIN
    return QuoteStatus.OK
}

enum class QuoteStatus { OK, TOO_LONG_ISIN, BID_NOT_LESS_THEN_ASK }