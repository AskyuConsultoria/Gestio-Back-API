package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.PedidoGraficoView
import consultoria.askyu.gestio.repository.PedidoViewRepository
import org.springframework.stereotype.Service

@Service
class PedidoViewService(
    val usuarioService: UsuarioService,
    val pedidoViewRepository: PedidoViewRepository
) {
    fun visualizar(usuarioId: Int): List<PedidoGraficoView> {
        usuarioService.existenceValidation(usuarioId)
        return pedidoViewRepository.findByUsuarioId(usuarioId)
    }
}