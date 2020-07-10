package br.com.astrosoft.pedidoRetira.viewmodel

import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel
import br.com.astrosoft.pedidoRetira.model.beans.PedidoRetira

class PedidoRetiraViewModel(view: IPedidoRetiraView): ViewModel<IPedidoRetiraView>(view) {
  fun updateGridVenda() {
    view.updateGridVenda(listVenda())
  }
  
  private fun listVenda(): List<PedidoRetira> {
    val filtro = view.filtroVenda
    return PedidoRetira.listaVenda()
      .filter {
        it.filtroNota(filtro.numNota()) &&
        it.filtroVendedor(filtro.vendedor())
      }
  }
  
  fun updateGridSepara() {
    view.updateGridSepara(listSepara())
  }
  
  private fun listSepara(): List<PedidoRetira> {
    val filtro = view.filtroSepara
    return PedidoRetira.listaSepara()
      .filter {
        it.filtroNota(filtro.numNota()) &&
        it.filtroVendedor(filtro.vendedor()) &&
        it.fintroUsuarioV(filtro.usuarioV())
      }
  }
  
  fun updateGridEntregue() {
    view.updateGridEntregue(listEntregue())
  }
  
  private fun listEntregue(): List<PedidoRetira> {
    val filtro = view.filtroEntregue
    return PedidoRetira.listaEntregue()
      .filter {
        it.filtroNota(filtro.numNota()) &&
        it.filtroVendedor(filtro.vendedor()) &&
        it.fintroUsuarioS(filtro.usuarioS())
      }
  }
  
  fun updateGridEditor() {
    view.updateGridEditor(listEditor())
  }
  
  private fun listEditor(): List<PedidoRetira> {
    val filtro = view.filtroEditor
    return PedidoRetira.listaEditor()
      .filter {
        it.filtroNota(filtro.numNota()) &&
        it.filtroVendedor(filtro.vendedor()) &&
        it.fintroUsuarioE(filtro.usuarioE())
      }
  }
  
  //
  fun marcaUserVenda(userNo: Int, pedido: PedidoRetira) = exec {
    pedido.marcaUserVenda(userNo)
    updateGridVenda()
  }
  
  fun desmarcaUserVenda(pedido: PedidoRetira) = exec {
    pedido.marcaUserVenda(0)
    updateGridSepara()
  }
  
  fun marcaUserSepara(userNo: Int, pedido: PedidoRetira) = exec {
    pedido.marcaUserSepara(userNo)
    updateGridSepara()
  }
  
  fun desmarcaUserSepara(pedido: PedidoRetira) = exec {
    pedido.marcaUserSepara(0)
    updateGridEntregue()
  }
  
  fun marcaUserEntregue(userNo: Int, pedido: PedidoRetira) = exec {
    pedido.marcaUserEntregue(userNo)
    updateGridEntregue()
  }
  
  fun desmarcaUserEntregue(pedido: PedidoRetira) = exec {
    pedido.marcaUserEntregue(0)
    updateGridEditor()
  }
}

interface IFiltroVenda {
  fun numNota(): String
  fun vendedor(): Int
}

interface IFiltroSepara {
  fun numNota(): String
  fun vendedor(): Int
  fun usuarioV(): Int
}

interface IFiltroEntregue {
  fun numNota(): String
  fun vendedor(): Int
  fun usuarioS(): Int
}

interface IFiltroEditor {
  fun numNota(): String
  fun vendedor(): Int
  fun usuarioE(): Int
}

interface IPedidoRetiraView: IView {
  fun updateGridVenda(itens: List<PedidoRetira>)
  fun updateGridSepara(itens: List<PedidoRetira>)
  fun updateGridEntregue(itens: List<PedidoRetira>)
  fun updateGridEditor(itens: List<PedidoRetira>)
  
  val filtroVenda: IFiltroVenda
  val filtroEntregue: IFiltroEntregue
  val filtroSepara: IFiltroSepara
  val filtroEditor: IFiltroEditor
  
  //
  fun marcaVenda(pedidoRetira: PedidoRetira?)
  fun desMarcaVenda(pedidoRetira: PedidoRetira?)
  fun marcaSepara(pedidoRetira: PedidoRetira?)
  fun desMarcaSepara(pedidoRetira: PedidoRetira?)
  fun marcaEntregue(pedidoRetira: PedidoRetira?)
  fun desMarcaEntregue(pedidoRetira: PedidoRetira?)
}

data class SenhaUsuario(var nome: String, var senha: String?)