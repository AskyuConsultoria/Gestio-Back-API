package askyu.gestio.controller

import askyu.gestio.dominio.pessoa.Endereco
import askyu.gestio.repository.EnderecoRepository
import org.springframework.beans.factory.annotation.Autowired
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
class EnderecoController {

    @Autowired
    lateinit var repository: EnderecoRepository

    @GetMapping
    fun getList(): ResponseEntity<List<Endereco>> {
        return ResponseEntity.status(200).body(repository.findAll())
    }

    @PostMapping
    fun cadastrarPorCEP(@RequestParam cep:String) {
        try {
            val restTemplate = RestTemplate()
            val url = "https://viacep.com.br/ws/${cep}/json/?fields=cep,logradouro,bairro,localidade,uf"
            val method = HttpMethod.GET
            val request = RequestEntity<Any>(null, method, UriComponentsBuilder.fromUriString(url).build().toUri())
            val response = restTemplate.exchange(request, Endereco::class.java)
            repository.save(response.body!!)
        } catch (erro:Exception){
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Esse CEP não existe")
        }
    }
}