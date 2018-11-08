#language: pt
#author: Ícaro Silva
#automation: Ícaro Silva
#encoding: iso-8859-1
@SQLREPORTSMST @WEB @SPRINT1
Funcionalidade: Extrair de dados via SQL no MST
  - Narrativa:
  O analista com permissão irá realizar consultas SQL e extraí-las via Excel na tela do MST.
  
  - Fora do Escopo:
  Outras telas

  Contexto: Efetuar login no MST
    Dado que estou logado no MST com as credenciais
      | ID: | Password: |
      |     |           |

  @WHERE @SQL @MST @T96
  Cenario: Validar que nao e possivel clausula where
    Quando consultar o SQL na tela
      | Query:                            |
      | select table_name from all_tables |
    Entao devera aparecer uma mensagem de erro
      | Mensagem:                                                |
      | This query contains invalid functions, tables or fields. |

  @SELECT @SQL @MST @T97
  Cenario: Validar obrigatoriedade clausula select
    Quando consultar o SQL na tela
      | Query:                                                                             |
      | INSERT INTO VERTICAL_CUSTOMFIELDS VALUES (0, 'customField6', 'd25', 'transaction') |
    Entao devera aparecer uma mensagem de erro
      | Mensagem:                                                |
      | This query contains invalid functions, tables or fields. |

  @SENSITIVE @SQL @MST @T98
  Cenario: Validar que nao ha dados sensiveis
    Quando consultar o SQL na tela
      | Query:                                               |
      | select credit_card_number from transaction where 1=1 |
    Entao devera aparecer uma mensagem de erro
      | Mensagem:                                                |
      | This query contains invalid functions, tables or fields. |

  @INSERT @SQL @MST @T99
  Cenario: Validar que nao e possivel clausula insert
    Quando consultar o SQL na tela
      | Query:                                                                             |
      | INSERT INTO VERTICAL_CUSTOMFIELDS VALUES (0, 'customField6', 'd25', 'transaction') |
    Entao devera aparecer uma mensagem de erro
      | Mensagem:                                                |
      | This query contains invalid functions, tables or fields. |

  @DELETE @SQL @MST @T100
  Cenario: Validar que nao e possivel clausula delete
    Quando consultar o SQL na tela
      | Query: |
      | delete |
    Entao devera aparecer uma mensagem de erro
      | Mensagem:                                                |
      | This query contains invalid functions, tables or fields. |

  @ALTER @SQL @MST @T101
  Cenario: Validar que nao e possivel clausula alter
    Quando consultar o SQL na tela
      | Query:      |
      | alter table |
    Entao devera aparecer uma mensagem de erro
      | Mensagem:                                                |
      | This query contains invalid functions, tables or fields. |

  @UPDATE @SQL @MST @T102
  Cenario: Validar que nao e possivel clausula update
    Quando consultar o SQL na tela
      | Query: |
      | update |
    Entao devera aparecer uma mensagem de erro
      | Mensagem:                                                |
      | This query contains invalid functions, tables or fields. |

  @PROCEDURE @SQL @T103
  Cenario: Validar que nao e possivel procedure
    Quando consultar o SQL na tela
      | Query:                     |
      | select * from @MyTableType |
    Entao devera aparecer uma mensagem de erro
      | Mensagem:                                                |
      | This query contains invalid functions, tables or fields. |

  @FUNCTION @SQL @T104
  Cenario: Validar que nao e possivel function
    Quando consultar o SQL na tela
      | Query:                                  |
      | select myFunc=dbo.sc_RetornaSupplier(1) |
    Entao devera aparecer uma mensagem de erro
      | Mensagem:                                                |
      | This query contains invalid functions, tables or fields. |

  @CREATE @SQL @T115
  Cenario: Validar que nao e possivel create
    Quando consultar o SQL na tela
      | Query:               |
      | create table laranja |
    Entao devera aparecer uma mensagem de erro
      | Mensagem:                                                |
      | This query contains invalid functions, tables or fields. |

  @COMMENTS @SQL @T116
  Cenario: Validar que nao e possivel comentarios
    Quando consultar o SQL na tela
      | Query:                                    |
      | select table_name from all_tables --where |
    Entao devera aparecer uma mensagem de erro
      | Mensagem:                                                |
      | This query contains invalid functions, tables or fields. |

  @KROTATE @SQL @T117
  Cenario: Validar que nao e possivel krotate
    Quando consultar o SQL na tela
      | Query:                          |
      | select * from krotate where 1=1 |
    Entao devera aparecer uma mensagem de erro
      | Mensagem:                                                |
      | This query contains invalid functions, tables or fields. |
