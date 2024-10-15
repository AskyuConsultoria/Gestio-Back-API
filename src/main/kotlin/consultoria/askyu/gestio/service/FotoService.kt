package consultoria.askyu.gestio.service

import consultoria.askyu.gestio.dominio.Agendamento
import consultoria.askyu.gestio.dominio.Etapa
import consultoria.askyu.gestio.dominio.Foto
import consultoria.askyu.gestio.dominio.ItemPedido
import consultoria.askyu.gestio.dtos.FotoCadastroDTO
import consultoria.askyu.gestio.interfaces.Servico
import consultoria.askyu.gestio.repository.ClienteRepository
import consultoria.askyu.gestio.repository.FotoRepository
import consultoria.askyu.gestio.repository.ItemPedidoRepository
import consultoria.askyu.gestio.repository.UsuarioRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException

class FotoService(
    val mapper: ModelMapper = ModelMapper(),
    val repository: FotoRepository,
    val itemPedidoRepository: ItemPedidoRepository,
    val usuarioRepository: UsuarioRepository
): Servico(repository, mapper) {
    // validações
    fun listValidation(lista:List<*>){
        if(lista.isEmpty()){
            throw ResponseStatusException(HttpStatusCode.valueOf(204), "O resultado da busca foi uma lista vazia")
        }
    }

    fun validateExistence(fotoId : Int){
        if(!repository.existsById(fotoId)){
            throw ResponseStatusException(
                HttpStatusCode.valueOf(404), "Foto não foi encontrada!"
            )
        }
    }

    fun idUsuarioValidation(id:Int): Boolean{
        if(usuarioRepository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404), "O usuario não existe.")
    }

    fun idItemPedidoValidation(id:Int):Boolean{
        if(itemPedidoRepository.existsById(id)){
            return true
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(404),"A ficha não existe")
    }



    // Serviços
    fun cadastrar(novaFoto:FotoCadastroDTO): Foto {

        idUsuarioValidation(novaFoto.usuario!!)
        idItemPedidoValidation(novaFoto.usuario!!)


        val foto = mapper.map(novaFoto, Foto::class.java)

        foto.usuario = usuarioRepository.findById(novaFoto.usuario!!).get()
        foto.itemPedido = itemPedidoRepository.findById(novaFoto.itemPedido!!).get()
        return repository.save(foto)

    }

    fun buscarUm(usuarioId: Int, fotoId:Int): Foto{
        idUsuarioValidation(usuarioId)
        validateExistence(fotoId)

        val fotoBuscada = repository.findByUsuarioIdAndId(usuarioId,fotoId)
        val foto = mapper.map(fotoBuscada,Foto::class.java)

        return foto
    }
    fun buscar(usuarioId: Int): List<Foto>{
        idUsuarioValidation(usuarioId)


        val listaFoto = repository.findByUsuarioId(usuarioId)
        val listaDto = mutableListOf<Foto>()

        listValidation(listaFoto)

        listaFoto.map {
            listaDto+= mapper.map(it, Foto::class.java)
        }
        // pra que serve esse dto? qual a função dele?
        return listaDto
    }

    fun atualizar(usuarioId:Int,fotoId:Int, fotoAtualizada: Foto): Foto {
        idUsuarioValidation(usuarioId)
        validateExistence(fotoId)

        fotoAtualizada.usuario!!.id = fotoAtualizada.id
        fotoAtualizada.id = fotoId
        return repository.save(fotoAtualizada)
    }

    fun excluir(usuarioId: Int, fotoId: Int) {
        idUsuarioValidation(usuarioId)
        validateExistence(fotoId)

        val etapa = repository.deleteByUsuarioIdAndId(usuarioId, fotoId)
    }


}