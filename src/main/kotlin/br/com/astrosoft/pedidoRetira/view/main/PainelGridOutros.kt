package br.com.astrosoft.pedidoRetira.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.pedidoRetira.model.beans.PedidoRetira
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroOutros
import br.com.astrosoft.pedidoRetira.viewmodel.IPedidoLinkView
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.textfield.IntegerField
import java.time.LocalDate

class PainelGridOutros(view : IPedidoLinkView, blockUpdate: () -> Unit): PainelGrid<PedidoRetira>(view, blockUpdate) {
  override fun Grid<PedidoRetira>.gridConfig() {
    //setSelectionMode(MULTI)
    colLoja()
    colnumPedido()
    colDataPedido()
    colHoraLink()
    colValorFrete()
    colTotal()
    colMetodo()
    colCartao()
    colStatusTef()
    colUserLink()
    colWhatsapp()
    colVendedor()
    colEmpno()
    colCliente()
    colUsername()
  }
  
  override fun filterBar() = FilterBarOutros()
  
  inner class FilterBarOutros(): FilterBar(), IFiltroOutros {
    lateinit var edtPedido: IntegerField
    lateinit var edtData: DatePicker
    
    override fun FilterBar.contentBlock() {
      /*
      button("Remover") {
        icon = VaadinIcon.ERASER.create()
        addThemeVariants(LUMO_SMALL)
        onLeftClick {view.desmarcaOutros()}
      }
       */
      edtPedido = edtPedido() {
        addValueChangeListener {blockUpdate()}
      }
      edtData = edtDataPedido() {
        addValueChangeListener {blockUpdate()}
      }
    }
    
    override fun numPedido(): Int = edtPedido.value ?: 0
    override fun data(): LocalDate? = edtData.value
  }
}

