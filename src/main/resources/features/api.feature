#language: pt
#author: Ícaro Silva
#automation: Ícaro Silva
#encoding: iso-8859-1
@GERADORDECPF @WEB @CPF
Funcionalidade: Validar a numeração de CPF.
  
  - Narrativa:
  	O usuário deverá consultar a numeração de cpf e o sistema irá avaliar a veracidade.

  Cenario: Validar numeração de cpf no site gerador de cpf
    Dado que esteja na pagina do gerador de cpf
    Quando informar o cpf para validacao
    Entao devera informar que esta correto
