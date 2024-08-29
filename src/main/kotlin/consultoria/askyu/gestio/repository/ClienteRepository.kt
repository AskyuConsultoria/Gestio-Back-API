package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository

interface ClienteRepository: JpaRepository<Cliente, Int>, IRepositorio{
    fun findByUsuarioId(usuarioId: Int): List<Cliente>
    fun findByUsuarioIdAndId(usuarioId: Int, clienteId: Int): Boolean
    fun existsByUsuarioIdAndId(usuarioId: Int, clienteId: Int): Boolean
    fun findByUsuarioIdAndNomeContainsIgnoreCase(usuarioId: Int, nome: String): List<Cliente>
}