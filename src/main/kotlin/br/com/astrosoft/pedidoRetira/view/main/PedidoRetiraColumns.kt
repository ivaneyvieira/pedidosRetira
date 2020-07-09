package br.com.astrosoft.pedidoRetira.view.main

import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnLocalTime
import br.com.astrosoft.framework.view.addColumnString
import br.com.astrosoft.framework.view.localePtBr
import br.com.astrosoft.pedidoRetira.model.beans.PedidoRetira
import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.addColumnFor
import com.github.mvysny.karibudsl.v10.datePicker
import com.github.mvysny.karibudsl.v10.integerField
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.component.textfield.TextFieldVariant
import com.vaadin.flow.data.value.ValueChangeMode

fun Grid<PedidoRetira>.colLoja() = addColumnInt(PedidoRetira::loja) {
  setHeader("Lj")
  width = "4em"
}

fun Grid<PedidoRetira>.colnumPedido() = addColumnInt(PedidoRetira::numPedido) {
  setHeader("Pedido")
  width = "7em"
}

fun Grid<PedidoRetira>.colDataPedido() = addColumnLocalDate(PedidoRetira::dataPedido) {
  setHeader("Data")
}

fun Grid<PedidoRetira>.colHoraPedido() = addColumnLocalTime(PedidoRetira::horaPedido) {
  setHeader("Hora")
}

fun Grid<PedidoRetira>.colMetodo() = addColumnInt(PedidoRetira::metodo) {
  setHeader("Mét")
  width = "5em"
}

fun Grid<PedidoRetira>.colNotaFiscal() = addColumnString(PedidoRetira::notaFiscal) {
  setHeader("NF")
}

fun Grid<PedidoRetira>.colDataNota() = addColumnLocalDate(PedidoRetira::dataNota) {
  setHeader("Data NF")
}

fun Grid<PedidoRetira>.colHoraNota() = addColumnLocalTime(PedidoRetira::horaNota) {
  setHeader("Hr NF")
}

fun Grid<PedidoRetira>.colDataLink() = addColumnLocalDate(PedidoRetira::dataLink) {
  setHeader("Data Link")
}

fun Grid<PedidoRetira>.colHoraLink() = addColumnLocalTime(PedidoRetira::horaLink) {
  setHeader("Hr Link")
}

fun Grid<PedidoRetira>.colUsername() = addColumnString(PedidoRetira::username) {
  setHeader("Usuário")
}

fun Grid<PedidoRetira>.colObs() = addColumnString(PedidoRetira::obs) {
  setHeader("Obs")
}

fun Grid<PedidoRetira>.colNota() = addColumnString(PedidoRetira::nota) {
  setHeader("LJ")
}

fun Grid<PedidoRetira>.statusPedido() = addColumnFor(PedidoRetira::statusPedido) {
  setHeader("Status")
}

fun Grid<PedidoRetira>.colValorFrete() = addColumnDouble(PedidoRetira::valorFrete) {
  setHeader("Frete")
}

fun Grid<PedidoRetira>.colTotal() = addColumnDouble(PedidoRetira::total) {
  setHeader("Total")
}

fun Grid<PedidoRetira>.colValorLink() = addColumnDouble(PedidoRetira::valorLink) {
  setHeader("Valor TEF")
}

fun Grid<PedidoRetira>.colCartao() = addColumnString(PedidoRetira::cartao) {
  setHeader("Cartão")
}

fun Grid<PedidoRetira>.colWhatsapp() = addColumnString(PedidoRetira::whatsapp) {
  setHeader("Whatsapp")
}

fun Grid<PedidoRetira>.colCliente() = addColumnString(PedidoRetira::cliente) {
  setHeader("Cliente")
}

fun Grid<PedidoRetira>.colEmpno() = addColumnInt(PedidoRetira::empno) {
  setHeader("Nº Vend")
}

fun Grid<PedidoRetira>.colUserLink() = addColumnInt(PedidoRetira::userLink) {
  setHeader("Usu Link")
}

fun Grid<PedidoRetira>.colVendedor() = addColumnString(PedidoRetira::vendedor) {
  setHeader("Vendedor")
}

fun Grid<PedidoRetira>.colParcelas() = addColumnInt(PedidoRetira::parcelas) {
  setHeader("Parc")
  width = "4em"
}

fun Grid<PedidoRetira>.colAutorizadora() = addColumnString(PedidoRetira::autorizadora) {
  setHeader("Autoriz")
}

fun Grid<PedidoRetira>.colAutorizacao() = addColumnString(PedidoRetira::autorizacao) {
  setHeader("Autorização")
}

fun Grid<PedidoRetira>.colNsuHost() = addColumnString(PedidoRetira::nsuHost) {
  setHeader("Nsu Host")
}

fun Grid<PedidoRetira>.colStatusTef() = addColumnString(PedidoRetira::statusTef) {
  setHeader("Status Tef")
}

fun Grid<PedidoRetira>.colDataTef() = addColumnLocalDate(PedidoRetira::dataTef) {
  setHeader("Data Tef")
}

//Campos de filtro
fun (@VaadinDsl HasComponents).edtPedido(block: (@VaadinDsl IntegerField).() -> Unit = {}) = integerField("Pedido") {
  this.valueChangeMode = ValueChangeMode.TIMEOUT
  this.isAutofocus = true
  addThemeVariants(TextFieldVariant.LUMO_SMALL)
  block()
}

fun (@VaadinDsl HasComponents).edtVendedor(block: (@VaadinDsl TextField).() -> Unit = {}) = textField("Vendedor") {
  this.valueChangeMode = ValueChangeMode.TIMEOUT
  addThemeVariants(TextFieldVariant.LUMO_SMALL)
  block()
}

fun (@VaadinDsl HasComponents).edtDataPedido(block: (@VaadinDsl DatePicker).() -> Unit = {}) = datePicker("Data") {
  localePtBr()
  isClearButtonVisible = true
  element.setAttribute("theme", "small")
  block()
}

