package consultoria.askyu.gestio.dominio

import jakarta.persistence.*

@Entity
data class ValorMedida(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var valor: Float? = null,
    @field:ManyToOne
    var nomeMedida: NomeMedida? = null,
    @field:ManyToOne
    var itemPedido: ItemPedido? = null,
    @field:ManyToOne
    var cliente: Cliente? = null,
    @field:ManyToOne
    var usuario: Usuario? = null,
    @field:ManyToOne
    var peca: Peca? = null
) {
    constructor(
        paramValor: Float?,
        paramNomeMedida: NomeMedida?,
        paramItemPedido: ItemPedido?,
        paramCliente: Cliente?,
        paramUsuario: Usuario?,
        paramPeca: Peca?
    ): this(
        valor = paramValor,
        nomeMedida = paramNomeMedida,
        itemPedido = paramItemPedido,
        cliente = paramCliente,
        usuario = paramUsuario,
        peca = paramPeca
    )
    constructor(
        paramValor: Float?,
        paramNomeMedida: NomeMedida?
    ): this(valor = paramValor, nomeMedida = paramNomeMedida)
}