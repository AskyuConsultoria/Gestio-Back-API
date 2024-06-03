package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class TelefoneDTO(

    @field:Size(min = 11, max = 11, message = "Telefone esta com o numero errado de caracteres numericos(Remova traços, barras e parenteses por favor")
    val telefone: String,

    val id: Int? = null,
    @field:NotNull
    val tipoTelefoneId: Int,
    @field:NotNull
    val clienteId: Int,
    @field:NotBlank
    val numero: String,
    val ativo: Boolean
)
