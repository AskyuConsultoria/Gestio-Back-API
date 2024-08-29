package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.ClienteView
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClienteViewRepository: JpaRepository<ClienteView, Int>, IRepositorio