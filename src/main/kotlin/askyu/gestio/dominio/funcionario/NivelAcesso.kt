package askyu.gestio.dominio.funcionario

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class NivelAcesso(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idNivelAcesso:Int?,
    @field:NotBlank var nome: String?,
    @field:NotBlank var descricao: String?
)
