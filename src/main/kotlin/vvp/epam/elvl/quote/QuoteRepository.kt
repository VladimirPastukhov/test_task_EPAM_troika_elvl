package vvp.epam.elvl.quote

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface QuoteRepository : CrudRepository<Quote, Long>