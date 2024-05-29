package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.dtos.ClienteResponse
import org.springframework.data.jpa.repository.JpaRepository

interface ClienteRepository: JpaRepository<Cliente, Int>{
    fun findAllByUsuarioId(usuarioId: Int): List<ClienteResponse>
}