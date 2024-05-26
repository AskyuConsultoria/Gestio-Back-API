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

    var localidade:String,

    var uf:String,

    @ManyToOne
    var usuario: Usuario?=  null
)

