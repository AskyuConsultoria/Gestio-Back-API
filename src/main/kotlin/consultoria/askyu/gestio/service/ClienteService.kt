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
    val clienteRepository:ClienteRepository,
    val usuarioRepository: UsuarioRepository,
    val usuarioService: UsuarioService
){

    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }

    fun validateExistence(usuarioId: Int, itemPedidoId: Int){
        if(!clienteRepository.existsByUsuarioIdAndId(usuarioId, itemPedidoId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "Cliente não foi encontrado!"
            )
        }
    }

    fun usuarioValidation(id:Int): Boolean{
        if(usuarioRepository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O usuário não existe.")
    }

    fun idValidation(id:Int): Boolean{
        if(clienteRepository.existsById(id)){
          return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O cliente não existe.")
    }

    fun cadastrar (cliente: ClienteCadastroDTO): Cliente{
        usuarioValidation(cliente.usuario)

        val novoCliente = mapper.map(cliente, Cliente::class.java)

        novoCliente.usuario = usuarioRepository.findById(cliente.usuario).get()

        if(cliente.responsavel != null) {
            idValidation(cliente.responsavel!!)
            novoCliente.responsavel = clienteRepository.findById(cliente.responsavel!!).get()
        }

        return clienteRepository.save(novoCliente)
    }

    fun buscarUmCliente(idCliente: Int): ClienteResponse{
        idValidation(idCliente)
        val cliente = clienteRepository.findById(idCliente).get()

        val clienteResponse = mapper.map(cliente, ClienteResponse::class.java)

        return clienteResponse
    }

    fun buscarClientes(idUsuario: Int): List<ClienteResponse>{
        val listaClientes = clienteRepository.findByUsuarioId(idUsuario)
        val listaDto = mutableListOf<ClienteResponse>()

        listValidation(listaClientes)

        listaClientes.map {
            listaDto+= mapper.map(it, ClienteResponse::class.java)
        }

        return listaDto
    }

    fun desativarClientePorId(id:Int):Cliente{
        idValidation(id)
        val deletado = clienteRepository.findById(id).get()

        deletado.ativo = false

        return clienteRepository.save(deletado)
    }

    fun atualizarCliente(dadoAtualizado: ClienteAtualizarDTO): Cliente{
        idValidation(dadoAtualizado.id)

        val cliente = clienteRepository.findById(dadoAtualizado.id).get()

        cliente.nome = dadoAtualizado.nome
        cliente.sobrenome = dadoAtualizado.sobrenome
        cliente.email = dadoAtualizado.email
        cliente.dtNasc = dadoAtualizado.dtNasc

        if(dadoAtualizado.responsavel != null){
            idValidation(dadoAtualizado.responsavel!!)
            cliente.responsavel = clienteRepository.findById(dadoAtualizado.responsavel!!).get()
        }

        return clienteRepository.save(cliente)
    }

}