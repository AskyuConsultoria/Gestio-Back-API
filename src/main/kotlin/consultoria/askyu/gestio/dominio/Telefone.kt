package consultoria.askyu.gestio.dominio

import jakarta.persistence.*

@Entity
class Telefone (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    var numero: String? = null,

    @field:ManyToOne
    var cliente: Cliente? = null,

    @field:ManyToOne
    var usuario: Usuario? = null,

    @field:ManyToOne
    var tipoTelefone: TipoTelefone? = null,

    var ativo: Boolean = true
) {
    constructor(
        paramNumero: String?,
        paramCliente: Cliente?,
        paramUsuario: Usuario?,
        paramTipoTelefone: TipoTelefone
    ): this(
        numero = paramNumero,
        cliente = paramCliente,
        usuario = paramUsuario,
        tipoTelefone = paramTipoTelefone
    )
}