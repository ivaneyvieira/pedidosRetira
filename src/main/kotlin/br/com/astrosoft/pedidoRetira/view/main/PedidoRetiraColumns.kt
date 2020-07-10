package br.com.astrosoft.pedidoRetira.view.main

import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.pedidoRetira.model.beans.PedidoRetira
import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.integerField
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.textfield.TextFieldVariant
import com.vaadin.flow.data.value.ValueChangeMode

fun Grid<PedidoRetira>.colLoja() = addColumnInt(PedidoRetira::loja) {
  setHeader("Lj")
  width = "4em"
}

fun Grid<PedidoRetira>.colnumPedido() = addColumnInt(PedidoRetira::pedido) {
  setHeader("Pedido")
  width = "7em"
}

fun Grid<PedidoRetira>.colDataPedido() = addColumnLocalDate(PedidoRetira::dataPedido) {
  setHeader("Data")
}

fun Grid<PedidoRetira>.colNotaFiscal() = addColumnString(PedidoRetira::notaFiscal) {
  setHeader("NF")
}

fun Grid<PedidoRetira>.colDataNota() = addColumnLocalDate(PedidoRetira::dataNota) {
  setHeader("Data NF")
}

fun Grid<PedidoRetira>.colValor() = addColumnDouble(PedidoRetira::valor) {
  setHeader("Valor")
}


fun Grid<PedidoRetira>.colUsuarioV() = addColumnInt(PedidoRetira::usuarioV) {
  setHeader("Usuário")
}

fun Grid<PedidoRetira>.colUsuarioS() = addColumnInt(PedidoRetira::usuarioS) {
  setHeader("Usuário")
}

fun Grid<PedidoRetira>.colUsuarioE() = addColumnInt(PedidoRetira::usuarioE) {
  setHeader("Usuário")
}

fun Grid<PedidoRetira>.colVendedor() = addColumnInt(PedidoRetira::vendedor) {
  setHeader("Vendedor")
}

fun Grid<PedidoRetira>.colObs() = addColumnString(PedidoRetira::obs) {
  setHeader("Obs")
}

//Campos de filtro
fun (@VaadinDsl HasComponents).numNota(block: (@VaadinDsl TextField).() -> Unit = {}) = textField("Nota") {
  this.valueChangeMode = ValueChangeMode.TIMEOUT
  addThemeVariants(TextFieldVariant.LUMO_SMALL)
  this.isAutofocus = true
  block()
}

fun (@VaadinDsl HasComponents).vendedor(block: (@VaadinDsl IntegerField).() -> Unit = {}) = integerField("Vendedor") {
  this.valueChangeMode = ValueChangeMode.TIMEOUT
  addThemeVariants(TextFieldVariant.LUMO_SMALL)
  block()
}

fun (@VaadinDsl HasComponents).usuario(block: (@VaadinDsl IntegerField).() -> Unit = {}) = integerField("Usuário") {
  this.valueChangeMode = ValueChangeMode.TIMEOUT
  addThemeVariants(TextFieldVariant.LUMO_SMALL)
  block()
}

