package consultoria.askyu.gestio.dominio

import jakarta.persistence.*

@Entity
data class Funcionario(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int,

    var usuario:String,

    var senha:String,

    var nivel_acesso_id:Int = 0,

    @field:ManyToOne
    var empresa: Empresa,

    var autenticado:Boolean = false,

    var ativo:Boolean = true,

) {
}