package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CepCadastroRequest(
    @NotBlank(message = "O CEP deve ser informado")
    @Size(min = 8, max = 8, message = "Formatação do CEP inválida")
    var cep:String,
)
