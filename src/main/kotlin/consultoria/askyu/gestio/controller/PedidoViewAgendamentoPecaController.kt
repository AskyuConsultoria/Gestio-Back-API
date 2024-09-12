package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.PedidoViewAgendamentoPeca
import consultoria.askyu.gestio.service.PedidoViewAgendamentoPecaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pedido-view-agendamento-peca")
class PedidoViewAgendamentoPecaController(
    val service: PedidoViewAgendamentoPecaService
) {
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{usuarioId}")
    fun visualizar(@PathVariable usuarioId: Int, @RequestParam nome: String): ResponseEntity<List<PedidoViewAgendamentoPeca>> {
        val listaAgendamento =
            service.visualizarPorNomeDaPeca(usuarioId, nome)
        return ResponseEntity.status(200).body(listaAgendamento)
    }

}