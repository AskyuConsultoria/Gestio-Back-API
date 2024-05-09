package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Funcionario
import consultoria.askyu.gestio.dtos.FuncionarioDTO
import consultoria.askyu.gestio.repository.EmpresaRepository
import consultoria.askyu.gestio.repository.FuncionarioRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class FuncionarioService(
    val repository: FuncionarioRepository,
    val empresaRepository: EmpresaRepository,
    val mapper: ModelMapper = ModelMapper()
)  {
    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }

    fun existenceValidation(id:Int){
        if(!repository.existsById(id)) {
            throw ResponseStatusException(HttpStatusCode.valueOf(404), "Funcionario não encontrado!")
        }
    }

    fun validadeUnico(funcionario: Funcionario){
        if(repository.countByUsuarioAndEmpresaId(funcionario.usuario, funcionario.empresa.id) >= 1){
            throw ResponseStatusException(HttpStatusCode.valueOf(409), "Esse cadastro ja existe no sistema!")
        }
    }

    fun cadastrar(funcionario: FuncionarioDTO):Funcionario{
        var empresa = empresaRepository.findById(funcionario.empresa_id).get()
        var func = mapper.map(funcionario, Funcionario::class.java)
        func.empresa = empresa
        validadeUnico(func)
        return repository.save(func)
    }

    fun desativarFuncionario(id:Int){
        existenceValidation(id)
        var func = repository.findById(id).get()
        func.ativo = false
        repository.save(func)
    }

    fun logar(usuario:String, senha:String):Funcionario{
        var user = repository.findByUsuarioAndSenha(usuario, senha)
        if(user.autenticado){
            throw ResponseStatusException(HttpStatusCode.valueOf(400), "Esse funcionario ja esta logado em algum outro lugar!")
        }
        user.autenticado = true
        return repository.save(user)
    }

    fun deslogar(usuario: String):Funcionario{
        var user = repository.findByUsuario(usuario)
        if(!user.autenticado){
            throw ResponseStatusException(HttpStatusCode.valueOf(400), "Esse funcionario não está logado!")
        }
        user.autenticado = false
        return repository.save(user)
    }

    fun obterInfo(id:Int):Funcionario{
        return repository.findById(id).get()
    }

    fun findAll():List<Funcionario>{
        val func = repository.findAll()
        listValidation(func)
        return func
    }

}