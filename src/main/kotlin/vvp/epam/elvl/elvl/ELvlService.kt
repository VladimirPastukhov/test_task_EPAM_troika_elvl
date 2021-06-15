package vvp.epam.elvl.elvl

import org.springframework.stereotype.Service
import vvp.epam.elvl.quote.Quote
import java.math.BigDecimal

@Service
class ELvlService(
    val repository: ElvlRepository
) {
    val eLvlMap = mutableMapOf<String, BigDecimal>()

    @Synchronized//test shows that such rough synch (not by certain isin) doesn`t affect response time much)
    fun  update(quote: Quote) {
        val oldElvl = eLvlMap[quote.isin]
        val newElvl = calcElvl(quote.bid, quote.ask, oldElvl)
        if (newElvl != null && newElvl != oldElvl){
            repository.save(Elvl(quote.isin, newElvl))
            eLvlMap[quote.isin] = newElvl
        }
    }

    fun getElvl(isin: String): BigDecimal? = eLvlMap[isin]

    fun getAllElvl() = eLvlMap.map { (isin, elvl) -> Elvl(isin, elvl) }
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