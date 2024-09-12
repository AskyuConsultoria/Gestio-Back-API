package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.AgendamentoViewClienteDependente
import consultoria.askyu.gestio.service.AgendamentoViewClienteDependenteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/agendamento-view-cliente-dependente")
class AgendamentoViewClienteDependenteController(
    val service: AgendamentoViewClienteDependenteService
){
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{usuarioId}/{responsavelId}")
    fun visualizar(@PathVariable usuarioId: Int, @PathVariable responsavelId: Int, @RequestParam nome: String): ResponseEntity<List<AgendamentoViewClienteDependente>> {
        val listaAgendamento =
            service.visualizarPorClienteNome(usuarioId, responsavelId, nome)
        return ResponseEntity.status(200).body(listaAgendamento)
    }
}