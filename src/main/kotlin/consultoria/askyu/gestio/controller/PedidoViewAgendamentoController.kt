package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.PedidoViewAgendamento
import consultoria.askyu.gestio.service.PedidoViewAgendamentoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pedido-view-agendamento")
class PedidoViewAgendamentoController(
    val service: PedidoViewAgendamentoService
) {
    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/por-peca/{usuarioId}")
    fun visualizar(@PathVariable usuarioId: Int, @RequestParam nome: String): ResponseEntity<List<PedidoViewAgendamento>> {
        val listaAgendamento =
            service.visualizarPorNomeDaPeca(usuarioId, nome)
        return ResponseEntity.status(200).body(listaAgendamento)
    }

    @CrossOrigin(
        origins = ["http://localhost:3333", "http://192.168.15.3:3333/"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/por-tecido/{usuarioId}")
    fun visualizarPorNomeTecido(@PathVariable usuarioId: Int, @RequestParam nome: String): ResponseEntity<List<PedidoViewAgendamento>> {
        val listaAgendamento =
            service.visualizarPorNomeDoTecido(usuarioId, nome)
        return ResponseEntity.status(200).body(listaAgendamento)
    }

}