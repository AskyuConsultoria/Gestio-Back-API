package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Etapa
import consultoria.askyu.gestio.dtos.EtapaCadastroDTO
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.EtapaRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class EtapaService(
    val mapper: ModelMapper = ModelMapper(),
    val repository: EtapaRepository,
    val clienteRepository: ClienteRepository,
    val usuarioRepository: UsuarioRepository
) {
        fun listValidation(lista:List<*>){
            if(lista.isEmpty()){
                throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
            }
        }

    fun validateExistence(usuarioId: Int){
        if(!repository.existsById(usuarioId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "Etapa não foi encontrado!"
            )
        }
    }

    fun idValidation(id:Int): Boolean{
        if(repository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "Etapa não existe.")
    }

    fun idUsuarioValidation(id:Int): Boolean{
        if(usuarioRepository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O usuario não existe.")
    }

    fun cadastrar(novaEtapa: EtapaCadastroDTO): Etapa {
        idUsuarioValidation(novaEtapa.usuario!!)

        val etapa = mapper.map(novaEtapa, Etapa::class.java)

        etapa.usuario = usuarioRepository.findById(novaEtapa.usuario!!).get()

        return repository.save(etapa)
    }

    fun buscarUm(idUsuario: Int): Etapa {
        idValidation(idUsuario)
        val etapaBuscada = repository.findById(idUsuario).get()

        val etapa = mapper.map(etapaBuscada, Etapa::class.java)

        return etapa
    }

    fun buscar(idUsuario: Int): List<Etapa>{
        //
        val listaEtapa = repository.findByUsuarioId(idUsuario)
        val listaDto = mutableListOf<Etapa>()

        listValidation(listaEtapa)

        listaEtapa.map {
            listaDto+= mapper.map(it, Etapa::class.java)
        }

        return listaDto
    }
}