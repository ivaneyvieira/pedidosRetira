package br.com.astrosoft.pedidoRetira.view.main

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.framework.view.addColumnButton
import br.com.astrosoft.pedidoRetira.model.beans.PedidoRetira
import br.com.astrosoft.pedidoRetira.model.beans.UserSaci
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroEntregue
import br.com.astrosoft.pedidoRetira.viewmodel.IPedidoRetiraView
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField

class PainelGridEntregue(view: IPedidoRetiraView, blockUpdate: () -> Unit): PainelGrid<PedidoRetira>(view,
                                                                                                     blockUpdate) {
  override fun Grid<PedidoRetira>.gridConfig() {
    addColumnButton(VaadinIcon.ARROW_FORWARD, view::marcaEntregue)
    colLoja()
    colnumPedido()
    colDataPedido()
    colNotaFiscal()
    colDataNota()
    colHoraNota()
    colValor()
    colUsuarioS()
    colVendedor()
    colObs()
  }
  
  override fun filterBar() = FilterBarPedido()
  
  inner class FilterBarPedido: FilterBar(), IFiltroEntregue {
    lateinit var edtNota: TextField
    lateinit var edtVendedor: IntegerField
    lateinit var edtUsuario: IntegerField
    
    override fun FilterBar.contentBlock() {
      val userSaci = AppConfig.userSaci as UserSaci
      button("Desmarca Link") {
        isVisible = userSaci.admin
        icon = VaadinIcon.CHECK_CIRCLE_O.create()
        addThemeVariants(LUMO_SMALL)
        onLeftClick {view.desMarcaSepara(selectionItem())}
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
    
    override fun usuarioS(): Int = edtUsuario.value ?: 0
  }
}
