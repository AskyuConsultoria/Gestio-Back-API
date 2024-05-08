package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.Empresa
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

class FuncionarioDTO(

    @NotBlank
    var usuario:String,

    @NotBlank
    var senha:String,

    @NotBlank
    var empresa_id: Int,

    ) {
}