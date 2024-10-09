package consultoria.askyu.gestio.repository


import consultoria.askyu.gestio.dominio.Foto
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository

interface FotoRepository: JpaRepository<Foto, Int>, IRepositorio {
    fun findByUsuarioId(usuarioId: Int): List<Foto>
}