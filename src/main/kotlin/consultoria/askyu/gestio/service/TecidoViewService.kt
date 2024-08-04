package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.TecidoGraficoView
import consultoria.askyu.gestio.repository.TecidoViewRepository
import org.springframework.stereotype.Service


@Service
class TecidoViewService(
    val tecidoViewRepository: TecidoViewRepository,
    val usuarioService: UsuarioService
) {

    fun visualizar(usuarioId: Int): List<TecidoGraficoView>{
        usuarioService.existenceValidation(usuarioId)
        return tecidoViewRepository.findByUsuarioId(usuarioId)
    }
}