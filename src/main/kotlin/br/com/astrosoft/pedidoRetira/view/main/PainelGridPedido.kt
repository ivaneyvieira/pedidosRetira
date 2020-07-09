package br.com.astrosoft.pedidoRetira.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.framework.view.addColumnButtonClipBoard
import br.com.astrosoft.pedidoRetira.model.beans.PedidoRetira
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroPedido
import br.com.astrosoft.pedidoRetira.viewmodel.IPedidoLinkView
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import java.time.LocalDate

class PainelGridPedido(view: IPedidoLinkView, blockUpdate: () -> Unit): PainelGrid<PedidoRetira>(view, blockUpdate) {
  override fun Grid<PedidoRetira>.gridConfig() {
    addColumnButtonClipBoard(VaadinIcon.ARROW_FORWARD, view::marcaVendedor, {noteClipBoard})
    colLoja()
    colnumPedido()
    colDataPedido()
    //colHoraPedido()
    colValorFrete()
    colTotal()
    colMetodo()
    colCartao()
    colStatusTef()
    colWhatsapp()
    colEmpno()
    colVendedor()
    colCliente()
    colUsername()
  }
  
  override fun filterBar() = FilterBarPedido()
  
  inner class FilterBarPedido: FilterBar(), IFiltroPedido {
    lateinit var edtPedido: IntegerField
    lateinit var edtData: DatePicker
    lateinit var edtVendedor: TextField
    
    override fun FilterBar.contentBlock() {
      edtPedido = edtPedido() {
        addValueChangeListener {blockUpdate()}
      }
      edtVendedor = edtVendedor() {
        addValueChangeListener {blockUpdate()}
      }
      edtData = edtDataPedido() {
        addValueChangeListener {blockUpdate()}
      }
    }
    
    override fun numPedido(): Int = edtPedido.value ?: 0
    
    override fun vendedor(): String = edtVendedor.value ?: ""
    
    override fun data(): LocalDate? = edtData.value
  }
}

