package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank

class UsuarioDTO(

    @NotBlank
    var usuario:String,

    @NotBlank
    var senha:String,

    )