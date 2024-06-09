package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class EtapaCadastroDTO(
    @NotBlank @field:Size(min = 1)
    var nome:String,
    @NotBlank
    var descricao:String,
    @NotBlank
    var ultimaEtapa: Boolean,
    @NotBlank
    var usuario:Int
)
