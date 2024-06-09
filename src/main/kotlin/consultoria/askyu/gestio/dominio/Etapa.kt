package consultoria.askyu.gestio.dominio

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
data class Etapa(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int,
    var nome:String,
    var descricao:String,
    var ultimaEtapa:Boolean,
    @ManyToOne
    var usuario:Usuario,
    var ativo:Boolean = true
)
