package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Etapa
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository

interface EtapaRepository: JpaRepository<Etapa, Int>, IRepositorio {
    fun findByUsuarioIdAndAtivoTrue(usuarioId: Int): List<Etapa>

    fun findByUsuarioIdAndIdAndAtivoTrue(usuarioId: Int, etapaId: Int): Etapa

    fun existsByUsuarioIdAndId(usuarioId: Int, etapaId: Int): Boolean
}