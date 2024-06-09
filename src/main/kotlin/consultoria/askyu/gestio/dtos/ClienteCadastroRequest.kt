package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.Usuario
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

class ClienteCadastroRequest(
    @field:NotBlank
    var nome: String? = null,
    @field:NotBlank
    var sobrenome: String? = null,
    @field:NotBlank
    var dt_nasc: LocalDate? = null,
    var email: String? = null,
    var usuario: Usuario? = null,
) {

}