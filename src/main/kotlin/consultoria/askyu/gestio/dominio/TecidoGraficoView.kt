package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "vw_rank_tecido")
data class TecidoGraficoView(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    var nome: String? = null,
    var qtdTecidos: Int? = null,
    var usuarioId: Int? = null,
    var dataInicio: Int? = null
) {

}