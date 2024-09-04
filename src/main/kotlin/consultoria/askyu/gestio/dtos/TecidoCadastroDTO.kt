package consultoria.askyu.gestio.dtos


import jakarta.validation.constraints.NotBlank

data class TecidoCadastroRequest(
    @field:NotBlank
    var id: Int,
    @field:NotBlank
    var nome:String,
    @field:NotBlank
    var usuario: Int
)