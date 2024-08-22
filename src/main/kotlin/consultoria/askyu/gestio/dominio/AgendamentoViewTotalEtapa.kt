package consultoria.askyu.gestio.dominio

import jakarta.persistence.*


@Entity
@Table(name = "vw_total_etapa")
class AgendamentoViewTotalEtapa(
    var qtd_agendamento: Int? = null,
    @field:Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var etapaId: Int? = null,
    var usuarioId: Int? = null,
    var ativo: Boolean? = null
) {
}