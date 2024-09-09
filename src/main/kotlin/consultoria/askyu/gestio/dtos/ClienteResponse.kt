package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.Cliente
import java.time.LocalDate

data class ClienteResponse(

    var id:Int? = null,

    var nome:String? = null,

    var sobrenome:String? = null,

    var dtNasc: LocalDate? = null,

    var email:String? = null,

    var responsavel: Cliente? = null,
    )
