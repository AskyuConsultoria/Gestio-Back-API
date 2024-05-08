package askyu.gestio.repository

import askyu.gestio.dominio.ficha.Tecido
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TecidoRepository: JpaRepository<Tecido, Int> {

    fun findByNome(nome: String): List<Tecido>

}