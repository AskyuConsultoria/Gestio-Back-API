package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.TipoTelefone
import consultoria.askyu.gestio.dtos.TipoTelefoneDTO
import consultoria.askyu.gestio.repository.TipoTelefoneRepository
import consultoria.askyu.gestio.service.TipoTelefoneService
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
@RequestMapping("/tipo-telefone")
class TipoTelefoneController (

    var tipoTelefoneRepository: TipoTelefoneRepository,
    var tipoTelefoneService: TipoTelefoneService,
    val mapper: ModelMapper = ModelMapper()

) {

    @Operation(summary = "Cadastrar tipos de telefone",
        description = "Cadastra uma tipo telefone no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Tipo telefone cadastrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Esse tipo telefone não existe", content = [Content(schema = Schema())])
        ],
    )
    @PostMapping
    fun criarTipoTelefone(@RequestBody @Valid novoTipoTelefone: TipoTelefone) : ResponseEntity<TipoTelefone> {

        val tipoTelefoneSalvo = tipoTelefoneRepository.save(novoTipoTelefone)
        return ResponseEntity.status(201).body(tipoTelefoneSalvo)
    }

    @Operation(summary = "Buscar todos os tipos de telefone",
        description = "Retorna todas os tipos telefone cadastrados.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo tipos de telefone cadastrados"),
            ApiResponse(responseCode = "204", description = "Não há tipos de telefone cadastrados", content = [Content(schema = Schema())]),
        ],
    )

    @GetMapping
    fun getList(usuarioId:Int): ResponseEntity<List<TipoTelefone>> {
        val lista = tipoTelefoneRepository.findByUsuarioId(usuarioId)

        if (lista.isNotEmpty()) {
            return ResponseEntity.status(200).body(lista)
        }
        return ResponseEntity.status(204).build()
    }


    @Operation(summary = "Buscar um tipo de telefone pelo ID",
        description = "Retorna um tipo de telefone específico pelo ID.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Tipo de telefone encontrado"),
            ApiResponse(responseCode = "404", description = "Tipo de telefone não encontrado", content = [Content(schema = Schema())])
        ],
    )
    @GetMapping("/{usuarioId}")
    fun buscarPorUsuario(@PathVariable usuarioId: Int): ResponseEntity<List<TipoTelefone>> {
        val listaTipoTelefone = tipoTelefoneRepository.findByUsuarioId(usuarioId)

        return  ResponseEntity.status(200).body(listaTipoTelefone)
    }

    @Operation(summary = "Atualizar um tipo de telefone",
        description = "Atualiza os dados de um tipo de telefone específico.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Tipo de telefone atualizado com sucesso"),
            ApiResponse(responseCode = "404", description = "Tipo de telefone não encontrado", content = [Content(schema = Schema())])
        ],
    )
    @PutMapping("/{usuarioId}/{tipoTelefoneId}")
    fun atualizarTipoTelefone(
        @PathVariable usuarioId: Int,
        @PathVariable tipoTelefoneId: Int,
        @RequestBody @Valid tipoTelefoneDTO: TipoTelefoneDTO
    ): ResponseEntity<TipoTelefone> {

        val tipoTelefoneAtualizado = tipoTelefoneService.atualizarTipoTelefone(usuarioId, tipoTelefoneId, tipoTelefoneDTO)

       return  ResponseEntity.status(200).body(tipoTelefoneAtualizado)
    }
}
