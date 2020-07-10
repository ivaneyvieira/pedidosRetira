package br.com.astrosoft.pedidoRetira.view.main

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.view.ViewLayout
import br.com.astrosoft.framework.view.tabGrid
import br.com.astrosoft.pedidoRetira.model.beans.PedidoRetira
import br.com.astrosoft.pedidoRetira.model.beans.UserSaci
import br.com.astrosoft.pedidoRetira.view.layout.PedidoRetiraLayout
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroEditor
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroEntregue
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroSepara
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroVenda
import br.com.astrosoft.pedidoRetira.viewmodel.IPedidoRetiraView
import br.com.astrosoft.pedidoRetira.viewmodel.PedidoRetiraViewModel
import br.com.astrosoft.pedidoRetira.viewmodel.SenhaUsuario
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

@Route(layout = PedidoRetiraLayout::class)
@PageTitle(AppConfig.title)
@HtmlImport("frontend://styles/shared-styles.html")
class PedidoRetiraView: ViewLayout<PedidoRetiraViewModel>(), IPedidoRetiraView {
  private val gridVenda = PainelGridVenda(this) {viewModel.updateGridVenda()}
  private val gridSepara = PainelGridSepara(this) {viewModel.updateGridSepara()}
  private val gridEntregue = PainelGridEntregue(this) {viewModel.updateGridEntregue()}
  private val gridEditor = PainelGridEditor(this) {viewModel.updateGridEditor()}
  override val viewModel: PedidoRetiraViewModel = PedidoRetiraViewModel(this)
  
  override fun isAccept() = true
  
  init {
    val user = AppConfig.userSaci as UserSaci
    tabSheet {
      setSizeFull()
      if(user.acl_venda) tabGrid(TAB_VENDA, gridVenda)
      if(user.acl_separa) tabGrid(TAB_SEPARA, gridSepara)
      if(user.acl_entregue) tabGrid(TAB_ENTREGUE, gridEntregue)
      if(user.acl_editor) tabGrid(TAB_EDITOR, gridEditor)
    }
    when {
      user.acl_venda    -> viewModel.updateGridVenda()
      user.acl_separa   -> viewModel.updateGridSepara()
      user.acl_entregue -> viewModel.updateGridEntregue()
      user.acl_editor   -> viewModel.updateGridEditor()
    }
  }
  
  override fun updateGridVenda(itens: List<PedidoRetira>) {
    gridVenda.updateGrid(itens)
  }
  
  override fun updateGridSepara(itens: List<PedidoRetira>) {
    gridSepara.updateGrid(itens)
  }
  
  override fun updateGridEntregue(itens: List<PedidoRetira>) {
    gridEntregue.updateGrid(itens)
  }
  
  override fun updateGridEditor(itens: List<PedidoRetira>) {
    gridEditor.updateGrid(itens)
  }
  
  override val filtroVenda: IFiltroVenda
    get() = gridVenda.filterBar as IFiltroVenda
  override val filtroSepara: IFiltroSepara
    get() = gridSepara.filterBar as IFiltroSepara
  override val filtroEntregue: IFiltroEntregue
    get() = gridEntregue.filterBar as IFiltroEntregue
  override val filtroEditor: IFiltroEditor
    get() = gridEditor.filterBar as IFiltroEditor
  
  private fun marcaUsuario(pedidoRetira: PedidoRetira?, action: (UserSaci, PedidoRetira) -> Unit) {
    if(pedidoRetira == null)
      showError("Pedido não selecionado")
    else {
      val userSaci = AppConfig.userSaci as UserSaci
      val form = FormUsuario(userSaci.login)
      
      showForm("Senha do Usuário", form) {
        val senha = form.usuario.senha ?: "#######"
        if(senha == userSaci.senha)
          action(userSaci, pedidoRetira)
        else
          showError("A senha não confere")
      }
    }
  }
  
  private fun desmarcaUsuario(pedidoRetira: PedidoRetira?, action: (PedidoRetira) -> Unit) {
    if(pedidoRetira == null)
      showError("Pedido não selecionado")
    else
      action(pedidoRetira)
  }
  
  override fun marcaVenda(pedidoRetira: PedidoRetira?) {
    marcaUsuario(pedidoRetira) {user, pedido ->
      viewModel.marcaUserVenda(user.no, pedido)
    }
  }
  
  override fun desMarcaVenda(pedidoRetira: PedidoRetira?) {
    desmarcaUsuario(pedidoRetira) {pedido ->
      viewModel.desmarcaUserVenda(pedido)
    }
  }
  
  override fun marcaSepara(pedidoRetira: PedidoRetira?) {
    marcaUsuario(pedidoRetira) {user, pedido ->
      viewModel.marcaUserSepara(user.no, pedido)
    }
  }
  
  override fun desMarcaSepara(pedidoRetira: PedidoRetira?) {
    desmarcaUsuario(pedidoRetira) {pedido ->
      viewModel.desmarcaUserSepara(pedido)
    }
  }
  
  override fun marcaEntregue(pedidoRetira: PedidoRetira?) {
    marcaUsuario(pedidoRetira) {user, pedido ->
      viewModel.marcaUserEntregue(user.no, pedido)
    }
  }
  
  override fun desMarcaEntregue(pedidoRetira: PedidoRetira?) {
    desmarcaUsuario(pedidoRetira) {pedido ->
      viewModel.desmarcaUserEntregue(pedido)
    }
  }
  
  companion object {
    const val TAB_VENDA: String = "Venda"
    const val TAB_SEPARA: String = "Separa"
    const val TAB_ENTREGUE: String = "Entregue"
    const val TAB_EDITOR: String = "Editor"
  }
}

class FormUsuario(val username: String): FormLayout() {
  private val binder = Binder<SenhaUsuario>(SenhaUsuario::class.java)
  
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
    binder.bean = SenhaUsuario(username, "")
  }
  
  val usuario
    get() = binder.bean
}

