package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Pedido
import consultoria.askyu.gestio.dtos.PedidoCadastroDTO
import consultoria.askyu.gestio.dtos.PedidoRelatorioResponse
import consultoria.askyu.gestio.service.PedidoRelatorioService
import consultoria.askyu.gestio.service.PedidoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pedido")
class PedidoController(
    val service: PedidoService,
    val pedidoRelatorioService: PedidoRelatorioService
) {
    @Operation(summary = "Cadastro de Pedido",
        description = "Cadastra um pedido no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso"),
            ApiResponse(responseCode = "204", description = "Não foi possível criar esse pedido.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.POST],
        allowCredentials = "true"
    )
    @PostMapping
    fun cadastro(@Valid @RequestBody novoPedido: PedidoCadastroDTO): ResponseEntity<Pedido> {
        val service = service.cadastrar(novoPedido)
        return ResponseEntity.status(200).body(service)
    }

    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{usuarioId}/relatorio-kpi")
    fun buscarRelatorioPedido(@PathVariable usuarioId: Int): ResponseEntity<PedidoRelatorioResponse> {
        val relatorioPedido = pedidoRelatorioService.getComparacaoPedidos(usuarioId)
        return ResponseEntity.status(200).body(relatorioPedido)
    }

}