package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.Tecido
import consultoria.askyu.gestio.dominio.Telefone
import consultoria.askyu.gestio.dominio.TipoTelefone
import consultoria.askyu.gestio.dtos.TelefoneDTO
import consultoria.askyu.gestio.dtos.TipoTelefoneDTO
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.TelefoneRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TelefoneService (

    val mapper: ModelMapper = ModelMapper(),
    val telefoneRepository: TelefoneRepository,
    val clienteRepository: ClienteRepository,
    val tipoTelefoneRepository: TelefoneRepository

) {

    fun listar(): List<Telefone> {
        val listaTelefone = telefoneRepository.findAll()
        listValidation(listaTelefone)
        return listaTelefone
    }

    fun listarPorNumero(numero: String): List<Telefone> {
        val listaTelefone = telefoneRepository.findByNumero(numero)
        listValidation(listaTelefone)
        return listaTelefone
    }
    fun salvar(telefone: Telefone): Telefone {
        val dto = mapper.map(telefone, Telefone::class.java)
        telefoneRepository.save(dto)
        return dto
    }

    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }

    fun telefoneValidation(usuarioId:Int, clienteId: Int, tipoTelefoneId: Int, telefoneId: Int){
        if(!telefoneRepository.existsByUsuarioIdAndClienteIdAndTipoTelefoneIdAndId(usuarioId, clienteId, tipoTelefoneId, telefoneId)){

            throw ResponseStatusException(HttpStatusCode.valueOf(404), "O Telefone não existe.")
        }

    }

    fun validarTelefone (usuarioId: Int, tipoTelefoneId: Int, telefoneId: Int){

        if (!telefoneRepository.existsByUsuarioIdAndId(usuarioId, tipoTelefoneId)){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "Lista de telefone está vazia")
        }
    }

    fun deletarTelefone(usuarioId:Int, clienteId: Int, tipoTelefoneId: Int, telefoneId: Int): Telefone {
        telefoneValidation(usuarioId, clienteId, tipoTelefoneId, telefoneId)

        val telefone = telefoneRepository.findByUsuarioIdAndId(usuarioId, tipoTelefoneId)

        telefone.usuario!!.id
        telefone.cliente!!.id
        telefone.tipoTelefone!!.id
        telefone.ativo = false

        return telefoneRepository.save(telefone)
    }
}