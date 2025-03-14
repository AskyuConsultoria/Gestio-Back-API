package consultoria.askyu.gestio.dominio

import jakarta.persistence.*
import org.hibernate.annotations.Immutable


@Entity
@Immutable
@Table(name = "vw_cliente")
data class ClienteView(
    var usuario_id: Int,
    @field:Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,
    var nome: String? = null,
    var sobrenome: String? = null,
    var email: String? = null,
)