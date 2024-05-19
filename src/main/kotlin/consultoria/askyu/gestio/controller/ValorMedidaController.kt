package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.ValorMedida
import consultoria.askyu.gestio.dtos.ValorMedidaCadastroRequest
import consultoria.askyu.gestio.service.ValorMedidaService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/valores-medidas")
class ValorMedidaController(
    var valorMedidaService: ValorMedidaService
) {
    @PostMapping("/{usuarioId}/{clienteId}/{pecaId}/{nomeMedidaId}/{itemPedidoId}")
    fun cadastrar(
       @PathVariable usuarioId: Int,
       @PathVariable clienteId: Int,
       @PathVariable pecaId: Int,
       @PathVariable nomeMedidaId: Int,
       @PathVariable itemPedidoId: Int,
       @RequestBody novoValorMedida: ValorMedidaCadastroRequest
    ): ResponseEntity<ValorMedida>{
        val valorMedida = valorMedidaService.postByIds(
            usuarioId, clienteId, pecaId, nomeMedidaId, itemPedidoId, novoValorMedida
        )
        return ResponseEntity.status(201).body(valorMedida)
    }

    @GetMapping("/{usuarioId}/{itemPedidoId}")
    fun buscarPorItemPedido(
        @PathVariable usuarioId: Int,
        @PathVariable itemPedidoId: Int
    ): ResponseEntity<List<ValorMedida>>{
        val listaValorMedida = valorMedidaService.getByUsuarioIdAndItemPedidoId(
            usuarioId, itemPedidoId
        )
        return ResponseEntity.status(200).body(listaValorMedida)
    }

    @PutMapping("/{usuarioId}/{itemPedidoId}/{valorMedidaId}")
    fun atualizar(
        @Valid
        @PathVariable usuarioId: Int,
        @PathVariable itemPedidoId: Int,
        @PathVariable valorMedidaId: Int,
        @RequestBody valorMedidaAtualizado: ValorMedidaCadastroRequest
    ): ResponseEntity<ValorMedida>{
        val valorMedida = valorMedidaService.putByUsuarioIdAndItemPedidoIdAndId(
            usuarioId, itemPedidoId, valorMedidaId, valorMedidaAtualizado
        )
        return ResponseEntity.status(200).body(valorMedida)
    }
}