package consultoria.askyu.gestio.dominio

import jakarta.persistence.*

@Entity
data class Etapa(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,
    var nome:String? = null,
    var descricao:String? = null,
    var ultimaEtapa:Boolean? = null,
    @ManyToOne
    var usuario:Usuario? = null,
    var ativo:Boolean = true
)
