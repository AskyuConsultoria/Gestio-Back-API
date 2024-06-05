package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.repository.EnderecoRepository
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dtos.CepCadastroDTO
import consultoria.askyu.gestio.service.EnderecoService
import consultoria.askyu.gestio.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/enderecos")
class EnderecoController(val service: EnderecoService
) {

    @Operation(summary = "Buscar todas os endereços",
        description = "Retorna todas os endereços cadastrados.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Exibindo endereços cadastradas"),
            ApiResponse(responseCode = "204", description = "Não há endereços cadastradas", content = [Content(schema = Schema())]),
        ],
    )
    @GetMapping
    fun getList(): ResponseEntity<List<Endereco>> {
        val lista = service.listar()
        return ResponseEntity.status(200).body(lista)
    }

    @Operation(summary = "Cadastrar endereço",
        description = "Cadastra uma endereço no sistema.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Endereço cadastrada com sucesso"),
            ApiResponse(responseCode = "404", description = "Esse cep não existe", content = [Content(schema = Schema())])
        ],
    )
    @PostMapping
    fun cadastrarPorCEP(@Valid @RequestParam cep:CepCadastroDTO):ResponseEntity<Endereco> {
        val salvo = service.cadastrarCEP(cep.cep)
        return ResponseEntity.status(200).body(salvo)
    }

    @Operation(summary = "Obtem um endereço",
        description = "Obtem um endereco que ja existe, buscado pelo cep.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Esse endereco não está cadastrado", content = [Content(schema = Schema())])
        ],
    )
    @GetMapping("/cep")
    fun getEndereco(@RequestParam cep:String):ResponseEntity<Endereco> {
        return service.buscar(cep)
    }

}