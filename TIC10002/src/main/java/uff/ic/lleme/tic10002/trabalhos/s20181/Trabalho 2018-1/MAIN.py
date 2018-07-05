'''
Rene Cruz Freire
renefreire@id.uff.br
25 de junho de 2018

Universidade Federal Fluminense
Instituto de Computação
Programa de Pós-Graduação em Computação
Estrutura de Dados e Algoritmos
'''

# Importando as classes especiais implementadas para este trabalho, além de
# funções nativas que serão utilizadas
from datetime import datetime
from time import sleep
from Cliente_arq import Cliente
from ServAtend_arq import ServAtend

# Clientes a ser atendidos na simulação
rafaela = Cliente('Rafaela',20,62946729425,['Extravio (perda) de celular','Comunicação de ocorrência'])
adriena = Cliente('Adriena',27,39283728373,['Comunicação de ocorrência','Denúncia do bairro'])
eliza = Cliente('Eliza',29,29382994921,['Consulta de registros de ocorrência','Cancelamento de pré-registro','Agendamento de pré-registro'])
rene = Cliente('Rene',32,11788491718,['Extravio (perda) de documentos','Encontro de documentos'])
pablo = Cliente('Pablo',33,34932920843,['Agendamento de pré-registro','Reagendamento de pré-registro'])
conceicao = Cliente('Conceicao',60,72938273661,['Ata Comunitária'])
valdemir = Cliente('Valdemir',65,48283872982,['Suporte','Agendamento de pré-registro'])

# Chamando a classe do serviço de atendimento
deic = ServAtend()

# Simulação do atendimento
deic.recepcionar(rafaela)
deic.recepcionar(eliza)
deic.recepcionar(adriena)
deic.recepcionar(rene)

deic.atender()

deic.recepcionar(pablo)

deic.atender()
deic.atender()
deic.atender()

deic.recepcionar(conceicao)

deic.atender()

deic.recepcionar(valdemir)

deic.atender()
deic.atender()

deic.gerarEstatistica()
