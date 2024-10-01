package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.TecidoGraficoView
import consultoria.askyu.gestio.interfaces.ViewControlador
import consultoria.askyu.gestio.service.TecidoViewService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/tecido-view")
class TecidoViewController(
    var tecidoViewService: TecidoViewService
):ViewControlador(tecidoViewService) {

    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{usuarioId}")
    fun visualizar(
        @PathVariable usuarioId: Int,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) dataInicio: LocalDateTime,
    ): ResponseEntity<List<TecidoGraficoView>>{
        val relatorioTecido = tecidoViewService.visualizar(usuarioId, dataInicio)
        return ResponseEntity.status(200).body(relatorioTecido)
    }
}