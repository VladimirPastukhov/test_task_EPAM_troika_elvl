package vvp.epam.elvl.elvl

import org.springframework.stereotype.Service
import vvp.epam.elvl.quote.Quote
import java.math.BigDecimal

@Service
class ELvlService() {

    val eLvlMap = mutableMapOf<String, BigDecimal>()

    fun update(quote: Quote){
        val oldElvl = getElvl(quote.isin)
        val newElvl = calcElvl(quote.bid, quote.ask, oldElvl)
        if(newElvl != null && newElvl != oldElvl)
            save(quote.isin, newElvl)
    }

    fun getElvl(isin: String): BigDecimal = eLvlMap[isin] ?: BigDecimal.ONE

    fun save(isin: String, ELvl: BigDecimal){

    }
}

fun calcElvl(bid: BigDecimal?, ask: BigDecimal?, oldElvl: BigDecimal?): BigDecimal? {
    if (oldElvl == null) {
        return bid ?: ask
    } else {
        if (bid != null && bid > oldElvl) {
            return bid
        }
        if (ask != null && ask < oldElvl) {
            return ask
        }
        return oldElvl
    }
}