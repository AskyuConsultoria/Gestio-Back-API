package consultoria.askyu.gestio.dominio

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

class TipoTelefone (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int,

    var nome:String,

    var digitos:Int,

    @field:ManyToOne
    var usuario: Usuario? = null,

    var ativo:Boolean = true




) {
}