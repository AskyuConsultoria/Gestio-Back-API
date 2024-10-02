package consultoria.askyu.gestio.llm

import org.springframework.web.bind.annotation.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

@RestController
@RequestMapping("/api-llm")
class IaController {

    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.POST],
        allowCredentials = "true"
    )
    @PostMapping()
    fun processarInput(@RequestBody ia: IA): String {
        val processBuilder = ProcessBuilder("C:/Users/kayky/AppData/Local/Programs/Python/Python312/python.exe", "C:/py/teste_2.py", ia.contexto, ia.etapa, ia.input)

        processBuilder.redirectErrorStream(true)

        val process = processBuilder.start()

        val output = StringBuilder()
        BufferedReader(InputStreamReader(process.inputStream, StandardCharsets.UTF_8)).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }
        }

        val exitCode = process.waitFor()

        println("ExitCode: ${exitCode}")

        if (exitCode != 0) {
            return "Erro: O processo Python terminou com o código de saída $exitCode\nOutput:\n$output"
        }

        println("Output: $output")

        return output.toString()

    }

}