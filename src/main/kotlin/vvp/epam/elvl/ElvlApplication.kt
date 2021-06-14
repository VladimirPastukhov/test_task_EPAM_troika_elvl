package vvp.epam.elvl

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


const val API_ROOT = "/api/v1.0/"

@SpringBootApplication
class ElvlApplication{

}

fun main(args: Array<String>) {
	runApplication<ElvlApplication>(*args)
}



