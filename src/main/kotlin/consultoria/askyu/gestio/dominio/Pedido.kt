package consultoria.askyu.gestio.dominio

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
data class Pedido(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int,
    @ManyToOne
    var itemPedido: ItemPedido,
    @ManyToOne
    var agendamento: Agendamento,
    @ManyToOne
    var usuario:Usuario,
    @ManyToOne
    var etapa: Etapa,
    @ManyToOne
    var cliente: Cliente,
    var ativo:Boolean = true
)
