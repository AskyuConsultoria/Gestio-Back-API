package consultoria.askyu.gestio

import consultoria.askyu.gestio.dominio.Usuario
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
data class Tecido(
    @field:Id
    var id:Int? = null,
    var nome:String? = null,
    @ManyToOne
    val usuario: Usuario? = null,
    var ativo: Boolean? = true
){
    constructor(
        paramId: Int,
        paramNome: String,
        paramUsuario: Usuario,
    ): this(id = paramId, nome = paramNome, usuario = paramUsuario)
}