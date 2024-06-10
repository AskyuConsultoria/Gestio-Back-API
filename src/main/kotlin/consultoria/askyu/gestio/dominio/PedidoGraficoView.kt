package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import java.util.*


//@Entity
//@Immutable
//@Table(name = "vw_rank_cliente")
//class PedidoGraficoView(
//    @EmbeddedId
//    val id: PedidoGraficoViewId? = null,
//
//
//) {
//
//}

@Entity
@Table(name = "vw_rank_cliente")
class PedidoGraficoView(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var nome: String,
    var qtdPedidos: Int,
    var usuarioId: Int
)