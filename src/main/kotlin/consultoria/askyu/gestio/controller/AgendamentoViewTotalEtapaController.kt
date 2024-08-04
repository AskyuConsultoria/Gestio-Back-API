package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.AgendamentoViewTotalEtapa
import consultoria.askyu.gestio.service.AgendamentoViewTotalEtapaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/agendamento-view-total-etapa")
class AgendamentoViewTotalEtapaController (
    val agendamentoViewTotalEtapaService: AgendamentoViewTotalEtapaService
) {

    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{usuarioId}")
    fun visualizar(@PathVariable usuarioId: Int): ResponseEntity<List<AgendamentoViewTotalEtapa>> {
        val contagemAgendamento = agendamentoViewTotalEtapaService.visualizar(usuarioId)
        return ResponseEntity.status(200).body(contagemAgendamento)
    }
}