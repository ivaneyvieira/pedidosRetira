package br.com.astrosoft.pedidoRetira.viewmodel

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.viewmodel.IView
import br.com.astrosoft.framework.viewmodel.ViewModel
import br.com.astrosoft.framework.viewmodel.fail
import br.com.astrosoft.pedidoRetira.model.beans.PedidoRetira
import br.com.astrosoft.pedidoRetira.model.beans.UserSaci
import java.io.InputStream
import java.time.LocalDate
import java.time.LocalTime

class PedidoRetiraViewModel(view: IPedidoLinkView): ViewModel<IPedidoLinkView>(view) {
  fun updateGridPedido() {
    view.updateGridPedido(listPedido())
  }
  
  private fun listPedido(): List<PedidoRetira> {
    val filtro = view.filtroPedido
    return PedidoRetira.listaPedido()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null)
        && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
        && (it.vendedor?.startsWith(filtro.vendedor()) == true
            || it.empno?.toString() == filtro.vendedor()
            || filtro.vendedor() == "")
      }
  }
  
  fun updateGridGerarLink() {
    view.updateGridGerarLink(listGerarLink())
  }
  
  private fun listGerarLink(): List<PedidoRetira> {
    val filtro = view.filtroGerarLink
    return PedidoRetira.listaGerarLink()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null)
        && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
        && (it.vendedor?.startsWith(filtro.vendedor()) == true
            || it.empno?.toString() == filtro.vendedor()
            || filtro.vendedor() == "")
      }
  }
  
  fun updateGridLink() {
    view.updateGridLink(listLink())
  }
  
  private fun listLink(): List<PedidoRetira> {
    val filtro = view.filtroLink
    return PedidoRetira.listaLink()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null)
        && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
        && (it.vendedor?.startsWith(filtro.vendedor()) == true
            || it.empno?.toString() == filtro.vendedor()
            || filtro.vendedor() == "")
      }
  }
  
  fun updateGridPendente() {
    view.updateGridPendente(listPendente())
  }
  
  private fun listPendente(): List<PedidoRetira> {
    val filtro = view.filtroPendente
    return PedidoRetira.listaPendente()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null)
        && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
        && (it.vendedor?.startsWith(filtro.vendedor()) == true
            || it.empno?.toString() == filtro.vendedor()
            || filtro.vendedor() == "")
      }
  }
  
  fun updateGridFinalizar() {
    view.updateGridFinalizar(listFinalizado())
  }
  
  private fun listFinalizado(): List<PedidoRetira> {
    val filtro = view.filtroFinalizar
    return PedidoRetira.listaFinalizar()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null)
        && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
      }
  }
  
  fun updateGridFaturado() {
    view.updateGridFaturado(listFaturado())
  }
  
  private fun listFaturado(): List<PedidoRetira> {
    val filtro = view.filtroFaturado
    return PedidoRetira.listaFaturado()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null)
        && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
      }
  }
  
  fun updateGridOutros() {
    view.updateGridOutros(listOutros())
  }
  
  private fun listOutros(): List<PedidoRetira> {
    val filtro = view.filtroOutros
    return PedidoRetira.listaOutros()
      .filter {
        (it.dataPedido == filtro.data() || filtro.data() == null)
        && (it.numPedido == filtro.numPedido() || filtro.numPedido() == 0)
      }
  }
  
  fun marcaPedido(pedido: PedidoRetira?) = exec {
    pedido?.marcaHorario(LocalDate.now(), LocalTime.now())
    updateGridLink()
  }
  
  fun desmarcaPedido() = exec {
    val itens =
      view.itensSelecionadoPendente()
        .ifEmpty {fail("Nenhum item selecionado")}
    itens.forEach {pedidoLink: PedidoRetira ->
      pedidoLink.marcaHorario(null, null)
    }
    updateGridPendente()
  }
  
  fun uploadFile(inputStream: InputStream) {
    PedidoRetira.uploadFile(inputStream)
    updateGridPendente()
  }
  
  fun marcaVendedor(pedido: PedidoRetira, senha: String) = exec {
    if(pedido.senhaVendedor == senha) pedido.marcaVendedor("S")
    else fail("Senha incorreta")
    updateGridPedido()
  }
  
  fun desmarcaVendedor() = exec {
    val itens =
      view.itensSelecionadoGerarLink()
        .ifEmpty {fail("Nenhum item selecionado")}
    itens.forEach {pedidoLink: PedidoRetira ->
      pedidoLink.marcaVendedor("")
    }
    updateGridGerarLink()
  }
  
  fun marcaUserLink(pedido: PedidoRetira, senha: String) = exec {
    val userSaci = AppConfig.userSaci as UserSaci
    if(userSaci.senha == senha)
      pedido.marcaUserLink(userSaci.no)
    else
      fail("Senha incorreta");
    
    updateGridGerarLink()
  }
  
  fun desmarcaUserLink() = exec {
    val itens =
      view.itensSelecionadoLink()
        .ifEmpty {fail("Nenhum item selecionado")}
    itens.forEach {pedidoLink: PedidoRetira ->
      pedidoLink.marcaUserLink(0)
    }
    updateGridLink()
  }
}

interface IFiltroPedido {
  fun numPedido(): Int
  fun vendedor(): String;
  fun data(): LocalDate?
}

interface IFiltroGerarLink {
  fun numPedido(): Int
  fun vendedor(): String;
  fun data(): LocalDate?
}

interface IFiltroLink {
  fun numPedido(): Int
  fun vendedor(): String;
  fun data(): LocalDate?
}

interface IFiltroPendente {
  fun numPedido(): Int
  fun vendedor(): String;
  fun data(): LocalDate?
}

interface IFiltroFinalizar {
  fun numPedido(): Int
  fun data(): LocalDate?
}

interface IFiltroFaturado {
  fun numPedido(): Int
  fun data(): LocalDate?
}

interface IFiltroOutros {
  fun numPedido(): Int
  fun data(): LocalDate?
}

interface IPedidoLinkView: IView {
  fun updateGridPedido(itens: List<PedidoRetira>)
  fun updateGridGerarLink(itens: List<PedidoRetira>)
  fun updateGridLink(itens: List<PedidoRetira>)
  fun updateGridPendente(itens: List<PedidoRetira>)
  fun updateGridFinalizar(itens: List<PedidoRetira>)
  fun updateGridFaturado(itens: List<PedidoRetira>)
  fun updateGridOutros(itens: List<PedidoRetira>)
  
  fun itensSelecionadoPedido(): List<PedidoRetira>
  fun itensSelecionadoGerarLink(): List<PedidoRetira>
  fun itensSelecionadoLink(): List<PedidoRetira>
  fun itensSelecionadoPendente(): List<PedidoRetira>
  
  val filtroPedido: IFiltroPedido
  val filtroLink: IFiltroLink
  val filtroGerarLink: IFiltroGerarLink
  val filtroPendente: IFiltroPendente
  val filtroFinalizar: IFiltroFinalizar
  val filtroFaturado: IFiltroFaturado
  val filtroOutros: IFiltroOutros
  
  //
  fun marcaLink(pedidoLink: PedidoRetira)
  fun desmarcaPedidoLink()
  fun marcaVendedor(pedidoLink: PedidoRetira)
  fun desmarcaPedido()
  fun marcaUserLink(pedidoLink: PedidoRetira)
  fun desmarcaUserLink()
  fun uploadFile(inputStream: InputStream)
}

data class SenhaVendendor(var nome: String, var senha: String?)

data class SenhaUsuario(var nome: String, var senha: String?)