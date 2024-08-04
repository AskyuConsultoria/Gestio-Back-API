package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.PedidoGraficoView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PedidoViewRepository: JpaRepository<PedidoGraficoView, UUID> {
   fun findByUsuarioId(usuarioId: Int): List<PedidoGraficoView>
}
