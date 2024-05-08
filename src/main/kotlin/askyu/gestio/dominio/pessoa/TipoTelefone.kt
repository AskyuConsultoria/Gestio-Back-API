package askyu.gestio.dominio.pessoa

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

@Entity
data class TipoTelefone(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?,
    @field:NotBlank var nome:String?,
    @field:NotBlank var digitos:Int?

)
