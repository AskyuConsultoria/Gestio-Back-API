package consultoria.askyu.gestio

import consultoria.askyu.gestio.Tecido
import org.springframework.data.jpa.repository.JpaRepository

interface TecidoRepository: JpaRepository<Tecido, Int> {

    fun findByNome(nome: String): List<Tecido>

}