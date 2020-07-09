package br.com.astrosoft.pedidoRetira.view.main

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.framework.view.tabGrid
import br.com.astrosoft.pedidoRetira.model.beans.PedidoRetira
import br.com.astrosoft.pedidoRetira.model.beans.UserSaci
import br.com.astrosoft.pedidoRetira.view.layout.PedidoRetiraLayout
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroFaturado
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroFinalizar
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroGerarLink
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroLink
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroOutros
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroPedido
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroPendente
import br.com.astrosoft.pedidoRetira.viewmodel.IPedidoLinkView
import br.com.astrosoft.pedidoRetira.viewmodel.PedidoRetiraViewModel
import br.com.astrosoft.pedidoRetira.viewmodel.SenhaUsuario
import br.com.astrosoft.pedidoRetira.viewmodel.SenhaVendendor
import com.github.mvysny.karibudsl.v10.bind
import com.github.mvysny.karibudsl.v10.passwordField
import com.github.mvysny.karibudsl.v10.tabSheet
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.dependency.HtmlImport
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.textfield.TextFieldVariant.LUMO_SMALL
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import java.io.InputStream

@Route(layout = PedidoRetiraLayout::class)
@PageTitle(AppConfig.title)
@HtmlImport("frontend://styles/shared-styles.html")
class PedidoRetiraView: ViewLayout<PedidoRetiraViewModel>(), IPedidoLinkView {
  private val gridPedido = PainelGridPedido(this) {viewModel.updateGridPedido()}
  private val gridLink = PainelGridLink(this) {viewModel.updateGridLink()}
  private val gridGerarLink = PainelGridGerarLink(this) {viewModel.updateGridGerarLink()}
  private val gridPendente = PainelGridPendente(this) {viewModel.updateGridPendente()}
  private val gridFinalizar = PainelGridFinalizado(this) {viewModel.updateGridFinalizar()}
  private val gridFaturar = PainelGridFaturado(this) {viewModel.updateGridFaturado()}
  private val gridOutros = PainelGridOutros(this) {viewModel.updateGridOutros()}
  override val viewModel: PedidoRetiraViewModel = PedidoRetiraViewModel(this)
  
  override fun isAccept() = true
  
  init {
    val user = AppConfig.userSaci as UserSaci
    tabSheet {
      setSizeFull()
      if(user.acl_pedido) tabGrid(TAB_PEDIDO, gridPedido)
      if(user.acl_link) tabGrid(TAB_GERAR_LINK, gridGerarLink)
      if(user.acl_link) tabGrid(TAB_LINK, gridLink)
      if(user.acl_pendente) tabGrid(TAB_PENDENTE, gridPendente)
      if(user.acl_finalizar) tabGrid(TAB_FINALIZAR, gridFinalizar)
      if(user.acl_faturado) tabGrid(TAB_FATURADO, gridFaturar)
      if(user.acl_outros) tabGrid(TAB_OUTROS, gridOutros)
    }
    when {
      user.acl_pedido    -> viewModel.updateGridPedido()
      user.acl_link      -> viewModel.updateGridGerarLink()
      user.acl_pendente  -> viewModel.updateGridPendente()
      user.acl_finalizar -> viewModel.updateGridFinalizar()
      user.acl_faturado  -> viewModel.updateGridFaturado()
      user.acl_outros    -> viewModel.updateGridOutros()
    }
  }
  
  override fun marcaUserLink(pedidoLink: PedidoRetira) {
    val userSaci = AppConfig.userSaci as UserSaci
    val form = FormUsuario()
    val usuario = SenhaUsuario(userSaci.login, "")
    form.binder.bean = usuario
    showForm("Senha do Usuário", form) {
      val senha = form.binder.bean.senha ?: "#######"
      viewModel.marcaUserLink(pedidoLink, senha)
    }
  }
  
  override fun desmarcaUserLink() {
    viewModel.desmarcaUserLink()
  }
  
  override fun desmarcaPedidoLink() {
    viewModel.desmarcaVendedor()
  }
  
  override fun marcaLink(pedidoLink: PedidoRetira) {
    viewModel.marcaPedido(pedidoLink)
  }
  
  override fun uploadFile(inputStream: InputStream) {
    viewModel.uploadFile(inputStream)
  }
  
