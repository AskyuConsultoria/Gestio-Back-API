package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.Email

data class EmailDTO(
    @field:Email(message = "Email inválido")
    val email: String
)