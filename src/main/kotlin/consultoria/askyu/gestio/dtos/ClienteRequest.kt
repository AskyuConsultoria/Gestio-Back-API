package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.Usuario
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import jdk.jfr.BooleanFlag
import java.util.*

data class ClienteRequest(
    @field: NotBlank
    @field: Email
    var email: String,

    @NotBlank
    var nome:String,

    @NotBlank
    var sobrenome:String,

    @field: Past
    var dtNasc: Date,

    @field: BooleanFlag
    var ativo: Boolean = true,

    var usuario: Usuario,

    )
