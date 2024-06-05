package consultoria.askyu.gestio.dominio

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
data class Endereco(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int,

    var cep:String,

    var logradouro:String,

    var bairro:String,

    @field:ManyToOne
    var usuario: Usuario,

    var localidade:String,

    @field:Size(min = 2, max = 2)
    var uf:String
)

