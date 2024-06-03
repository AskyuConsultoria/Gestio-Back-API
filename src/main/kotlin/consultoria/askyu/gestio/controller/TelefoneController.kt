package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.Tecido
import consultoria.askyu.gestio.dominio.Telefone
import consultoria.askyu.gestio.dominio.TipoTelefone
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