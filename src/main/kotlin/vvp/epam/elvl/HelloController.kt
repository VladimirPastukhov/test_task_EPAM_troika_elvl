package vvp.epam.elvl

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(API_ROOT)
class HelloController {
    @GetMapping("/hello")
    fun hello() = "HELLO"
}