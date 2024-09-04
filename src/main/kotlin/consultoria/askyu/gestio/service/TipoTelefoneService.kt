package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.TipoTelefone
import consultoria.askyu.gestio.dtos.TipoTelefoneDTO
import consultoria.askyu.gestio.repository.TipoTelefoneRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TipoTelefoneService(

    val mapper: ModelMapper = ModelMapper(),
    val usuarioService: UsuarioService,
    val tipoTelefoneRepository: TipoTelefoneRepository

) {
    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }
    fun buscarPorUsuario (usuarioId: Int): List<TipoTelefone>{
        usuarioService.existenceValidation(usuarioId)

        val listaTipoTelefone = tipoTelefoneRepository.findByUsuarioId(usuarioId)

        listValidation(listaTipoTelefone)

        return listaTipoTelefone
    }

    fun validarTipoTelefone (usuarioId: Int, tipoTelefoneId: Int){

        if (!tipoTelefoneRepository.existsByUsuarioIdAndId(usuarioId, tipoTelefoneId)){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "Lista de tipo telefone está vazia")
        }
    }
    fun atualizarTipoTelefone(usuarioId: Int, tipoTelefoneId: Int, dadoAtualizado: TipoTelefoneDTO): TipoTelefone {

        usuarioService.existenceValidation(usuarioId)

        validarTipoTelefone(usuarioId, tipoTelefoneId)
        val tipoTelefoneOptional = tipoTelefoneRepository.findByUsuarioIdAndId(usuarioId, tipoTelefoneId)

       val tipoTelefone = mapper.map(dadoAtualizado, TipoTelefone::class.java)

        tipoTelefone.usuario!!.id = usuarioId
        tipoTelefone.id = tipoTelefoneId

        return tipoTelefoneRepository.save(tipoTelefone)

    }
}