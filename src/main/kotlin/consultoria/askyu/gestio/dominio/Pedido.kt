package consultoria.askyu.gestio.dominio

import jakarta.persistence.*

@Entity
data class Pedido(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,
    @ManyToOne
    var itemPedido: ItemPedido? = null,
    @ManyToOne
    var agendamento: Agendamento? = null,
    @ManyToOne
    var usuario:Usuario? = null,
    @ManyToOne
    var etapa: Etapa? = null,
    @ManyToOne
    var cliente: Cliente? = null,
    var ativo:Boolean = true
)
