package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.PedidoGraficoView
import consultoria.askyu.gestio.dtos.PedidoRelatorioPorMesResponse
import consultoria.askyu.gestio.interfaces.ViewControlador
import consultoria.askyu.gestio.service.PedidoPorMesRelatorioService
import consultoria.askyu.gestio.service.PedidoViewService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.core.io.Resource
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/pedido-view")
class PedidoViewController(
    val pedidoViewService: PedidoViewService,
    val pedidoPorMesService: PedidoPorMesRelatorioService
): ViewControlador(pedidoViewService) {

    @Operation(summary = "Exibe dados para graficos",
        description = "Exibe dados para graficos")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Peça desativada com sucesso!", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Usuário ou Peça não existem.", content = [Content(schema = Schema())])
        ],
    )

    @GetMapping("/{usuarioId}")
    fun visualizar(
        @PathVariable usuarioId: Int,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) dataInicio: LocalDateTime,
    ): ResponseEntity<List<PedidoGraficoView>> {
        val relatorioPedido = pedidoViewService.visualizar(usuarioId, dataInicio)
        return ResponseEntity.status(200).body(relatorioPedido)
    }


    @GetMapping("/extrair/{usuarioId}")
    fun exportarClientesPedidos(
        @PathVariable usuarioId:Int
    ): ResponseEntity<Resource>{
        val csvTecido = pedidoViewService.exportar(usuarioId)
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM
        headers.setContentDispositionFormData("attachment", csvTecido.file.name)
        return ResponseEntity.status(200).body(csvTecido)

    }

    @Operation(summary = "Exibe dados para graficos",
        description = "Exibe dados para graficos")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Peça desativada com sucesso!", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Usuário ou Peça não existem.", content = [Content(schema = Schema())])
        ],
    )

    @GetMapping("/mes/{usuarioId}")
    fun visualizarPorMes(
        @PathVariable usuarioId: Int,
        @RequestParam ano:Int
    ): ResponseEntity<List<PedidoRelatorioPorMesResponse>> {
        val relatorioPedido = pedidoPorMesService.getComparacaoPedidos(usuarioId, ano)
        return ResponseEntity.status(200).body(relatorioPedido)
    }


    @GetMapping("/mes/extrair/{usuarioId}")
    fun exportarPedidosPorMes(
        @PathVariable usuarioId:Int
    ): ResponseEntity<Resource>{
        val csvPedido = pedidoPorMesService.exportar(usuarioId)
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM
        headers.setContentDispositionFormData("attachment", csvPedido.file.name)
        return ResponseEntity.status(200).body(csvPedido)

    }

}