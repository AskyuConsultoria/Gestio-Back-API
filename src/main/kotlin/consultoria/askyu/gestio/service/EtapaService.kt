package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Etapa
import consultoria.askyu.gestio.dtos.EtapaCadastroDTO
import consultoria.askyu.gestio.interfaces.Servico
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
    val usuarioRepository: UsuarioRepository
): Servico(repository, mapper) {
        fun listValidation(lista:List<*>){
            if(lista.isEmpty()){
                throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
            }
        }

    fun validateExistence(etapaId: Int){
        if(!repository.existsById(etapaId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "Etapa não foi encontrado!"
            )
        }
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

    fun buscarUm(idUsuario: Int, idEtapa: Int): Etapa {
        idUsuarioValidation(idUsuario)
        validateExistence(idEtapa)

        val etapaBuscada = repository.findByUsuarioIdAndIdAndAtivoTrue(idUsuario, idEtapa)

        val etapa = mapper.map(etapaBuscada, Etapa::class.java)

        return etapa
    }

    fun buscar(idUsuario: Int): List<Etapa>{
        idUsuarioValidation(idUsuario)

        val listaEtapa = repository.findByUsuarioIdAndAtivoTrue(idUsuario)
        val listaDto = mutableListOf<Etapa>()

        listValidation(listaEtapa)

        listaEtapa.map {
            listaDto+= mapper.map(it, Etapa::class.java)
        }

        return listaDto
    }

    fun atualizar(idUsuario: Int, idEtapa: Int, etapaAtualizada: Etapa): Etapa {
        idUsuarioValidation(idUsuario)
        validateExistence(idEtapa)

        etapaAtualizada.usuario!!.id = idUsuario
        etapaAtualizada.id = idEtapa
        return repository.save(etapaAtualizada)
    }

    fun excluir(idUsuario: Int, idEtapa: Int): Etapa {
        idUsuarioValidation(idUsuario)
        validateExistence(idEtapa)

        val etapa = repository.findByUsuarioIdAndIdAndAtivoTrue(idUsuario, idEtapa)
        etapa.ativo = false
        return repository.save(etapa)
    }




}