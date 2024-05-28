package consultoria.askyu.gestio.dominio

import consultoria.askyu.gestio.Tecido
import jakarta.persistence.*

@Entity
data class ColecaoTecido(
    @field:Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @field:ManyToOne
    var tecido: Tecido? = null,
    @field:ManyToOne
    var itemPedido: ItemPedido? = null,
    @field:ManyToOne
    var cliente: Cliente? = null,
    @field:ManyToOne
    var usuario: Usuario? = null,
    @field:ManyToOne
    var peca: Peca? = null,
    var ativo: Boolean? = true
) {
    constructor(
        paramTecido: Tecido,
        paramItemPedido: ItemPedido,
        paramCliente: Cliente,
        paramUsuario: Usuario,
        paramPeca: Peca
    ): this(
        tecido = paramTecido,
        itemPedido = paramItemPedido,
        cliente = paramCliente,
        usuario = paramUsuario,
        peca = paramPeca
    )
}