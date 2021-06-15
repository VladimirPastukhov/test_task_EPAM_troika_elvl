package vvp.epam.elvl.quote

import java.math.BigDecimal
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Quote(
    @Column(length = 12)
    val isin: String,
    val bid: BigDecimal? = null,
    val ask: BigDecimal? = null,
    @Id @GeneratedValue val id: Long? = null
) {
    lateinit var timestamp: Timestamp
    fun withCurrentTimestamp() = apply { timestamp = Timestamp(System.currentTimeMillis()) }
}