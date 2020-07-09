SELECT E.storeno                                    AS loja,
       E.ordno                                      AS pedido,
       Cast(E.date AS DATE)                         AS dataPedido,
       N.nfno                                       AS nfno,
       N.nfse                                       AS nfse,
       N.issuedate                                  AS dataNota,
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
  LEFT JOIN  sqldados.eordrk AS R
	       ON (R.storeno = E.storeno AND R.ordno = E.ordno)
WHERE E.date >= :date
  AND E.storeno = :storeno
  AND N.tipo = 0
  AND R.remarks__480 LIKE 'RETIRA%'