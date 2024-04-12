package askyu.gestio.dominio.evento

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class Tag(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idTag:Int?,
    @field:NotBlank var nome:String?,
    var cor:String?,
    @field:NotBlank @ManyToOne var fkTipo: Int?,
)