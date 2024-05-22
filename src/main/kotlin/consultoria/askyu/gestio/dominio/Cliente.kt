package consultoria.askyu.gestio.dominio

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotBlank
import java.util.Date


@Entity
data class Cliente(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?,
    @field:NotBlank
    var nome:String?,
    @field:NotBlank
    var sobrenome:String?,
    @field:NotBlank
    var dtNasc:Date?,
    @field:NotBlank
    var email:String?,
    @field:ManyToOne
    var responsavelId: Cliente?,
    var usuarioId:Int?,
    var ativo: Boolean? = true
)