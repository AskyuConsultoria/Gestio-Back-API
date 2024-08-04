package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.TecidoGraficoView
import consultoria.askyu.gestio.service.TecidoViewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tecido-view")
class TecidoViewController(
    var tecidoViewService: TecidoViewService
) {

    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{usuarioId}")
    fun visualizar(@PathVariable usuarioId: Int): ResponseEntity<List<TecidoGraficoView>>{
        val relatorioTecido = tecidoViewService.visualizar(usuarioId)
        return ResponseEntity.status(200).body(relatorioTecido)
    }
}