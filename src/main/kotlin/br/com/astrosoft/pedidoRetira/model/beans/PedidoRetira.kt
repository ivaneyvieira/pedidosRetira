package br.com.astrosoft.pedidoRetira.model.beans

import br.com.astrosoft.AppConfig
import br.com.astrosoft.pedidoRetira.model.saci
import java.time.LocalDate
import java.time.LocalTime

data class PedidoRetira(val loja: Int,
                        val pedido: Int,
                        val dataPedido: LocalDate,
                        val nfno: String,
                        val nfse: String,
                        val dataNota: LocalDate,
                        val horaNota: LocalTime,
                        val valor: Double,
                        val usuarioV: Int,
                        val usuarioS: Int,
                        val usuarioE: Int,
                        val vendedor: Int,
                        val obs: String) {
  val notaFiscal: String
    get() = numeroNota(nfno, nfse)
  
  private fun numeroNota(nfno: String, nfse: String): String {
    return when {
      nfno == "" -> ""
      nfse == "" -> nfno
      else       -> "$nfno/$nfse"
    }
  }
  
  fun marcaUserVenda(user: Int) {
    saci.marcaUserVenda(loja, pedido, user)
  }
  
  fun marcaUserSepara(user: Int) {
    saci.marcaUserSepara(loja, pedido, user)
  }
  
  fun marcaUserEntregue(user: Int) {
    saci.marcaUserEntregue(loja, pedido, user)
  }
  
  fun filtroNota(notaFiltro: String) = notaFiscal.startsWith(notaFiltro) || notaFiltro.trim() == ""
  
  fun filtroVendedor(vendedorFiltro: Int) =vendedorFiltro == vendedor || vendedorFiltro == 0
  
  fun fintroUsuarioV(usuarioFiltro : Int) = usuarioFiltro == usuarioV || usuarioFiltro == 0
  
  fun fintroUsuarioS(usuarioFiltro : Int) = usuarioFiltro == usuarioS || usuarioFiltro == 0
  
  fun fintroUsuarioE(usuarioFiltro : Int) = usuarioFiltro == usuarioE || usuarioFiltro == 0
  
  val isVenda
    get() = usuarioE == 0 && usuarioS == 0 && usuarioV == 0
  val isSepara
    get() = usuarioE == 0 && usuarioS == 0 && usuarioV != 0
  val isEntregue
    get() = usuarioE == 0 && usuarioS != 0
  val isEditor
    get() = usuarioE != 0
  
  companion object {
    private val storeno: Int by lazy {
      UserSaci.findUser(AppConfig.userSaci?.login)?.storeno ?: 0
    }
    
    @Synchronized
    fun updateList(): List<PedidoRetira> {
      return saci.listaPedidoRetira(storeno);
    }
    
    fun listaVenda(): List<PedidoRetira> {
      return updateList().filter {it.isVenda}
    }
    
    fun listaSepara(): List<PedidoRetira> {
      return updateList().filter {it.isSepara}
    }
    
    fun listaEntregue(): List<PedidoRetira> {
      return updateList().filter {it.isEntregue}
    }
    
    fun listaEditor(): List<PedidoRetira> {
      return updateList().filter {
        it.isEditor
      }
    }
  }
}
