package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class AgendamentoCadastroDTO(
    @Size(min = 1)
    var nome:String? = null,
    @NotBlank
    var dataInicio: LocalDateTime? = null,
    @NotBlank @Future
    var dataFim: LocalDateTime? = null,
    var descricao:String? = null,
    @NotNull
    var usuario: Int? = null,
    @NotNull
    var etapa: Int? = null,
    @NotNull
    var cliente: Int? = null,
    @NotNull
    var endereco: Int? = null
)
