package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.ValorMedida
import org.springframework.data.jpa.repository.JpaRepository

interface ValorMedidaRepository: JpaRepository<ValorMedida, Int> {
}