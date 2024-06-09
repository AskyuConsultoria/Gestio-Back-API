package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Etapa
import org.springframework.data.jpa.repository.JpaRepository

interface EtapaRepository: JpaRepository<Etapa, Int> {
    fun findByUsuarioId(usuarioId: Int): List<Etapa>
}