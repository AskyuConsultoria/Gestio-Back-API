package consultoria.askyu.gestio.dtos

import java.time.LocalDate
import java.util.*

data class ResponsavelResponse(

    var id:Int,

    var nome:String,

    var sobrenome:String,

    var dtNasc: LocalDate,

    var email:String,

    )
