package consultoria.askyu.gestio

import consultoria.askyu.gestio.dominio.Pedido
import consultoria.askyu.gestio.dtos.TecidoCadastroRequest
import consultoria.askyu.gestio.interfaces.Servico
import consultoria.askyu.gestio.service.UsuarioService
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TecidoService(
    var usuarioService: UsuarioService,
    var tecidoRepository: TecidoRepository,
    val mapper: ModelMapper = ModelMapper()
): Servico(tecidoRepository, mapper){
    fun salvar(usuarioId: Int, tecidoId: Int, novoTecido: TecidoCadastroRequest): Tecido{
        usuarioService.existenceValidation(usuarioId)
        val tecido = mapper.map(novoTecido, Tecido::class.java)
        tecido.usuario = usuarioService.findById(usuarioId)
        tecido.id = tecidoId
        return tecidoRepository.save(tecido)
    }

    fun atualizar(usuarioId: Int, tecidoId: Int, tecidoAtualizado: Tecido): Tecido{
        usuarioService.existenceValidation(usuarioId)
        existenceValidation(usuarioId, tecidoId)
        tecidoAtualizado.usuario = usuarioService.findById(usuarioId)
        tecidoAtualizado.id = tecidoId
        return tecidoRepository.save(tecidoAtualizado)
    }

    fun listar(usuarioId: Int): List<Tecido> {
      val listaTecido = tecidoRepository.findByUsuarioId(usuarioId)
        validarSeListaEVazia(listaTecido)
        return listaTecido
    }

    fun listarPorNome(usuarioId: Int, nome: String): Tecido? {
        // busca os tecidos pelo usuario e ordena por nome (ordem alfabetica)
        val listaTecido = tecidoRepository.findByUsuarioId(usuarioId).sortedBy { it.nome }
        validarSeListaEVazia(listaTecido)

        // chuta o inicio e fim da lista, pegando size - 1 para equiparar a posição real
        var inicio = 0
        var fim = listaTecido.size - 1

       // Enquanto inicio for menor ou igual a fim, há uma faixa válida para buscar.
        while (inicio <= fim) {
            val meio = (inicio + fim) / 2
            val tecidoAtual = listaTecido[meio]

            // Se o tecido atual for igual ao nome procurado, retorna o tecido.
            when {
                tecidoAtual.nome == nome -> return tecidoAtual
                // Se o tecido atual é menor, ajusta 'inicio' para a direita.
                tecidoAtual.nome!! < nome -> inicio = meio + 1
                // Se o tecido atual é maior, ajusta 'fim' para a esquerda.
                else -> fim = meio - 1
            }
        }

        return null
    }

    // Versão simplificada do kotlin:
//    val index = listaTecido.binarySearch { it.nome!!.compareTo(nome) }
//    return if (index >= 0) listaTecido[index] else null


    fun buscarTecidoPorId(usuarioId: Int, tecidoId: Int): Tecido {
        usuarioService.existenceValidation(usuarioId)
        existenceValidation(usuarioId, tecidoId)
        val tecido = tecidoRepository.findByUsuarioIdAndId(usuarioId, tecidoId)
        return tecido
    }

    fun desativar(usuarioId: Int, tecidoId: Int): Tecido{
        existenceValidation(usuarioId, tecidoId)
        val tecido = tecidoRepository.findByUsuarioIdAndId(usuarioId, tecidoId)
        tecido.usuario!!.id = usuarioId
        tecido.id = tecidoId
        tecido.ativo = false
        return tecidoRepository.save(tecido)
    }


    fun validarSeListaEVazia(listaTecido: List<*>) {
        if (listaTecido.isEmpty()) {
            throw ResponseStatusException(
                HttpStatusCode.valueOf(204), "A lista de teciods encontra-se vazia."
            )
        }
    }

    fun existenceValidation(usuarioId: Int, tecidoId: Int){
        if(!tecidoRepository.existsByUsuarioIdAndId(usuarioId, tecidoId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "O tecido acessado não existe no sistema."
            )
        }
    }

}