package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class Agendamento(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int,
    var nome:String,
    var dataInicio: LocalDateTime,
    var dataFim: LocalDateTime,
    var descricao:String,
    @ManyToOne
    var usuario:Usuario,
    @ManyToOne
    var etapa: Etapa,
    @ManyToOne
    var cliente: Cliente,
    var ativo:Boolean = true
)
