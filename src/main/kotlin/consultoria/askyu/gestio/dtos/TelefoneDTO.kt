package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.Size

data class TelefoneDTO(
    @field:Size(min = 11, max = 11, message = "Telefone esta com o numero errado de caracteres numericos(Remova traços, barras e parenteses por favor")
    val telefone: Int
)
