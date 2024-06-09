package consultoria.askyu.gestio.dominio

import jakarta.persistence.*

@Entity
data class ItemPedido(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var observacao: String? = null,
    @field:ManyToOne
    var cliente: Cliente? = null,

    @field:ManyToOne
    var usuario: Usuario? = null,

    @field:ManyToOne
    var peca: Peca? = null,

    var ativo: Boolean? = true
) {
   constructor(
       paramObservacao: String?,
       paramCliente: Cliente?,
       paramUsuario: Usuario?,
       paramPeca: Peca?,
   ): this(
       observacao = paramObservacao,
       cliente = paramCliente,
       usuario = paramUsuario,
       peca = paramPeca
   )
}