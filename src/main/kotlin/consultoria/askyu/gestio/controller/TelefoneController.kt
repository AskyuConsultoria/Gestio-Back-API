package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Telefone
import consultoria.askyu.gestio.repository.TelefoneRepository
import consultoria.askyu.gestio.service.TelefoneService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/telefone")
class TelefoneController(

    var telefoneRepository: TelefoneRepository,
    var telefoneService: TelefoneService,
    val mapper: ModelMapper = ModelMapper()

) {
    @Operation(summary = "Cadastrar novo Telefone",
        description = "Cadastra um Telefone no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Telefone cadastrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Esse Telefone não existe", content = [Content(schema = Schema())])
        ],
    )

    @PostMapping
    fun cadastrarTelefone(@RequestBody @Valid telefone: Telefone) : ResponseEntity<Telefone> {
        val salvo = telefoneService.salvar(telefone)
        return ResponseEntity.status(201).body(salvo)
    }

    @Operation(summary = "Buscar todos os telefones",
        description = "Retorna todos os telefones cadastrados.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo telefones cadastrados"),
            ApiResponse(responseCode = "204", description = "Não há telefones cadastrados", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping
    fun listar(): ResponseEntity<List<Telefone>>{
        val listaTecido = telefoneService.listar()
        return ResponseEntity.status(200).body(listaTecido)
    }

    @Operation(summary = "Buscar um telefone especificado por seu identicador",
        description = "Retorna um telefone pelo seu id e do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo telefone!"),
            ApiResponse(responseCode = "404", description = "Usuário ou telefone não existem", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping("/buscar-um/{usuarioId}/{telefoneId}")
    fun buscarPorId(@PathVariable usuarioId: Int, @PathVariable telefoneId: Int): ResponseEntity<Telefone>{
        val telefone = telefoneService.buscarPorId(usuarioId, telefoneId)
        return ResponseEntity.status(200).body(telefone)
    }

    @Operation(summary = "Busca telefone pelo id do cliente.",
        description = "Retorna uma lista de telefones identificado pelo id do usuário e do cliente")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo telefones pelo número"),
            ApiResponse(responseCode = "204", description = "Nenhum telefone foi encontrado com este número")
        ],
    )

    @GetMapping("/{usuarioId}/{clienteId}")
    fun listarPorCliente(@PathVariable usuarioId: Int, @PathVariable clienteId: Int): ResponseEntity<List<Telefone>>{
        val listaTelefone = telefoneService.listarPorCliente(usuarioId, clienteId)
        return ResponseEntity.status(200).body(listaTelefone)
    }

    @Operation(summary = "Busca telefone pelo número.",
        description = "Retorna uma lista de telefones identificado pelo número")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo telefones pelo número"),
            ApiResponse(responseCode = "204", description = "Nenhum telefone foi encontrado com este número")
        ],
    )

    @GetMapping("/numero")
    fun listarPorNumero(@RequestParam numero: String): ResponseEntity<List<Telefone>>{
        val listaTelefone = telefoneService.listarPorNumero(numero)
        return ResponseEntity.status(200).body(listaTelefone)
    }


    @Operation(summary = "Atualiza um número de telefone pelo id do usuário e seu próprio id.",
        description = "Atualiza um número de telefone pelo id do usuário e seu próprio id.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Numero atualizado!"),
            ApiResponse(responseCode = "404", description = "Usuário ou telefone não foram encontrados")
        ],
    )
    @PatchMapping("/{usuarioId}/{telefoneId}")
    fun atualizarNumero(@PathVariable usuarioId: Int, @PathVariable telefoneId: Int, @RequestParam numero: String): ResponseEntity<Telefone>{
        val telefone = telefoneService.atualizarTelefone(usuarioId, telefoneId, numero)
        return ResponseEntity.status(200).body(telefone)
    }


    @DeleteMapping("/{usuarioId}/{telefoneId}")
    fun deletarTelefone(
        @PathVariable usuarioId: Int,
        @PathVariable telefoneId: Int
    ): ResponseEntity<Void> {
        telefoneService.deletarTelefone(usuarioId, telefoneId)
        return ResponseEntity.status(204).build()
    }


}