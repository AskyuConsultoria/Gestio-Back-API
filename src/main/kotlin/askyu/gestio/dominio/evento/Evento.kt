package askyu.gestio.dominio.evento

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class Evento(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var idEvento:Int?,
    @field:NotBlank var nome:String?,
    @field:NotBlank var dataInicio: LocalDateTime?,
    @field:NotBlank var dataFim: LocalDateTime?,
    @field:NotBlank var localizacao:String?,
    @field:NotBlank var descricao:String?,
    @field:NotBlank var ativo:Boolean?
) {
    constructor() : this(null, null, null,null, null, null, null)
}
