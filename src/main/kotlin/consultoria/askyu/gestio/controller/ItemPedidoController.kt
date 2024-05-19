package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.ItemPedido
import consultoria.askyu.gestio.dtos.ItemPedidoCadastroRequest
import consultoria.askyu.gestio.service.ItemPedidoService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/itens-pedidos")
class ItemPedidoController(
    var itemPedidoService: ItemPedidoService
) {

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

    @GetMapping("/{usuarioId}")
    fun buscarPorUsuario(@PathVariable usuarioId: Int): ResponseEntity<List<ItemPedido>>{
        val listaItemPedido = itemPedidoService.getByUsuarioId(usuarioId)
        return ResponseEntity.status(200).body(listaItemPedido)
    }

    @DeleteMapping("/{usuarioId}/{itemPedidoId}")
    fun excluirPorUsuario(
        @PathVariable usuarioId: Int,
        @PathVariable itemPedidoId: Int
    ): ResponseEntity<Void>{
        itemPedidoService.deleteByUsuarioIdAndId(usuarioId, itemPedidoId)
        return ResponseEntity.status(204).build()
    }
}