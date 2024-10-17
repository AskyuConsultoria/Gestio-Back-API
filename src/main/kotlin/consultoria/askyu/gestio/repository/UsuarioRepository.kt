package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.interfaces.IRepositorio
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Int>, IRepositorio {

    fun countByUsuario(usuario: String): Int

    fun countByUsuarioAndSenhaAndAtivoIsTrue(usuario: String, senha: String): Int

    fun findByUsuarioAndSenha(usuario: String, senha: String): Usuario

    fun findByUsuario(usuario: String): Usuario

}