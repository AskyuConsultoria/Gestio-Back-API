package askyu.gestio.dominio.pessoa

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
data class Endereco(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idEndereco:Int?,
    @field:NotBlank var CEP:String?,
    @field:NotBlank var logradouro:String?,
    @field:NotBlank var bairro:String?,
    @field:NotBlank var localidade:String?,
    @field:NotBlank @field:Size(min = 2, max = 2) var uf:String?
)
