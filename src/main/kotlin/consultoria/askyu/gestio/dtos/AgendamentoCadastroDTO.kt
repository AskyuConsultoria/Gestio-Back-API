package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class AgendamentoCadastroDTO(
    @NotBlank @Size(min = 1)
    var nome:String,
    @NotBlank
    var dataInicio: LocalDateTime,
    @NotBlank @Future
    var dataFim: LocalDateTime,
    @NotBlank
    var descricao:String,
    @NotBlank
    var usuario: Int,
    @NotBlank
    var etapa: Int,
    @NotBlank
    var cliente: Int,
)
