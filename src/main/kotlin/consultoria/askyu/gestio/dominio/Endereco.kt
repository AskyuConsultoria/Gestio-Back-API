package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import jakarta.validation.constraints.Size

@Entity
data class Endereco(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    var cep:String? = null,

    var logradouro:String? = null,

    var bairro:String? = null,

    var localidade:String? = null,

    @field:Size(min = 2, max = 2)
    var uf:String? = null,

    @field: ManyToOne
    var usuario: Usuario? = null,

    var ativo: Boolean = true,

    @field: ManyToOne
    var cliente: Cliente? = null
)

