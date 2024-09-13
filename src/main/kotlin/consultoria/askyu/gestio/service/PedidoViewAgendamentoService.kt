package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.PedidoViewAgendamentoPeca
import consultoria.askyu.gestio.repository.PedidoViewAgendamentoPecaRepository
import org.springframework.stereotype.Service

@Service
class PedidoViewAgendamentoPecaService(
    val repository: PedidoViewAgendamentoPecaRepository,
    val usuarioService: UsuarioService
) {

    fun visualizarPorNomeDaPeca(usuarioId: Int, nome: String): List<PedidoViewAgendamentoPeca>{
        usuarioService.existenceValidation(usuarioId)
        val listaAgendamento =
            repository.findByUsuarioIdAndPecanomeContainsIgnoreCaseAndAtivoTrue(usuarioId, nome)
        usuarioService.listValidation(listaAgendamento)
        return listaAgendamento
    }

    fun visualizarPorNomeDoTecido(usuarioId: Int, nome: String): List<PedidoViewAgendamentoPeca>{
        usuarioService.existenceValidation(usuarioId)
        val listaAgendamento =
            repository.findByUsuarioIdAndTecidonomeContainsIgnoreCaseAndAtivoTrue(usuarioId, nome)
        usuarioService.listValidation(listaAgendamento)
        return listaAgendamento
    }

}