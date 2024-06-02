package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dtos.ClienteAtualizarDTO
import consultoria.askyu.gestio.dtos.ClienteCadastroDTO
import consultoria.askyu.gestio.dtos.ClienteResponse
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ClienteService (
    val mapper: ModelMapper = ModelMapper(),
    val clienteRepositorio:ClienteRepository,
    val usuarioRepositorio: UsuarioRepository
){

    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }

    fun usuarioValidation(id:Int): Boolean{
        if(usuarioRepositorio.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O usuário não existe.")
    }

    fun idValidation(id:Int): Boolean{
        if(clienteRepositorio.existsById(id)){
          return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O cliente não existe.")
    }

    fun cadastrar (cliente: ClienteCadastroDTO): Cliente{
        usuarioValidation(cliente.usuario)

        val novoCliente = mapper.map(cliente, Cliente::class.java)

        novoCliente.usuario = usuarioRepositorio.findById(cliente.usuario).get()

        if(cliente.responsavel != null) {
            idValidation(cliente.responsavel!!)
            novoCliente.responsavel = clienteRepositorio.findById(cliente.responsavel!!).get()
        }

        return clienteRepositorio.save(novoCliente)
    }

    fun buscarUmCliente(idCliente: Int): ClienteResponse{
        idValidation(idCliente)
        val cliente = clienteRepositorio.findById(idCliente).get()

        val clienteResponse = mapper.map(cliente, ClienteResponse::class.java)

        return clienteResponse
    }

    fun buscarClientes(idUsuario: Int): List<ClienteResponse>{
        val listaClientes = clienteRepositorio.findByUsuarioId(idUsuario)
        val listaDto = mutableListOf<ClienteResponse>()

        listValidation(listaClientes)

        val dtos = listaClientes.map {
            listaDto+= mapper.map(it, ClienteResponse::class.java)
        }

        return listaDto
    }

    fun desativarClientePorId(id:Int):Cliente{
        idValidation(id)
        var deletado = clienteRepositorio.findById(id).get()

        deletado.ativo = false

        return clienteRepositorio.save(deletado)
    }

    fun atualizarCliente(dadoAtualizado: ClienteAtualizarDTO): Cliente{
        idValidation(dadoAtualizado.id)

        val cliente = clienteRepositorio.findById(dadoAtualizado.id).get()

        cliente.nome = dadoAtualizado.nome
        cliente.sobrenome = dadoAtualizado.sobrenome
        cliente.email = dadoAtualizado.email
        cliente.dtNasc = dadoAtualizado.dtNasc

        if(dadoAtualizado.responsavel != null){
            idValidation(dadoAtualizado.responsavel!!)
            cliente.responsavel = clienteRepositorio.findById(dadoAtualizado.responsavel!!).get()
        }

        return clienteRepositorio.save(cliente)
    }

}