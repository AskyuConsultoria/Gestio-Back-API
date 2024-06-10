package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class AgendamentoCadastroDTO(
    @NotBlank @Size(min = 1)
    var nome:String? = null,
    @NotBlank
    var dataInicio: LocalDateTime? = null,
    @NotBlank @Future
    var dataFim: LocalDateTime? = null,
    @NotBlank
    var descricao:String? = null,
    @NotBlank
    var usuario: Int? = null,
    @NotBlank
    var etapa: Int? = null,
    @NotBlank
    var cliente: Int? = null,
)
