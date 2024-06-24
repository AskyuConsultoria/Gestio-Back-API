package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Etapa
import consultoria.askyu.gestio.dtos.EtapaCadastroDTO
import consultoria.askyu.gestio.service.EtapaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/etapas")

class EtapaController(
    val service: EtapaService
) {

    @Operation(summary = "Cadastro de Etapa",
        description = "Cadastra uma Etapa no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Etapa cadastrada com sucesso"),
            ApiResponse(responseCode = "404", description = "Não foi possível criar essa etapa.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.POST],
        allowCredentials = "true"
    )
    @PostMapping
    fun cadastro(@Valid @RequestBody novaEtapa: EtapaCadastroDTO): ResponseEntity<Etapa> {
        val service = service.cadastrar(novaEtapa)
        return ResponseEntity.status(200).body(service)
    }


    @Operation(summary = "Listagem de etapas pelo id do usuário",
        description = "Busca todas as etapas com base no id do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Etapas encontradas com sucesso!"),
            ApiResponse(responseCode = "204", description =  "Não foi encontrada nenhuma etapa"),
            ApiResponse(responseCode = "404", description = "Usuário não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{idUsuario}")
    fun buscar(@PathVariable idUsuario: Int): ResponseEntity<List<Etapa>>{
        val listaEtapa = service.buscar(idUsuario)
        return ResponseEntity.status(200).body(listaEtapa)
    }

    @Operation(summary = "Busca uma etapa pelo id do usuário",
        description = "Busca uma única etapa com base no id do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Etapas encontradas com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário não foi encontrado.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.GET],
        allowCredentials = "true"
    )
    @GetMapping("/{idUsuario}/{idEtapa}")
    fun buscarUm(@PathVariable idUsuario: Int, @PathVariable idEtapa: Int): ResponseEntity<Etapa>{
        val etapa = service.buscarUm(idUsuario, idEtapa)
        return ResponseEntity.status(200).body(etapa)
    }

    @Operation(summary = "Atualiza uma etapa pelo id do usuário e pelo próprio id da etapa",
        description = "Atualiza uma única etapa com base no id do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Etapas encontradas com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou Etapa não foi encontrada.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.PUT],
        allowCredentials = "true"
    )
    @PutMapping("/{idUsuario}/{idEtapa}")
    fun atualizar(
        @PathVariable idUsuario: Int,
        @PathVariable idEtapa: Int,
        @RequestBody etapaAtualizada: Etapa
    ): ResponseEntity<Etapa>{
        val etapa = service.atualizar(idUsuario, idEtapa, etapaAtualizada)
        return ResponseEntity.status(200).body(etapa)
    }

    @Operation(summary = "Exclui logicamente uma etapa pelo id do usuário e pelo próprio id da etapa",
        description = "Exclui logicamente uma única etapa com base no id do usuário.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Etapas encontradas com sucesso!"),
            ApiResponse(responseCode = "404", description = "Usuário ou Etapa não foi encontrada.", content = [Content(schema = Schema())]),
        ],
    )
    @CrossOrigin(
        origins = ["http://localhost:3333"],
        methods = [RequestMethod.PUT],
        allowCredentials = "true"
    )
    @DeleteMapping("/{idUsuario}/{idEtapa}")
    fun excluir(
        @PathVariable idUsuario: Int,
        @PathVariable idEtapa: Int,
    ): ResponseEntity<Etapa>{
        val etapa = service.excluir(idUsuario, idEtapa)
        return ResponseEntity.status(200).body(etapa)
    }

}