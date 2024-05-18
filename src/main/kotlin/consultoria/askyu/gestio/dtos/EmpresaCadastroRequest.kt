package consultoria.askyu.gestio.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.br.CNPJ

data class EmpresaCadastroRequest(
    @NotBlank(message = "O CNPJ não pode ser nulo ou vazio")
    @field:CNPJ
    var cnpj:String, //modelo de CNPJ valido para testar: 00.000.000/0001-91

    @NotBlank(message = "O nome da empresa não pode ser nulo ou vazio")
    var nomeEmpresa:String,

    @NotBlank(message = "O nome fantasia não pode ser nulo ou vazio")
    var nomeFantasia:String,

    @NotBlank(message = "O telefone de contato não pode ser nulo ou vazio e deve ser valido")
    var telefone:Int,

    @NotBlank(message = "O Email não pode ser nulo ou vazio e deve ser valido")
    @field:Email
    var email:String,
)
