package askyu.gestio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GestioApplication

fun main(args: Array<String>) {
	runApplication<GestioApplication>(*args)
}
