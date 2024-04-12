package askyu.gestio.repository

import askyu.gestio.dominio.evento.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag, Int> {

    fun findByNome(nome: String): List<Tag>
}