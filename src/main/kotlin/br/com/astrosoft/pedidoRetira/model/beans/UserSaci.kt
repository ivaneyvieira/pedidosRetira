package br.com.astrosoft.pedidoRetira.model.beans

import br.com.astrosoft.framework.spring.IUser
import br.com.astrosoft.pedidoRetira.model.saci
import kotlin.math.pow

class UserSaci: IUser {
  var no: Int = 0
  var name: String = ""
  override var login: String = ""
  override var senha: String = ""
  override fun roles(): List<String> {
    val roles = if(admin) listOf("ADMIN") else listOf("USER")
    val roleVenda = if(acl_venda) listOf("VENDA") else listOf()
    val roleSepara = if(acl_separa) listOf("SEPARA") else listOf()
    val roleEntregue = if(acl_entregue) listOf("ENTREGUE") else listOf()
    val roleEditor = if(acl_editor) listOf("EDITOR") else listOf()
    return roles + roleVenda + roleSepara + roleEntregue + roleEditor
  }
  
  var bitAcesso: Int = 0
  var prntno: Int = 0
  var impressora: String = ""
  var storeno: Int = 0
  
  //Otiros campos
  var ativo
    get() = (bitAcesso and BIT_ATIVO) != 0 || admin
    set(value) {
      bitAcesso = if(value) bitAcesso or BIT_ATIVO
      else bitAcesso and BIT_ATIVO.inv()
    }
  val admin
    get() = login == "ADM"
  var acl_venda
    get() = (bitAcesso and BIT_VENDA) != 0 || admin
    set(value) {
      bitAcesso = if(value) bitAcesso or BIT_VENDA
      else bitAcesso and BIT_VENDA.inv()
    }
  var acl_separa
    get() = (bitAcesso and BIT_SEPARA) != 0 || admin
    set(value) {
      bitAcesso = if(value) bitAcesso or BIT_SEPARA
      else bitAcesso and BIT_SEPARA.inv()
    }
  var acl_entregue
    get() = (bitAcesso and BIT_ENTREGUE) != 0 || admin
    set(value) {
      bitAcesso = if(value) bitAcesso or BIT_ENTREGUE
      else bitAcesso and BIT_ENTREGUE.inv()
    }
  var acl_editor
    get() = (bitAcesso and BIT_EDITOR) != 0 || admin
    set(value) {
      bitAcesso = if(value) bitAcesso or BIT_EDITOR
      else bitAcesso and BIT_EDITOR.inv()
    }
  
  companion object {
    private val BIT_ATIVO = 2.pow(9)
    private val BIT_VENDA = 2.pow(0)
    private val BIT_SEPARA = 2.pow(1)
    private val BIT_ENTREGUE = 2.pow(2)
    private val BIT_EDITOR = 2.pow(3)
    
    fun findAll(): List<UserSaci>? {
      return saci.findAllUser()
        .filter {it.ativo}
    }
    
    fun updateUser(user: UserSaci) {
      saci.updateUser(user)
    }
    
    fun findUser(login: String?): UserSaci? {
      return saci.findUser(login)
        .firstOrNull()
    }
  }
}

fun Int.pow(e: Int): Int = this.toDouble()
  .pow(e)
  .toInt()
