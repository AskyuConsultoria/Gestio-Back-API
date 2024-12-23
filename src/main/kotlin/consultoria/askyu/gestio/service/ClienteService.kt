package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dtos.ClienteAtualizarDTO
import consultoria.askyu.gestio.dtos.ClienteCadastroDTO
import consultoria.askyu.gestio.dtos.ClienteResponse
import consultoria.askyu.gestio.interfaces.Servico
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.io.File
import java.io.FileReader
import java.time.LocalDate
import java.util.*

@Service
class ClienteService (
    val mapper: ModelMapper = ModelMapper(),
    val clienteRepository:ClienteRepository,
    val usuarioRepository: UsuarioRepository,
    val usuarioService: UsuarioService
): Servico(clienteRepository, mapper){

    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }

    fun validateExistence(usuarioId: Int, clienteId: Int){
        if(!clienteRepository.existsByUsuarioIdAndId(usuarioId, clienteId)){
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

    fun cadastrarTxt (fileCliente: File): List<Cliente>{
        val leitor = Scanner(fileCliente)
        val listaCliente = mutableListOf<Cliente>()

        while(leitor.hasNext()){
            val linha = leitor.nextLine()

            val registro = linha.substring(0, 2)

            if(registro == "02"){
                val nome = linha.substring(2,32).trim()

                val sobrenome = linha.substring(32,62).trim()

                val dtNascString = linha.substring(62,72).trim()
                val dtNasc = LocalDate.parse(dtNascString)

                val email = linha.substring(72,102).trim()

                val responsavelId = linha.substring(102,105).trim()
                var responsavel:Cliente? = null
                if(responsavelId != "") {
                    idValidation(responsavelId.toInt())
                    responsavel = clienteRepository.findById(responsavelId.toInt()).get()
                }

                val usuarioId = linha.substring(105,108).trim().toInt()

                usuarioValidation(usuarioId)
                val usuario = usuarioRepository.findById(usuarioId).get()

                val cliente = Cliente(null, nome, sobrenome, dtNasc, email, responsavel, usuario)

                listaCliente.add(cliente)
                clienteRepository.save(cliente)
            }
        }
        leitor.close()

        return listaCliente
    }

    fun buscarUmCliente(idCliente: Int): Cliente{
        idValidation(idCliente)
        val cliente = clienteRepository.findById(idCliente).get()
        return cliente
    }

    fun buscarClientes(idUsuario: Int): List<ClienteResponse>{
        val listaClientes = clienteRepository.findByUsuarioIdAndAtivoTrue(idUsuario)
        val listaDto = mutableListOf<ClienteResponse>()

        listValidation(listaClientes)

        listaClientes.map {
            listaDto+= mapper.map(it, ClienteResponse::class.java)
        }

        return listaDto
    }

    fun buscarClientesPorNome(idUsuario: Int, nome: String): List<Cliente>{
        val listaClientes = clienteRepository.findByUsuarioIdAndNomeContainsIgnoreCaseAndAtivoTrue(idUsuario, nome)

        listValidation(listaClientes)

        return listaClientes
    }

    fun buscarClientesPorResponsavel(usuarioId: Int, responsavelId: Int): List<Cliente>{
        usuarioService.existenceValidation(usuarioId)
        idValidation(responsavelId)

        val listaClientesDependentes = clienteRepository.findByUsuarioIdAndResponsavelIdAndAtivoTrue(usuarioId, responsavelId)
        listValidation(listaClientesDependentes)

        return listaClientesDependentes
    }


    fun desativarClientePorId(usuarioId: Int, clienteId: Int):Cliente{
        idValidation(clienteId)

        val deletado = clienteRepository.findByUsuarioIdAndId(usuarioId, clienteId)
        deletado.ativo = false
        return clienteRepository.save(deletado)
    }

    fun atualizarCliente(idCliente: Int, dadoAtualizado: ClienteAtualizarDTO): Cliente{
        idValidation(idCliente)

        val cliente = clienteRepository.findById(idCliente).get()

        cliente.nome = dadoAtualizado.nome
        cliente.sobrenome = dadoAtualizado.sobrenome
        cliente.email = dadoAtualizado.email

        if(dadoAtualizado.responsavel != null){
            idValidation(dadoAtualizado.responsavel!!)
            cliente.responsavel = clienteRepository.findById(dadoAtualizado.responsavel!!).get()
        }

        return clienteRepository.save(cliente)
    }

    fun atualizarResponsavel(usuarioId: Int, clienteId: Int, responsavelId: Int): Cliente {
        usuarioService.existenceValidation(usuarioId)
        idValidation(clienteId)
        idValidation(responsavelId)

        val cliente = clienteRepository.findByUsuarioIdAndId(usuarioId, clienteId)
        cliente.responsavel = clienteRepository.findByUsuarioIdAndId(usuarioId, responsavelId)
        return clienteRepository.save(cliente)
    }

    fun retirarResponsavel(usuarioId: Int, clienteId: Int): Cliente{
        usuarioService.existenceValidation(usuarioId)
        idValidation(clienteId)

        val cliente = clienteRepository.findByUsuarioIdAndId(usuarioId, clienteId)
        cliente.responsavel = null
        return clienteRepository.save(cliente)
    }

}