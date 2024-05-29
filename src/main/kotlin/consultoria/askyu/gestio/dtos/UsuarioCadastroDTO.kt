package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank

class UsuarioCadastroDTO(

    @NotBlank
    var usuario:String,

    @NotBlank
    var senha:String,

    )