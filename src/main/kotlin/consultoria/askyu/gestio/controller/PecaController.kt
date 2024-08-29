package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Peca
import consultoria.askyu.gestio.dtos.PecaCadastroRequest
import consultoria.askyu.gestio.interfaces.Controlador
import consultoria.askyu.gestio.service.PecaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pecas")
class PecaController(
    var pecaService: PecaService,
): Controlador(pecaService) {

    @Operation(summary = "Cadastra um peça com base no Id do usuário.",
        description = "Realiza um cadastro de uma peça de roupa.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Cadastro feito com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário não existe.", content = [Content(schema = Schema())])
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.POST],
        allowCredentials = "true"
    )
    @PostMapping("/{id}")
    fun postByUsuarioId(@Valid @PathVariable id: Int, @RequestBody novaPeca: PecaCadastroRequest): ResponseEntity<Peca>{
     val peca  = pecaService.postByUsuarioId(id, novaPeca)
        return ResponseEntity.status(201).body(peca)
    }

    @Operation(summary = "Busca todos as peças pelo Id do usuário.",
        description = "Retorna todas as peças pelo Id do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Peças buscadas com sucesso!"),
            ApiResponse(responseCode = "204", description = "Nenhuma peça cadastrada no sistema."),
            ApiResponse(responseCode = "404", description = "Usuário não existe.", content = [Content(schema = Schema())])
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{id}")
    fun getAllByUsuarioId(@PathVariable id: Int): ResponseEntity<List<Peca>>{
       val listaPeca = pecaService.getAllByUsuarioId(id)
        return ResponseEntity.status(200).body(listaPeca)
    }

    @Operation(summary = "Busca uma peça com base no Id do usuário e da peça.",
        description = "Retorna uma única peça com base no Id de um usuário e de uma peça.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Peças encontrada com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou Peça não existem.", content = [Content(schema = Schema())])
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{usuarioId}/{pecaId}")
    fun getByUsuarioIdAndId(@PathVariable usuarioId: Int, @PathVariable pecaId: Int): ResponseEntity<Peca>{
        val peca = pecaService.getByUsuarioIdAndId(usuarioId, pecaId)
        return ResponseEntity.status(200).body(peca)
    }

    @Operation(summary = "Busca todas as peças com base no Id do usuário e o nome da peça.",
        description = "Retorna toda as peças com base no Id do usuário e no nome da peça, ignorando o case.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Peças encontradas com sucesso!"),
            ApiResponse(responseCode = "204", description = "Nenhuma peça corresponde ao nome usado."),
            ApiResponse(responseCode = "404", description = "Usuário não existe.", content = [Content(schema = Schema())])
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{id}/filtro-nome")
    fun getByUsuarioIdAndNome(@PathVariable id: Int, @RequestParam nome: String): ResponseEntity<List<Peca>>{
        val listaPeca = pecaService.getByUsuarioIdAndNome(id, nome)
        return ResponseEntity.status(200).body(listaPeca)
    }

    @Operation(summary = "Atualiza uma peça com base no Id do usuário e peça.",
        description = "Atualiza uma peça com base no Id do usuário e da peça selecionada.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Peça atualizada com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou Peça não existem.", content = [Content(schema = Schema())])
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.PUT],
        allowCredentials = "true"
    )
    @PutMapping("/{usuarioId}/{pecaId}")
    fun putByUsuarioIdAndId(
        @PathVariable usuarioId: Int,
        @PathVariable pecaId: Int,
        @RequestBody pecaAtualizada: PecaCadastroRequest
    ): ResponseEntity<Peca>{
        val peca = pecaService.putByUsuarioId(usuarioId, pecaId, pecaAtualizada)
        return ResponseEntity.status(200).body(peca)
    }


    @Operation(summary = "Desativa uma peça com base no Id do usuário e peça.",
        description = "Desativa uma peça com base no Id do usuário e da peça selecionada.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Peça desativada com sucesso!", content = [Content(schema = Schema())]),
            ApiResponse(responseCode = "404", description = "Usuário ou Peça não existem.", content = [Content(schema = Schema())])
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.DELETE],
        allowCredentials = "true"
    )
    @DeleteMapping("/{usuarioId}/{pecaId}")
    fun deleteByUsuarioIdAndId(@PathVariable usuarioId: Int, @PathVariable pecaId: Int): ResponseEntity<Void>{
        pecaService.deleteByUsuarioIdAndId(usuarioId, pecaId)
        return ResponseEntity.status(204).build()
    }




}