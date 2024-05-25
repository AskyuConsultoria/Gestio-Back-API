package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Cliente
import org.springframework.data.jpa.repository.JpaRepository

interface ClienteRepository:JpaRepository<Cliente, Int> {

    fun findByUsuarioIdAndId(usuarioId: Int, clienteId: Int): Boolean
    fun existsByUsuarioIdAndId(usuarioId: Int, clienteId: Int): Boolean
}