  override fun desmarcaPedido() {
    if(itensSelecionadoPendente().isEmpty()) showError("Nenhum pedido foi selecionado")
    viewModel.desmarcaPedido()
  }
  
  override fun marcaVendedor(pedidoLink: PedidoRetira) {
    val form = FormVendedor()
    val vendendor = SenhaVendendor(pedidoLink.vendedor ?: "Não encontrado", "")
    form.binder.bean = vendendor
    showForm("Senha do vendedor", form) {
      val senha = form.binder.bean.senha ?: "#######"
      viewModel.marcaVendedor(pedidoLink, senha)
    }
  }
  
  override fun itensSelecionadoPedido(): List<PedidoRetira> {
    return gridPedido.selectedItems()
  }
  
  override fun itensSelecionadoGerarLink(): List<PedidoRetira> {
    return gridGerarLink.selectedItems()
  }

  override fun itensSelecionadoLink(): List<PedidoRetira> {
    return gridLink.selectedItems()
  }
  
  override fun itensSelecionadoPendente(): List<PedidoRetira> {
    return gridPendente.selectedItems()
  }
  
  override fun updateGridPedido(itens: List<PedidoRetira>) {
    gridPedido.updateGrid(itens)
  }
  
  override fun updateGridLink(itens: List<PedidoRetira>) {
    gridLink.updateGrid(itens)
  }
  
  override fun updateGridGerarLink(itens: List<PedidoRetira>) {
    gridGerarLink.updateGrid(itens)
  }
  
  override fun updateGridPendente(itens: List<PedidoRetira>) {
    gridPendente.updateGrid(itens)
  }
  
  override fun updateGridFinalizar(itens: List<PedidoRetira>) {
    gridFinalizar.updateGrid(itens)
  }
  
  override fun updateGridFaturado(itens: List<PedidoRetira>) {
    gridFaturar.updateGrid(itens)
  }
  
  override fun updateGridOutros(itens: List<PedidoRetira>) {
    gridOutros.updateGrid(itens)
  }
  
  override val filtroPedido: IFiltroPedido
    get() = gridPedido.filterBar as IFiltroPedido
  override val filtroLink: IFiltroLink
    get() = gridLink.filterBar as IFiltroLink
  override val filtroGerarLink: IFiltroGerarLink
    get() = gridGerarLink.filterBar as IFiltroGerarLink
  override val filtroPendente: IFiltroPendente
    get() = gridPendente.filterBar as IFiltroPendente
  override val filtroFinalizar: IFiltroFinalizar
    get() = gridFinalizar.filterBar as IFiltroFinalizar
  override val filtroFaturado: IFiltroFaturado
    get() = gridFaturar.filterBar as IFiltroFaturado
  override val filtroOutros: IFiltroOutros
    get() = gridOutros.filterBar as IFiltroOutros
  
  companion object {
    const val TAB_PEDIDO: String = "Pedido"
    const val TAB_GERAR_LINK: String = "Gerar Link"
    const val TAB_LINK: String = "Link"
    const val TAB_PENDENTE: String = "Pendente"
    const val TAB_FINALIZAR: String = "Finalzar"
    const val TAB_FATURADO: String = "Faturado"
    const val TAB_OUTROS: String = "Outros status"
  }
}

class FormVendedor: FormLayout() {
  val binder = Binder<SenhaVendendor>(SenhaVendendor::class.java)
  
  init {
    textField("Nome") {
      isEnabled = false
      addThemeVariants(LUMO_SMALL)
      bind(binder).bind(SenhaVendendor::nome)
    }
    
    passwordField("Senha") {
      addThemeVariants(LUMO_SMALL)
      bind(binder).bind(SenhaVendendor::senha)
      this.isAutofocus = true
    }
  }
}

class FormUsuario: FormLayout() {
  val binder = Binder<SenhaUsuario>(SenhaUsuario::class.java)
  
  init {
    textField("Nome") {
      isEnabled = false
      addThemeVariants(LUMO_SMALL)
      bind(binder).bind(SenhaUsuario::nome)
    }
    
    passwordField("Senha") {
      addThemeVariants(LUMO_SMALL)
      bind(binder).bind(SenhaUsuario::senha)
      this.isAutofocus = true
    }
  }
}

