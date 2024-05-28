package consultoria.askyu.gestio.controller

import consultoria.askyu.gestio.repository.EnderecoRepository
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dtos.CepCadastroDTO
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
@RequestMapping("/endereco")
class EnderecoController(
    val repository: EnderecoRepository,
    val mapper: ModelMapper = ModelMapper()
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
        val lista = repository.findAll()

        if (lista.isNotEmpty()) {
            return ResponseEntity.status(200).body(lista)
        }
        return ResponseEntity.status(204).build()
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
    fun cadastrarPorCEP(@Valid @RequestParam cepRequest:CepCadastroDTO):Boolean {

        var cep = cepRequest.cep


        if(repository.countByCep(cep) >= 1){
            return throw ResponseStatusException(HttpStatusCode.valueOf(409), "CEP ja cadastrado")
        }

        try {
            val restTemplate = RestTemplate()
            val url = "https://viacep.com.br/ws/${cep}/json/?fields=cep,logradouro,bairro,localidade,uf"
            val method = HttpMethod.GET
            val request = RequestEntity<Any>(null, method, UriComponentsBuilder.fromUriString(url).build().toUri())
            val response = restTemplate.exchange(request, Endereco::class.java)
            repository.save(response.body!!)
            return true
        } catch (erro:Exception){
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Esse CEP não existe")
        }
    }
    @GetMapping("/cep")
    fun getEndereco(@RequestParam cep:String):ResponseEntity<Endereco> {

        return ResponseEntity.of(repository.findByCep(cep))

    }

}