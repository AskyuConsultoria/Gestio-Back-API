package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.AgendamentoViewTotalEtapa
import consultoria.askyu.gestio.interfaces.ViewControlador
import consultoria.askyu.gestio.interfaces.ViewServico
import consultoria.askyu.gestio.service.AgendamentoViewTotalEtapaServico
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/agendamento-view-total-etapa")
class AgendamentoViewTotalEtapaController (
    val agendamentoViewTotalEtapaService: AgendamentoViewTotalEtapaServico
):ViewControlador(agendamentoViewTotalEtapaService) {

    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{usuarioId}")
    fun visualizar(@PathVariable usuarioId: Int): ResponseEntity<List<AgendamentoViewTotalEtapa>> {
        val contagemAgendamento = agendamentoViewTotalEtapaService.visualizar(usuarioId)
        return ResponseEntity.status(200).body(contagemAgendamento)
    }
}