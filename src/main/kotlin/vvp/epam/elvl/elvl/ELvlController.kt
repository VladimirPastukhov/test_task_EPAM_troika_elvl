package vvp.epam.elvl.elvl

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vvp.epam.elvl.API_ROOT

@RestController
@RequestMapping("$API_ROOT/elvls")
class ELvlController(
    val service: ELvlService
) {
    @GetMapping("/{isin}")
    fun getElvl2(@PathVariable isin: String) = service.getElvl(isin)

    @GetMapping("/")
    fun getElvls(): List<Elvl> = service.getAllElvl()
}