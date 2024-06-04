package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.Moradia
import consultoria.askyu.gestio.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MoradiaRepository: JpaRepository<Moradia, Int> {
    fun findByUsuarioIdAndClienteId(idUsuario: Int,idCliente: Int): List<Moradia>

    fun findByUsuarioId(idUsuario: Int): List<Moradia>

    fun findByUsuarioIdAndEnderecoId(idUsuario: Int,idEndereco: Int): List<Moradia>

    fun existsByUsuarioIdAndId(idUsuario: Int, id: Int): Boolean

    fun findByUsuarioIdAndId(idUsuario:Int, id: Int): Optional<Moradia>

}