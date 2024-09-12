package consultoria.askyu.gestio.dominio
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "vw_agendamento_cliente_dependente")
class AgendamentoViewClienteDependente(
    @field:Id @field: GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var dataInicio: LocalDate? = null,
    var dataFim: LocalDate? = null,
    var localizacao: String? = null,
    var descricao: String? = null,
    var usuarioId: Int? = null,
    var etapaId: Int? = null,
    var enderecoId: Int? = null,
    var telefoneId: Int? = null,
    var ativo: Boolean? = null,
    var nome: String? = null,
    var sobrenome: String? = null,
    var email: String? = null,
    var responsavelId: Int? = null,
    var clienteativo: Boolean? = null
) {
}