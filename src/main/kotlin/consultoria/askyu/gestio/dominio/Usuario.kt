package consultoria.askyu.gestio.dominio

import jakarta.persistence.*

@Entity
data class Usuario(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int,

    var usuario:String,

    var senha:String,

    var autenticado:Boolean = false,

    var ativo:Boolean = true,

) {
}