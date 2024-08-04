package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class EtapaCadastroDTO(
    @NotBlank @field:Size(min = 1)
    var nome:String? = null,
    @NotBlank
    var descricao:String? = null,
    @NotBlank
    var ultimaEtapa: Boolean? = null,
    @NotBlank
    var usuario:Int? = null
)
