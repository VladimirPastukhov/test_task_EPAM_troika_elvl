package vvp.epam.elvl.elvl

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Elvl(
    @Id
    @Column(length = 12)
    val isin: String,
    val elvl: BigDecimal,
)