package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Moradia
import consultoria.askyu.gestio.dtos.MoradiaResponse
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.EnderecoRepository
import consultoria.askyu.gestio.repository.MoradiaRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class MoradiaService (
    val repository: MoradiaRepository,
    val usuario: UsuarioRepository,
    val cliente: ClienteRepository,
    val endereco: EnderecoRepository,
    val mapper: ModelMapper = ModelMapper()
) {

    fun validarUsuario(idUsuario: Int){
        if(!usuario.existsById(idUsuario)){
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado!")
        }
    }

    fun getLista(idUsuario: Int): List<MoradiaResponse>{
        val lista = repository.findByUsuarioId(idUsuario)
        validarLista(lista)

        val dtos = lista.map {
            mapper.map(it, MoradiaResponse::class.java)
        }
        return dtos
    }

    fun validarLista(lista:List<*>) {
        if (lista.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(204))
        }
    }

    fun buscarPorCliente(idCliente: Int, idUsuario: Int): List<Moradia> {
        validarIdUsuarioCliente(idUsuario, idCliente)
        val listaMoradia = repository.findByUsuarioIdAndClienteId(idUsuario,idCliente)

        return listaMoradia
    }

    fun buscarPorEndereco(idUsuario: Int,idEndereco: Int, idCliente: Int): List<Moradia>  {
        validarIdUsuarioEndereco(idUsuario, idCliente, idEndereco)
        val listaMoradia = repository.findByUsuarioIdAndEnderecoId(idUsuario,idEndereco)

        return listaMoradia
    }

    fun salvar(idUsuario: Int, idCliente: Int,idEndereco: Int, moradia: Moradia): Moradia{
        validarUsuario(idUsuario)
        validarIdUsuarioCliente(idUsuario,idCliente)
        validarIdUsuarioEndereco(idUsuario,idCliente, idEndereco)

       return repository.save(moradia)
    }

    fun excluirPorId(id: Int,idUsuario: Int, idCliente: Int,idEndereco: Int): Optional<Moradia>{
        validarUsuario(idUsuario)
        validarIdUsuarioCliente(idUsuario,idCliente)
        validarIdUsuarioEndereco(idUsuario,idCliente, idEndereco)
        validarIdMoradiaUsuario(idUsuario, id)
        repository.deleteById(id)
        return repository.findByUsuarioIdAndId(idUsuario, id)
    }

    fun validarIdMoradiaUsuario(idUsuario: Int,id: Int){
        if(!repository.existsByUsuarioIdAndId(idUsuario,id)){
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Moradia não encontrada!")
        }

    } fun validarIdUsuarioCliente(idUsuario: Int,id: Int){
        if(!cliente.existsByUsuarioIdAndId(idUsuario,id)){
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Cliente não encontrado!")
        }

    } fun validarIdUsuarioEndereco(idUsuario: Int,idEndereco: Int, idCliente: Int){
        if(!endereco.existsByUsuarioIdAndClienteIdAndId(idUsuario, idCliente, idEndereco)){
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Endereço não encontrado!")
        }

    }


}
