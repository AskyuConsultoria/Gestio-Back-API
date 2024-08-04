package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Agendamento(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,
    var nome:String? = null,
    var dataInicio: LocalDateTime? = null,
    var dataFim: LocalDateTime? = null,
    var descricao:String? = null,
    @ManyToOne
    var usuario:Usuario? = null,
    @ManyToOne
    var etapa: Etapa? = null,
    @ManyToOne
    var cliente: Cliente? = null,
    var ativo:Boolean = true
)
