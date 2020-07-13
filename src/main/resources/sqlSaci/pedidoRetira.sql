SELECT E.storeno                                    AS loja,
       E.ordno                                      AS pedido,
       CAST(E.date AS DATE)                         AS dataPedido,
       CAST(N.nfno AS CHAR)                         AS nfno,
       N.nfse                                       AS nfse,
       CAST(N.issuedate AS DATE)                    AS dataNota,
       sec_to_time(N2.auxLong4)                     AS horaNota,
       N.grossamt / 100                             AS valor,
       E.s15                                        AS usuarioV,
       E.s14                                        AS usuarioS,
       E.s13                                        AS usuarioE,
       E.empno                                      AS vendedor,
       IFNULL(TRIM(MID(R.remarks__480, 1, 40)), '') AS obs
FROM sqldados.eord           AS E
  INNER JOIN sqldados.emp    AS V
	       ON V.no = E.empno
  INNER JOIN sqldados.nf     AS N
	       ON E.storeno = N.storeno AND E.nfno = N.nfno AND E.nfse = N.nfse
  LEFT JOIN  sqldados.nf2    AS N2
	       ON N.storeno = N2.storeno AND N.pdvno = N2.pdvno AND N.xano = N2.xano
  LEFT JOIN  sqldados.eordrk AS R
	       ON (R.storeno = E.storeno AND R.ordno = E.ordno)
WHERE N.issuedate >= :data
  AND (E.storeno = :storeno OR :storeno = 0)
  AND N.tipo = 0
  AND N.status <> 1
  AND R.remarks__480 LIKE 'RETIRA%'