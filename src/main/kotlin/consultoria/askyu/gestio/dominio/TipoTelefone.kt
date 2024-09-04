package consultoria.askyu.gestio.dominio

import jakarta.persistence.*

@Entity
class TipoTelefone (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int,

    var nome:String,

    var digitos:Int,

    @field:ManyToOne
    var usuario: Usuario? = null

) {
}