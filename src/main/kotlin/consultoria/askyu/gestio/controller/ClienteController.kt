package consultoria.askyu.gestio.controller

import askyu.gestio.dto.TecidoCadastroRequest
import consultoria.askyu.gestio.Tecido
import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dtos.ClienteRequest
import consultoria.askyu.gestio.service.ClienteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

class ClienteController (val clienteService: ClienteService){

    @PostMapping
    fun cadastrar(@RequestBody @Valid cliente: Cliente): ResponseEntity<Cliente> {
        val novoCliente= cliente
        clienteService.salvar(cliente)
        return ResponseEntity.status(201).body(novoCliente)
    }

    @GetMapping
    fun listar(): ResponseEntity<List<Cliente>> {
        val listaTecido = clienteService.getLista()
        return ResponseEntity.status(200).body(listaTecido)
    }


    @GetMapping("/{nome}")
    fun listarPorNome(@RequestParam nome: String): ResponseEntity<List<Cliente>> {
        val lista= clienteService.listarNomeCliente(nome)
        return ResponseEntity.status(200).body(lista)
    }


    @GetMapping("/{id}")
    fun buscarTecidoPorId(@PathVariable id: Int): ResponseEntity<Optional<Cliente>> {
        clienteService.validarIdCliente(id)
        val clienteId = clienteService.buscarPorId(id)
        return ResponseEntity.status(200).body(clienteId)
    }


    @PutMapping()
    fun atualizar(
        @RequestParam id: Int,
        @RequestBody @Valid cliente: Cliente,
    ): ResponseEntity<Cliente> {
        cliente.id = id
        clienteService.salvar(cliente)
        return ResponseEntity.status(200).body(cliente)
    }


    @DeleteMapping()
    fun desativar(@RequestParam id: Int): ResponseEntity<Void> {
        clienteService.desativar(id)
        return ResponseEntity.status(200).build()
    }

}