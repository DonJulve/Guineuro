@file:Suppress("NoWildcardImports", "WildcardImport", "SpreadOperator")

package guineuroChat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CrossOrigin
import java.util.Scanner

@SpringBootApplication
class Application
fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@RestController
@RequestMapping("/guineuro")
class GuineuroController() {

    private val guineuro = Guineuro()

    @PostMapping("/respond")
    @CrossOrigin(origins = ["http://localhost:3000"]) // Habilita CORS solo para este endpoint
    fun getResponse(@RequestBody request: GuineuroRequest): GuineuroResponse {
        val scanner = Scanner(request.message)
        val response = guineuro.respond(scanner)
        return GuineuroResponse(response)
    }
}

data class GuineuroRequest(val message: String)
data class GuineuroResponse(val response: String)

