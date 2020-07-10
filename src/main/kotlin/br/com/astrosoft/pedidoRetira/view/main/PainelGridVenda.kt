package br.com.astrosoft.pedidoRetira.view.main

import br.com.astrosoft.AppConfig
import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.framework.view.addColumnButton
import br.com.astrosoft.framework.view.addColumnButtonClipBoard
import br.com.astrosoft.pedidoRetira.model.beans.PedidoRetira
import br.com.astrosoft.pedidoRetira.model.beans.UserSaci
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroVenda
import br.com.astrosoft.pedidoRetira.viewmodel.IPedidoRetiraView
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.button.ButtonVariant.LUMO_SMALL
class PainelGridVenda(view: IPedidoRetiraView, blockUpdate: () -> Unit): PainelGrid<PedidoRetira>(view, blockUpdate) {
  override fun Grid<PedidoRetira>.gridConfig() {
    addColumnButton(VaadinIcon.ARROW_FORWARD, view::marcaVenda)
    colLoja()
    colnumPedido()
    colDataPedido()
    colNotaFiscal()
    colDataNota()
    colValor()
    colVendedor()
    colObs()
  }
  
  override fun filterBar() = FilterBarPedido()
  
  inner class FilterBarPedido: FilterBar(), IFiltroVenda {
    lateinit var edtNota: TextField
    lateinit var edtVendedor: IntegerField
    
    override fun FilterBar.contentBlock() {

      edtNota = numNota {
        addValueChangeListener {blockUpdate()}
      }
      edtVendedor = vendedor {
        addValueChangeListener {blockUpdate()}
      }
    }
    
    override fun numNota(): String = edtNota.value ?: ""
    
    override fun vendedor(): Int = edtVendedor.value ?: 0
  }
}

