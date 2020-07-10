package br.com.astrosoft.pedidoRetira.model

import br.com.astrosoft.framework.model.QueryDB
import br.com.astrosoft.framework.util.DB
import br.com.astrosoft.framework.util.toSaciDate
import br.com.astrosoft.pedidoRetira.model.beans.PedidoRetira
import br.com.astrosoft.pedidoRetira.model.beans.UserSaci
import java.time.LocalDate
import java.time.LocalTime

class QuerySaci: QueryDB(driver, url, username, password) {
  fun findUser(login: String?): List<UserSaci> {
    login ?: return emptyList()
    val sql = "/sqlSaci/userSenha.sql"
    return query(sql, UserSaci::class) {
      addParameter("login", login)
    }
  }
  
  fun findAllUser(): List<UserSaci> {
    val sql = "/sqlSaci/userSenha.sql"
    return query(sql, UserSaci::class) {
      addParameter("login", "TODOS")
    }
  }
  
  fun updateUser(user: UserSaci) {
    val sql = "/sqlSaci/updateUser.sql"
    script(sql) {
      addOptionalParameter("login", user.login)
      addOptionalParameter("bitAcesso", user.bitAcesso)
      addOptionalParameter("storeno", user.storeno)
    }
  }
  
  fun listaPedidoRetira(storeno: Int): List<PedidoRetira> {
    val sql = "/sqlSaci/pedidoRetira.sql"
    val data = LocalDate.now().minusDays(15).toSaciDate()
    return query(sql, PedidoRetira::class) {
      addOptionalParameter("storeno", storeno)
      addOptionalParameter("data", data)
    }
  }
  
  fun marcaUserVenda(loja: Int, numPedido: Int, userno: Int) {
    val sql = "/sqlSaci/marcaUserVenda.sql"
    script(sql) {
      addParameter("storeno", loja)
      addParameter("ordno", numPedido)
      addParameter("userno", userno)
    }
  }
  
  fun marcaUserSepara(loja: Int, numPedido: Int, userno: Int) {
    val sql = "/sqlSaci/marcaUserSepara.sql"
    script(sql) {
      addParameter("storeno", loja)
      addParameter("ordno", numPedido)
      addParameter("userno", userno)
    }
  }
  
  fun marcaUserEntregue(loja: Int, numPedido: Int, userno: Int) {
    val sql = "/sqlSaci/marcaUserEntregue.sql"
    script(sql) {
      addParameter("storeno", loja)
      addParameter("ordno", numPedido)
      addParameter("userno", userno)
    }
  }
  
  companion object {
    private val db = DB("saci")
    internal val driver = db.driver
    internal val url = db.url
    internal val username = db.username
    internal val password = db.password
    internal val test = db.test
    val ipServer =
      url.split("/")
        .getOrNull(2)
  }
}

val saci = QuerySaci()