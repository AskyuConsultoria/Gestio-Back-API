package consultoria.askyu.gestio.dominio

import jakarta.persistence.*

@Entity
class TipoTelefone (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    var nome:String? = null,

    var digitos:Int? = null,

    @field:ManyToOne
    var usuario: Usuario? = null

) {
    constructor(
        paramNome: String?,
        paramDigitos: Int?,
        paramUsuario: Usuario?
    ): this(nome = paramNome, digitos = paramDigitos, usuario = paramUsuario)
}