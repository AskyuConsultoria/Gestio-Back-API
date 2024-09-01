package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.interfaces.Servico
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
    val usuarioService: UsuarioService,
    val clienteService: ClienteService,
    val mapper: ModelMapper = ModelMapper()
): Servico(repository, mapper) {

    // Validações

    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }

    fun existenceValidation(id:Int):Boolean{
        if(repository.existsById(id)) {
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "Endereco não encontrado!")
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

    fun listarPorCliente(usuarioId: Int, clienteId: Int): List<Endereco>{
        usuarioService.existenceValidation(usuarioId)
        clienteService.validateExistence(usuarioId, clienteId)

        val listaEndereco = repository.findByUsuarioIdAndClienteIdAndAtivoTrue(usuarioId, clienteId)
        usuarioService.listValidation(listaEndereco)
        return listaEndereco
    }


    fun buscarPorId(usuarioId: Int, enderecoId: Int): Endereco{
        usuarioService.existenceValidation(usuarioId)
        existenceValidation(enderecoId)
        val endereco = repository.findByUsuarioIdAndIdAndAtivoTrue(usuarioId, enderecoId)
        return endereco
    }

    fun cadastrarCEP(cep:String):Endereco{
        validarCepExiste(cep)
        val endereco = viaCep(cep)
        return repository.save(endereco)
    }

    fun viaCep(cep:String):Endereco{
        try {
            val restTemplate = RestTemplate()
            val url = "https://viacep.com.br/ws/${cep}/json/?fields=cep,logradouro,bairro,localidade,uf"
            val method = HttpMethod.GET
            val request = RequestEntity<Any>(null, method, UriComponentsBuilder.fromUriString(url).build().toUri())
            val response = restTemplate.exchange(request, Endereco::class.java)
            return response.body!!
        } catch (erro:Exception){
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Esse CEP não existe")
        }
    }
}