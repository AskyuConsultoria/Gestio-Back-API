package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dtos.ClienteAtualizarDTO
import consultoria.askyu.gestio.dtos.ClienteCadastroDTO
import consultoria.askyu.gestio.dtos.ClienteResponse
import consultoria.askyu.gestio.service.ClienteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clientes")
class ClienteController (
    val service: ClienteService
){
    @Operation(summary = "Cadastro de Cliente",
        description = "Cadastra um cliente no sistema do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Cliente cadastrado com sucesso"),
            ApiResponse(responseCode = "204", description = "Não foi possível cadastrar esse cliente.", content = [Content(schema = Schema())]),
        ],
    )
    @PostMapping
        fun cadastro(@Valid @RequestBody novoCliente: ClienteCadastroDTO): ResponseEntity<Cliente>{
        val service = service.cadastrar(novoCliente)

        return ResponseEntity.status(200).body(service)
    }

    @Operation(summary = "Pesquisar um cliente",
        description = "Busca um cliente do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo cliente."),
            ApiResponse(responseCode = "204", description = "Não foi possível exibir esse cliente.", content = [Content(schema = Schema())]),
        ],
    )
    @GetMapping("/{idCliente}")
    fun getUmCliente (@RequestParam idCliente: Int): ResponseEntity<ClienteResponse>{
        val cliente = service.buscarUmCliente(idCliente)

        return ResponseEntity.status(200).body(cliente)
    }

    @Operation(summary = "Pesquisar todos os clientes",
        description = "Busca todos os clientes do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo lista de clientes."),
            ApiResponse(responseCode = "204", description = "Não foi possível exibir essa lista de cliente.", content = [Content(schema = Schema())]),
        ],
    )
    @GetMapping("/{idUsuario}")
    fun getTodosOsClientes (@RequestParam idUsuario: Int): ResponseEntity<List<ClienteResponse>>{
        val listaCliente = service.buscarClientes(idUsuario)

        return ResponseEntity.status(200).body(listaCliente)
    }

    @Operation(summary = "Desativar cliente",
        description = "Desativar (visualmente será excluir) um cliente no sistema do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Cliente desativado com sucesso"),
            ApiResponse(responseCode = "404", description = "Não foi possível desativar esse cliente.", content = [Content(schema = Schema())]),
        ],
    )
    @DeleteMapping("/{idCliente}")
    fun desativarCliente(@RequestParam idCliente: Int): ResponseEntity<ClienteResponse>{
        service.desativarClientePorId(idCliente)

        return ResponseEntity.status(204).build()
    }

    @Operation(summary = "Atualizar Cliente",
        description = "Atualiza os campos de um cliente no sistema do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            ApiResponse(responseCode = "204", description = "Não foi possível atualizar esse cliente.", content = [Content(schema = Schema())]),
        ],
    )
    @PutMapping("/{idCliente}")
    fun atualizarCliente(@Valid @PathVariable idCliente: Int, @RequestBody dados: ClienteAtualizarDTO): ResponseEntity<Cliente>{
        val clienteAtualizado = service.atualizarCliente(dados)

        return ResponseEntity.status(200).body(clienteAtualizado)
    }

}


