package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Telefone
import consultoria.askyu.gestio.dominio.TipoTelefone
import consultoria.askyu.gestio.dtos.TelefoneDTO
import consultoria.askyu.gestio.repository.TelefoneRepository
import consultoria.askyu.gestio.service.TelefoneService
import consultoria.askyu.gestio.service.TipoTelefoneService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
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