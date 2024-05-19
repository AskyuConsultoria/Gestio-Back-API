package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.ItemPedido
import consultoria.askyu.gestio.dtos.ItemPedidoCadastroRequest
import consultoria.askyu.gestio.service.ItemPedidoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/itens-pedidos")
class ItemPedidoController(
    var itemPedidoService: ItemPedidoService
) {

    @Operation(summary = "Cadastra uma ficha com base no Id do usuário, cliente e peça",
        description = "Realiza o cadastro de uma ficha com base no Id do usuário, cliente e peça.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Cadastro de ficha feito com sucesso!"),
            ApiResponse(responseCode = "404", description = "Cliente ou Peça não existem.", content = [Content(schema = Schema())])
        ],
    )
    @PostMapping("/{usuarioId}/{clienteId}/{pecaId}")
    fun cadastrar(
        @Valid
       @PathVariable usuarioId: Int,
       @PathVariable clienteId: Int,
       @PathVariable pecaId: Int,
       @RequestBody novoItemPedido: ItemPedidoCadastroRequest
    ): ResponseEntity<ItemPedido>{
        val itemPedido = itemPedidoService.postByUsuarioIdAndClienteIdAndPecaId(
            usuarioId, clienteId, pecaId, novoItemPedido
        )
        return ResponseEntity.status(200).body(itemPedido)
    }

    @Operation(summary = "Busca todas as fichas com base no Id do usuário",
        description = "Retorna todas as fichas com base no Id do usuário")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Cadastro de ficha feito com sucesso!"),
            ApiResponse(responseCode = "204", description = "Nenhuma cadastrada pelo usuário.", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Usuário não existe.", content = [Content(schema = Schema())])
        ],
    )
    @GetMapping("/{usuarioId}")
    fun buscarPorUsuario(@PathVariable usuarioId: Int): ResponseEntity<List<ItemPedido>>{
        val listaItemPedido = itemPedidoService.getByUsuarioId(usuarioId)
        return ResponseEntity.status(200).body(listaItemPedido)
    }

    @Operation(summary = "Desativa uma ficha pelo Id do usuário e da própria ficha.",
        description = "Realiza a exclusão lógica de uma ficha pelo Id do usuário e da própria medida.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Ficha desativada com sucesso!"),
            ApiResponse(responseCode = "404", description = "Ficha não foi encontrada.", content = [Content(schema = Schema())])
        ],
    )
    @DeleteMapping("/{usuarioId}/{itemPedidoId}")
    fun excluirPorUsuario(
        @PathVariable usuarioId: Int,
        @PathVariable itemPedidoId: Int
    ): ResponseEntity<Void>{
        itemPedidoService.deleteByUsuarioIdAndId(usuarioId, itemPedidoId)
        return ResponseEntity.status(204).build()
    }
}