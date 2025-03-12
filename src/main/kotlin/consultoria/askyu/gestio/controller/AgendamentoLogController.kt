package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.AgendamentoLog
import consultoria.askyu.gestio.service.AgendamentoLogService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/agendamento-log")
class AgendamentoLogController(
    val service: AgendamentoLogService
) {

    @Operation(summary = "Listagem dos logs de um agendamento com base no id do usuário e do agendamento",
        description = "Busca os logs de um agendamento com base no id do usuário e do agendamento")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Log de agendamentos encontradas com sucesso!"),
            ApiResponse(responseCode = "204", description =  "Não foi encontrado nenhum log de agendamento"),
            ApiResponse(responseCode = "404", description = "Usuário não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping("/{usuarioId}/{agendamentoId}")
    fun buscarLogs(
        @PathVariable usuarioId: Int,
        @PathVariable agendamentoId: Int,
    ): ResponseEntity<List<AgendamentoLog>> {
        val listaAgendamentoLog = service.buscarPorIdAgendamento(usuarioId, agendamentoId)
        return ResponseEntity.status(200).body(listaAgendamentoLog)
    }
}