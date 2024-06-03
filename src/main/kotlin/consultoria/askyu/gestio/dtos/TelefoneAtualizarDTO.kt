package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank

class TelefoneAtualizarDTO (

    @field:NotBlank
    val id: Int,

    val tipoTelefoneId: Int,

    val clienteId: Int,

    val numero: String,

    val ativo: Boolean
) {
}