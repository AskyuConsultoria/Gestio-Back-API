package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Cliente
import consultoria.askyu.gestio.dominio.Endereco
import consultoria.askyu.gestio.dominio.Moradia
import consultoria.askyu.gestio.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MoradiaRepository: JpaRepository<Moradia, Int> {
    fun findMoradia():List<Moradia>

    fun findByIdByCliente(idCliente: Int): Optional<Moradia>

    fun findByIdByUsuario(idUuario: Int): Optional<Moradia>

    fun findByIdByEndereco(idEndereco: Int): Optional<Moradia>


}