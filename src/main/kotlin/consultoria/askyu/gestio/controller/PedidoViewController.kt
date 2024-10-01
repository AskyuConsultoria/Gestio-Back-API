package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.PedidoGraficoView
import consultoria.askyu.gestio.interfaces.ViewControlador
import consultoria.askyu.gestio.service.PedidoViewService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/pedido-view")
class PedidoViewController(
    val pedidoViewService: PedidoViewService
): ViewControlador(pedidoViewService) {

    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{usuarioId}")
    fun visualizar(
        @PathVariable usuarioId: Int,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) dataInicio: LocalDateTime,
    ): ResponseEntity<List<PedidoGraficoView>> {
        val relatorioPedido = pedidoViewService.visualizar(usuarioId, dataInicio)
        return ResponseEntity.status(200).body(relatorioPedido)
    }


}