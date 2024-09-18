package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import java.time.LocalDate


@Entity
data class Cliente(

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    var nome:String? = null,

    var sobrenome:String? = null,

    var dtNasc: LocalDate? = null,

    var email:String? = null,

    @field:ManyToOne
    var responsavel: Cliente? = null,

    @ManyToOne
    var usuario:Usuario? = null,

    var ativo: Boolean = true

)