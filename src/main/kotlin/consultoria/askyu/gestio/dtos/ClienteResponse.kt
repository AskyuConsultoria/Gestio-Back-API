package consultoria.askyu.gestio.dtos

import consultoria.askyu.gestio.dominio.Cliente
import java.time.LocalDate
import java.util.*

data class ClienteResponse(

    var id:Int,

    var nome:String,

    var sobrenome:String,

    var dtNasc: LocalDate,

    var email:String,

    var responsavel: Cliente?,
    )
