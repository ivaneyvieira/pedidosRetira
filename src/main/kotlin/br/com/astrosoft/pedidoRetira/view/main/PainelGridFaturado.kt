package br.com.astrosoft.pedidoRetira.view.main

import br.com.astrosoft.framework.view.PainelGrid
import br.com.astrosoft.pedidoRetira.model.beans.PedidoRetira
import br.com.astrosoft.pedidoRetira.viewmodel.IFiltroFaturado
import br.com.astrosoft.pedidoRetira.viewmodel.IPedidoLinkView
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.textfield.IntegerField
import java.time.LocalDate

class PainelGridFaturado(view : IPedidoLinkView, blockUpdate: () -> Unit): PainelGrid<PedidoRetira>(view, blockUpdate) {
  override fun Grid<PedidoRetira>.gridConfig() {
    colLoja()
    colnumPedido()
    colDataPedido()
    colValorFrete()
    colTotal()
    colValorLink()
    colMetodo()
    colStatusTef()
    colUserLink()
    colCartao()
    colAutorizadora()
    colParcelas()
    colAutorizacao()
    colNsuHost()
    colDataTef()
    colVendedor()
  }
  
  override fun filterBar() = FilterBarFaturar()
  
  inner class FilterBarFaturar(): FilterBar(), IFiltroFaturado {
    lateinit var edtPedido: IntegerField
    lateinit var edtData: DatePicker
    
    override fun FilterBar.contentBlock() {
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

