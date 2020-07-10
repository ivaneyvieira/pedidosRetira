package br.com.astrosoft.pedidoRetira.view.main

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.pedidoRetira.model.beans.PedidoRetira
import br.com.astrosoft.pedidoRetira.model.beans.UserSaci
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroEditor
import br.com.astrosoft.pedidoRetira.viewmodel.IPedidoRetiraView
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField

class PainelGridEditor(view: IPedidoRetiraView, blockUpdate: () -> Unit): PainelGrid<PedidoRetira>(view,
                                                                                                   blockUpdate) {
  override fun Grid<PedidoRetira>.gridConfig() {
    colLoja()
    colnumPedido()
    colDataPedido()
    colNotaFiscal()
    colDataNota()
    colValor()
    colUsuarioE()
    colVendedor()
    colObs()
  }
  
  override fun filterBar() = FilterBarPendente()
  
  inner class FilterBarPendente: FilterBar(), IFiltroEditor {
    lateinit var edtNota: TextField
    lateinit var edtVendedor: IntegerField
    lateinit var edtUsuario: IntegerField
    
    override fun FilterBar.contentBlock() {
      val userSaci = AppConfig.userSaci as UserSaci
      button("Desmarca Link") {
        isVisible = userSaci.admin
        icon = VaadinIcon.CHECK_CIRCLE_O.create()
        addThemeVariants(LUMO_SMALL)
        onLeftClick {view.desMarcaEntregue(selectionItem())}
      }
      edtNota = numNota {
        addValueChangeListener {blockUpdate()}
      }
      edtVendedor = vendedor {
        addValueChangeListener {blockUpdate()}
      }
      edtUsuario = usuario {
        addValueChangeListener {blockUpdate()}
      }
    }
    
    override fun numNota(): String = edtNota.value ?: ""
    
    override fun vendedor(): Int = edtVendedor.value ?: 0
    
    override fun usuarioE(): Int = edtUsuario.value ?: 0
  }
}

