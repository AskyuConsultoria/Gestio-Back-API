package askyu.gestio.repository

import askyu.gestio.dominio.evento.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface EventoRepository : JpaRepository<Tag, Int> {
}