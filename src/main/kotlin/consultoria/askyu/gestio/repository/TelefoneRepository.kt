package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Telefone
import org.springframework.data.jpa.repository.JpaRepository

interface TelefoneRepository : JpaRepository<Telefone, Int> {

    fun findByUsuarioIdAndNumeroContainsIgnoreCase(usuarioId: Int, numero: String): List<Telefone>

    fun findByUsuarioId(usuarioId:Int): List<Telefone>

    fun existsByUsuarioIdAndId(usuarioId: Int, tipoTelefoneId: Int): Boolean
    fun findByUsuarioIdAndId(usuarioId: Int, tipoTelefoneId: Int): Telefone

    fun existsByUsuarioIdAndClienteIdAndTipoTelefoneIdAndId(usuarioId:Int, clienteId: Int, tipoTelefoneId: Int, telefoneId: Int): Boolean
}