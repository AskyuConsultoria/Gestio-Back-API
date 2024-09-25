package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Nota(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var titulo: String? = null,
    var corpo: String? = null,
    var cor: String? = null,
    var dataCriacao: LocalDateTime? = null,
    var dataAtualizacao: LocalDateTime? = null,
    @field: ManyToOne
    var usuario: Usuario? = null,
    @field: ManyToOne
    var cliente: Cliente? = null,
    var ativo: Boolean = true
) {
}