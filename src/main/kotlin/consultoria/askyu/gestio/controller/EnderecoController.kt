package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dtos.CepCadastroDTO
import consultoria.askyu.gestio.service.EnderecoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
        val salvo = service.viaCep(cep.cep)
        return ResponseEntity.status(200).body(salvo)
    }

    @PostMapping("/teste-cadastro")
    fun cadastroTeste(@RequestBody endereco: Endereco): ResponseEntity<Endereco>{
        service.salvarTeste(endereco)
        return ResponseEntity.status(200).body(endereco)
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

    @Operation(summary = "Atualiza um endereço",
        description = "Atualiza um endereço que ja existe pelo cep.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Esse endereco não está cadastrado", content = [Content(schema = Schema())])
        ],
    )
    @PutMapping("/{cep}")
    fun AtualizarPorCep(@RequestParam cep: String,@Valid @RequestParam cepRequest:CepCadastroDTO):ResponseEntity<Endereco> {
        val salvo = service.viaCep(cepRequest.cep)
        return ResponseEntity.status(200).body(salvo)
    }

    @Operation(summary = "Deleta um endereço",
        description = "Deleta um endereço que ja existe pelo id.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Esse endereco não está cadastrado", content = [Content(schema = Schema())])
        ],
    )
    @DeleteMapping("/{cep}")
    fun DeleterPorCep(@RequestParam cep: String):ResponseEntity<Void> {
        service.excluirPorCep(cep)
        return ResponseEntity.status(200).build()
    }

}