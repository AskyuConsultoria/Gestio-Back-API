package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.AgendamentoViewTotalEtapa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AgendamentoViewTotalEtapaRepository: JpaRepository<AgendamentoViewTotalEtapa, Int> {
   fun findByUsuarioIdAndAtivoTrue(usuarioId: Int): List<AgendamentoViewTotalEtapa>
}