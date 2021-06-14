package vvp.epam.elvl.elvl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vvp.epam.elvl.API_ROOT

@RestController
@RequestMapping("$API_ROOT/elvl")
class ELvlController(val service: ELvlService) {

    @GetMapping("/")
    fun getElvl(@RequestParam isin: String) = service.getElvl(isin)
}