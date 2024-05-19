package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dtos.ClienteCadastroRequest
import consultoria.askyu.gestio.service.ClienteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clientes")
class ClienteController(
    var clienteService: ClienteService
) {

    @PostMapping("/{usuarioId}")
    fun postByUsuarioId(
        @PathVariable usuarioId: Int,
        @RequestBody novoCliente: ClienteCadastroRequest
    ): ResponseEntity<Cliente>{
        val cliente = clienteService.postByUsuarioId(usuarioId, novoCliente)
        return ResponseEntity.status(200).body(cliente)
    }
}