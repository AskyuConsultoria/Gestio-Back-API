package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Agendamento
import consultoria.askyu.gestio.dominio.Etapa
import consultoria.askyu.gestio.dtos.AgendamentoCadastroDTO
import consultoria.askyu.gestio.dtos.EtapaCadastroDTO
import consultoria.askyu.gestio.service.AgendamentoService
import consultoria.askyu.gestio.service.EtapaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMethod

class EtapaController(
    val service: EtapaService
) {

    @Operation(summary = "Cadastro de Etapa",
        description = "Cadastra uma Etapa no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Etapa cadastrada com sucesso"),
            ApiResponse(responseCode = "204", description = "Não foi possível criar essa etapa.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.POST],
        allowCredentials = "true"
    )
    @PostMapping
    fun cadastro(@Valid @RequestBody novaEtapa: EtapaCadastroDTO): ResponseEntity<Etapa> {
        val service = service.cadastrar(novaEtapa)
        return ResponseEntity.status(200).body(service)
    }
}