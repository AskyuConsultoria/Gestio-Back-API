package consultoria.askyu.gestio.dominio

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

data class ItemPedido(
    @field: Id
    @field: GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var observacao: String? = null,
    var cliente: Cliente? = null,
    var usuario: Usuario? = null,
    var peca: Peca? = null,
    var ativo: Boolean? = true
) {
   constructor(
       paramObservacao: String,
       paramCliente: Cliente,
       paramUsuario: Usuario,
       paramPeca: Peca,
   ): this(
       observacao = paramObservacao,
       cliente = paramCliente,
       usuario = paramUsuario,
       peca = paramPeca
   )
}