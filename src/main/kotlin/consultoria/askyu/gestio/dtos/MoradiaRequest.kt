package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.Usuario
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import kotlin.math.max
import kotlin.math.min

data class MoradiaRequest(
    var id:Int?= null,

    var endereco: Endereco?= null,


    var usuario: Usuario?= null,

    var cliente: Cliente?= null,

    var numero:Int?= null,
    var complemento: String?= null,
    var ativo:Boolean?=null
)


