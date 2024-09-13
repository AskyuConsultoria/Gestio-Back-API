package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.PedidoViewAgendamento
import consultoria.askyu.gestio.repository.PedidoViewAgendamentoRepository
import org.springframework.stereotype.Service

@Service
class PedidoViewAgendamentoService(
    val repository: PedidoViewAgendamentoRepository,
    val usuarioService: UsuarioService
) {

    fun visualizarPorNomeDaPeca(usuarioId: Int, nome: String): List<PedidoViewAgendamento>{
        usuarioService.existenceValidation(usuarioId)
        val listaAgendamento =
            repository.findByUsuarioIdAndPecanomeContainsIgnoreCaseAndAtivoTrue(usuarioId, nome)
        usuarioService.listValidation(listaAgendamento)
        return listaAgendamento
    }

    fun visualizarPorNomeDoTecido(usuarioId: Int, nome: String): List<PedidoViewAgendamento>{
        usuarioService.existenceValidation(usuarioId)
        val listaAgendamento =
            repository.findByUsuarioIdAndTecidonomeContainsIgnoreCaseAndAtivoTrue(usuarioId, nome)
        usuarioService.listValidation(listaAgendamento)
        return listaAgendamento
    }

}