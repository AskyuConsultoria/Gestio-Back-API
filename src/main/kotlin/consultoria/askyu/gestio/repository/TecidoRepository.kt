package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Tecido
import org.springframework.data.jpa.repository.JpaRepository

interface TecidoRepository: JpaRepository<Tecido, Int> {

    fun findByNome(nome: String): List<Tecido>

}