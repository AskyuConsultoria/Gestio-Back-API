package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.Email

data class EmailDTO(
    @field:Email(message = "Email inválido")
    var email: String
)