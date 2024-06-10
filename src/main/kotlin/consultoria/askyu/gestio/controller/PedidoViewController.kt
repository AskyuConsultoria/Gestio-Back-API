package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.PedidoGraficoView
import consultoria.askyu.gestio.service.PedidoViewService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pedido-view")
class PedidoViewController(
    val pedidoViewService: PedidoViewService
) {

    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.POST],
        allowCredentials = "true"
    )
    @GetMapping("/{usuarioId}")
    fun visualizar(@PathVariable usuarioId: Int): List<PedidoGraficoView>{
      return pedidoViewService.visualizar(usuarioId)
    }
}