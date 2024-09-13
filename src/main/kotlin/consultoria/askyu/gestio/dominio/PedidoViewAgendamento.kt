package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "vw_pedido_agendamento_peca")
class PedidoViewAgendamento(
    @field:Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    var dataInicio: LocalDate? = null,
    var dataFim: LocalDate? = null,
    var usuarioId: Int? = null,
    var ativo: Boolean? = null,
    var nome: String? = null,
    var sobrenome: String? = null,
    var pecanome: String? = null,
    var tecidonome: String? = null
) {
}