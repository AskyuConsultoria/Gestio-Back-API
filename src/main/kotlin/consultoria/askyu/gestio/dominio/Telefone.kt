package consultoria.askyu.gestio.dominio

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

class Telefone (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int? = null,

    @field:ManyToOne
    var usuario: Usuario? = null,

    @field:ManyToOne
    var tipoTelefone: TipoTelefone? = null,

    @field:ManyToOne
    var cliente: Cliente? = null,

    var numero: String? = null,

    var ativo: Boolean = true
) {
}