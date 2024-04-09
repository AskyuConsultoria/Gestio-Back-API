package askyu.gestio.dominio.funcionario

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

@Entity
data class TipoAcesso(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idTipoAcesso:Int?,
    @field:NotBlank var nome: String?,
    @field:NotBlank var descricao: String?
)
