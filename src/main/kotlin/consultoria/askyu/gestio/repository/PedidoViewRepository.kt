package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.PedidoGraficoView
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PedidoViewRepository: JpaRepository<PedidoGraficoView, UUID>, IRepositorio {
   fun findByUsuarioId(usuarioId: Int): List<PedidoGraficoView>
}
