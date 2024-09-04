package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Telefone
import org.springframework.data.jpa.repository.JpaRepository

interface TelefoneRepository : JpaRepository<Telefone, Int> {

    fun findByUsuarioIdAndClienteIdAndAtivoTrue(usuarioId: Int, clienteId: Int): List<Telefone>

    fun findByNumero(numero: String): List<Telefone>

    fun findByUsuarioId(usuarioId:Int): List<Telefone>?

    fun existsByUsuarioIdAndId(usuarioId: Int, tipoTelefoneId: Int): Boolean
   fun findByUsuarioIdAndIdAndAtivoTrue(usuarioId: Int, tipoTelefoneId: Int): Telefone

   fun existsByUsuarioIdAndAndId(usuarioId:Int, telefoneId: Int): Boolean
}