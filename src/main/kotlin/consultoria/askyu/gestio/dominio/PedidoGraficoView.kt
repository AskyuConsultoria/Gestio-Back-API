package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "vw_rank_cliente")
class PedidoGraficoView(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var nome: String,
    var sobrenome: String,
    var qtdPedidos: Int,
    var usuarioId: Int,
    var dataInicio: Int

)