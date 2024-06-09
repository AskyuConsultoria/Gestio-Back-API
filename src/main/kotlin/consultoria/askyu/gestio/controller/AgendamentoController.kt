package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Agendamento
import consultoria.askyu.gestio.dtos.AgendamentoCadastroDTO
import consultoria.askyu.gestio.service.AgendamentoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/agenda")
class AgendamentoController(
    val service: AgendamentoService
) {

    @Operation(summary = "Cadastro de Agendamento",
        description = "Cadastra um agendamento no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Agendamento cadastrado com sucesso"),
            ApiResponse(responseCode = "204", description = "Não foi possível criar esse agendamento.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.POST],
        allowCredentials = "true"
    )
    @PostMapping
    fun cadastro(@Valid @RequestBody novoAgendamento: AgendamentoCadastroDTO): ResponseEntity<Agendamento> {
        val service = service.cadastrar(novoAgendamento)
        return ResponseEntity.status(200).body(service)
    }
}