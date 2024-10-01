package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.PedidoGraficoView
import consultoria.askyu.gestio.interfaces.ViewServico
import consultoria.askyu.gestio.repository.PedidoViewRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PedidoViewService(
    val usuarioService: UsuarioService,
    val pedidoViewRepository: PedidoViewRepository
): ViewServico(pedidoViewRepository, usuarioService) {
     fun visualizar(usuarioId: Int, dataInicio: LocalDateTime): List<PedidoGraficoView> {
        usuarioService.existenceValidation(usuarioId)
        return pedidoViewRepository.findByUsuarioIdAndDataInicioGreaterThanEqual(usuarioId, dataInicio)
    }

}