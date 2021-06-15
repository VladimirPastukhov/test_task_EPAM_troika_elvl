package vvp.epam.elvl.elvl

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ElvlRepository: CrudRepository<Elvl, String>