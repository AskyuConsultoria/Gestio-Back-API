package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Telefone
import consultoria.askyu.gestio.dtos.TelefoneDTO
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
@RequestMapping("/telefones")
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
    @PostMapping("/{usuarioId}/{clienteId}/{tipoTelefoneId}")
    fun cadastrarTelefone(
        @PathVariable usuarioId: Int,
        @PathVariable clienteId: Int,
        @PathVariable tipoTelefoneId: Int,
        @Valid
        @RequestBody dadoAtualizado: TelefoneDTO
    ) : ResponseEntity<Telefone> {
        val salvo = telefoneService.salvarTelefone(usuarioId, clienteId, tipoTelefoneId, dadoAtualizado)
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

    @GetMapping("/{usuarioId}")
    fun listar(@PathVariable usuarioId: Int): ResponseEntity<List<Telefone>>{
        val listaTelefone = telefoneService.listar(usuarioId)
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
    @GetMapping("/{usuarioId}/filtro-numero")
    fun listarPorNumero(@PathVariable usuarioId: Int, @RequestParam numero: String): ResponseEntity<List<Telefone>>{
        val listaTelefone = telefoneService.listarPorNumero(usuarioId, numero)
        return ResponseEntity.status(200).body(listaTelefone)
    }

    @PutMapping("/{usuarioId}/{clienteId}/{tipoTelefoneId}/{telefoneId}")
    fun atualizarTelefone(
        @PathVariable usuarioId: Int,
        @PathVariable clienteId: Int,
        @PathVariable tipoTelefoneId: Int,
        @PathVariable telefoneId: Int,
        @RequestBody dadoAtualizado: TelefoneDTO
    ): ResponseEntity<Telefone>  {
        val telefone = telefoneService.atualizarTelefone(usuarioId, clienteId, tipoTelefoneId, telefoneId, dadoAtualizado)
        return ResponseEntity.status(200).body(telefone)
    }

    @DeleteMapping("/{usuarioId}/{clienteId}/{tipoTelefoneId}/{telefoneId}")
    fun deletarTelefone(
        @PathVariable usuarioId: Int,
        @PathVariable clienteId: Int,
        @PathVariable tipoTelefoneId: Int,
        @PathVariable telefoneId: Int
    ): ResponseEntity<Void> {
        telefoneService.deletarTelefone(usuarioId, clienteId, tipoTelefoneId, telefoneId)
        return ResponseEntity.status(200).build()
    }


}