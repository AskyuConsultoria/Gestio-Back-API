package consultoria.askyu.gestio.dominio

import jakarta.persistence.*

@Entity
data class Endereco(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    var cep:String? = null,

    var logradouro:String? = null,

    var bairro:String? = null,
 
    var localidade: String? = null,

    var uf:String? = null,

    @field: ManyToOne
    var usuario: Usuario? = null,

    var ativo: Boolean = true,

    @field: ManyToOne
    var cliente: Cliente? = null,

    var cidade: String? = null,

    var numero: String? = null
)

