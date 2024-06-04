package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Cliente
import org.springframework.data.jpa.repository.JpaRepository

interface ClienteRepository: JpaRepository<Cliente, Int>{
    fun existsByUsuarioIdAndId(idUsuario: Int, id: Int): Boolean
}