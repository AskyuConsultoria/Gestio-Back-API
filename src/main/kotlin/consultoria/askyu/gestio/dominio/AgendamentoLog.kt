package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "log_agendamento")
data class AgendamentoLog(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,
    @field:ManyToOne
    var agendamento: Agendamento? = null,
    var dataInicio: LocalDateTime? = null,
    var dataFim: LocalDateTime? = null,
    var localizacao: String? = null,
    var descricao:String? = null,
    @field:ManyToOne
    var usuario:Usuario? = null,
    @field:ManyToOne
    var etapa: Etapa? = null,
    @field:ManyToOne
    var cliente: Cliente? = null
)