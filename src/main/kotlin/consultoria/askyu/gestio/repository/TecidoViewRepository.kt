package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.TecidoGraficoView
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TecidoViewRepository: JpaRepository<TecidoGraficoView, UUID> {
    fun findByUsuarioId(usuarioId: Int): List<TecidoGraficoView>
}