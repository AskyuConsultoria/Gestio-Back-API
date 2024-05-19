package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.ValorMedida
import consultoria.askyu.gestio.dtos.ValorMedidaCadastroRequest
import consultoria.askyu.gestio.service.ValorMedidaService
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
        return ResponseEntity.status(200).body(valorMedida)
    }

    
}