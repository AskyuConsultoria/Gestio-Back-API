package consultoria.askyu.gestio.repository

import consultoria.askyu.gestio.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Int> {

    fun countByUsuario(usuario: String): Int

    fun countUsuarioAndSenhaAndAtivoIsTrue(usuario: String, senha: String): Int

    fun findByUsuarioAndSenha(usuario: String, senha: String): Usuario

    fun findByUsuario(usuario: String): Usuario
}