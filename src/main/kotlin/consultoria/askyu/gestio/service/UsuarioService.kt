package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Usuario
import consultoria.askyu.gestio.dtos.UsuarioCadastroDTO
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UsuarioService(
    val repository: UsuarioRepository,
    val mapper: ModelMapper = ModelMapper()
)  {
    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }

    fun existenceValidation(id:Int):Boolean{
        if(repository.existsById(id)) {
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "Usuario não encontrado!")
    }

    fun uniqueValidation(usuario: Usuario):Boolean{
        if(repository.countByUsuario(usuario.usuario!!) == 0){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(409), "Esse cadastro ja existe no sistema!")
    }

    fun cadastrar(usuario: UsuarioCadastroDTO):Usuario{
        val novoUsuario = mapper.map(usuario, Usuario::class.java)
        uniqueValidation(novoUsuario)
        novoUsuario.ativo = true
        return repository.save(novoUsuario)
    }

    fun desativarUsuario(id:Int):Usuario{
        existenceValidation(id)
        var usuario = repository.findById(id).get()
        usuario.ativo = false
        return repository.save(usuario)
    }

    fun logar(usuario:String, senha:String):Usuario{
        if(repository.countByUsuarioAndSenhaAndAtivoIsTrue(usuario, senha) == 1) {
            var user = repository.findByUsuarioAndSenha(usuario, senha)
            if (user.autenticado) {
                throw ResponseStatusException(
                    HttpStatusCode.valueOf(409),
                    "Esse usuario ja esta logado em algum outro lugar!"
                )
            }
            user.autenticado = true
            return repository.save(user)
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "Esse usuário não existe!")
    }

    fun deslogar(usuario: String):Usuario{
        var user = repository.findByUsuario(usuario)
        if(!user.autenticado){
            throw ResponseStatusException(HttpStatusCode.valueOf(400), "Esse usuario não está logado!")
        }
        user.autenticado = false
        return repository.save(user)
    }

    fun obterInfo(id:Int):Usuario{
        existenceValidation(id)
        return repository.findById(id).get()
    }

    fun findAll():List<Usuario>{
        val usuario = repository.findAll()
        listValidation(usuario)
        return usuario
    }

    fun findById(id:Int):Usuario{
        val usuario = repository.findById(id).get()
        return usuario
    }

}