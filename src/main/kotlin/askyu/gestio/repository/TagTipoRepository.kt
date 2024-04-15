package askyu.gestio.repository

import askyu.gestio.dominio.evento.TipoTag
import org.springframework.data.jpa.repository.JpaRepository

interface TagTipoRepository: JpaRepository<TipoTag, Int> {

}