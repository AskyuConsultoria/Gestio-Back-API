package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.TecidoGraficoView
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*

interface TecidoViewRepository: JpaRepository<TecidoGraficoView, UUID>, IRepositorio {
    fun findByUsuarioIdAndDataInicioGreaterThan(usuarioId: Int, dataInicio: LocalDateTime): List<TecidoGraficoView>
}