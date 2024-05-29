package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.Tecido
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.repository.EnderecoRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatusCode
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder

@Service
class EnderecoService(
    val repository: EnderecoRepository,
    val mapper: ModelMapper = ModelMapper()
) {

    // Validações

    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }

    fun validarCepExiste(cep:String){
        if(repository.countByCep(cep) >= 1){
            throw ResponseStatusException(HttpStatusCode.valueOf(409), "CEP ja cadastrado")
        }
    }

    // Serviços

    fun listar(): List<Endereco> {
        val lista = repository.findAll()
        listValidation(lista)
        return lista
    }

    fun buscar(cep:String): ResponseEntity<Endereco>{
        return ResponseEntity.of(repository.findByCep(cep))
    }

    fun cadastrarCEP(cep:String):Endereco{
        validarCepExiste(cep)
        try {
            val restTemplate = RestTemplate()
            val url = "https://viacep.com.br/ws/${cep}/json/?fields=cep,logradouro,bairro,localidade,uf"
            val method = HttpMethod.GET
            val request = RequestEntity<Any>(null, method, UriComponentsBuilder.fromUriString(url).build().toUri())
            val response = restTemplate.exchange(request, Endereco::class.java)
            return repository.save(response.body!!)
        } catch (erro:Exception){
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Esse CEP não existe")
        }
    }

    fun excluirPorCep(cep: String){
        if(repository.existsByCep(cep)){
            repository.deleteByCep(cep)
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "Esse CEP não existe")
    }
}