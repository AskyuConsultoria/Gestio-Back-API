package consultoria.askyu.gestio.dominio

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.util.Date


@Entity
data class Cliente(

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int,

    var nome:String,

    var sobrenome:String,

    var dtNasc: LocalDate,

    var email:String,

    @field:ManyToOne
    var responsavel: Cliente?,

    @ManyToOne
    var usuario:Usuario?,

    var ativo: Boolean = true
)