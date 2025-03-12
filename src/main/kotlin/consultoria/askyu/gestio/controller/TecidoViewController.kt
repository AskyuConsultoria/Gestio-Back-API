package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.TecidoGraficoView
import consultoria.askyu.gestio.interfaces.ViewControlador
import consultoria.askyu.gestio.service.TecidoViewService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.core.io.Resource
import java.time.LocalDateTime

@RestController
@RequestMapping("/tecido-view")
class TecidoViewController(
    var tecidoViewService: TecidoViewService
):ViewControlador(tecidoViewService) {


    @GetMapping("/{usuarioId}")
    fun visualizar(
        @PathVariable usuarioId: Int,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) dataInicio: LocalDateTime,
    ): ResponseEntity<List<TecidoGraficoView>>{
        val relatorioTecido = tecidoViewService.visualizar(usuarioId, dataInicio)
        return ResponseEntity.status(200).body(relatorioTecido)
    }

    @GetMapping("/extrair/{usuarioId}")
    fun exportarTecidos(
        @PathVariable usuarioId:Int
    ): ResponseEntity<Resource>{
        val csvTecido = tecidoViewService.exportar(usuarioId)
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM
        headers.setContentDispositionFormData("attachment", csvTecido.file.name)
        return ResponseEntity.status(200).body(csvTecido)

    }
}