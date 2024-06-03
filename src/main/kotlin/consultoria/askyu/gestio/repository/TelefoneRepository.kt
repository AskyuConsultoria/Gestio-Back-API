package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Telefone
import consultoria.askyu.gestio.dominio.TipoTelefone
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TelefoneRepository : JpaRepository<Telefone, Int> {

    fun findByNumero(numero: String): List<Telefone>

    fun findByUsuarioId(usuarioId:Int): List<Telefone>?

    fun existsByUsuarioIdAndId(usuarioId: Int, tipoTelefoneId: Int): Boolean
   fun findByUsuarioIdAndId(usuarioId: Int, tipoTelefoneId: Int): Telefone

   fun existsByUsuarioIdAndClienteIdAndTipoTelefoneIdAndId(usuarioId:Int, clienteId: Int, tipoTelefoneId: Int, telefoneId: Int): Boolean
}