package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.NomeMedida
import org.springframework.data.jpa.repository.JpaRepository

interface NomeMedidaRepository: JpaRepository<NomeMedida, Int> {
}