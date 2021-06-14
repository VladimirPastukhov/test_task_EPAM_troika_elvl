package vvp.epam.elvl.elvl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import vvp.epam.elvl.API_ROOT
import javax.websocket.server.PathParam

@RestController
@RequestMapping("$API_ROOT/elvl")
class ELvlController(@Autowired val service: ELvlService) {

    @GetMapping("/{isin}")
    fun getElvl2(@PathVariable isin: String) = isin


}