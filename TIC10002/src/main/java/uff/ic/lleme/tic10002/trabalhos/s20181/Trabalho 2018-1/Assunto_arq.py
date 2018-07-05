'''
Rene Cruz Freire
renefreire@id.uff.br
25 de junho de 2018

Universidade Federal Fluminense
Instituto de Computação
Programa de Pós-Graduação em Computação
Estrutura de Dados e Algoritmos
'''

# Importando função que calcula aleatoriamente um inteiro num intervalo entre
# dois inteiros quaisquer
from random import randint

# Classe que recebe as informações de assunto e atribui nível de urgência,tipo
# tempo mínimo de atendimento e providências para cada assunto abordado
class Assunto:
    
    # Método que recebe a descrição da ocorrência
    def __init__(self,descricao,tipo='inv',urgencia=-1,minTempo=-1,providencia='inv'):
        self.descricao = descricao
        self.tipo = tipo
        self.urgencia = urgencia
        self.minTempo = minTempo
        self.providencia = providencia
    
    # Método que atribui a urgência de cada ocorrência descrita, bem como seu tipo,
    # tempo mínimo de atendimento e providências a serem tomadas
    def tipoAssunto(self):
        if self.descricao == 'Suporte':
            self.tipo = 'Tipo 1'
            self.urgencia = 0
            self.minTempo = randint(1,3)
            opcoes = ['Dúvida solucionada','Dúvida fora do escopo da DEIC']
            self.providencia = opcoes[randint(0,1)]
        elif self.descricao == 'Consulta de registros de ocorrência':
            self.tipo = 'Tipo 1'
            self.urgencia = 1
            self.minTempo = randint(1,3)
            opcoes = ['Registro encontrado','Registro não encontrado']
            self.providencia = opcoes[randint(0,1)]
        elif self.descricao == 'Cancelamento de pré-registro':
            self.tipo = 'Tipo 2'
            self.urgencia = 2
            self.minTempo = randint(2,5)
            opcoes = ['Pré-registro cancelado com sucesso','Pré-registro não foi encontrado']
            self.providencia = opcoes[randint(0,1)]
        elif self.descricao == 'Agendamento de pré-registro':
            self.tipo = 'Tipo 2'
            self.urgencia = 3
            self.minTempo = randint(2,5)
            opcoes = ['Pré-registro agendado com sucesso','Cidadão encaminhado para delegacia especializada']
            self.providencia = opcoes[randint(0,1)]
        elif self.descricao == 'Reagendamento de pré-registro':
            self.tipo = 'Tipo 2'
            self.urgencia = 4
            self.minTempo = randint(2,5)
            opcoes = ['Pré-registro reagendado com sucesso','Pré-registro não encontrado']
            self.providencia = opcoes[randint(0,1)]
        elif self.descricao == 'Encontro de documentos':
            self.tipo = 'Tipo 3'
            self.urgencia = 5
            self.minTempo = randint(5,7)
            opcoes = ['Documento encontrado','Documento não encontrado']
            self.providencia = opcoes[randint(0,1)]
        elif self.descricao == 'Extravio (perda) de documentos':
            self.tipo = 'Tipo 3'
            self.urgencia = 6
            self.minTempo = randint(5,7)
            opcoes = ['Extravio registrado','Documentação insuficiente']
            self.providencia = opcoes[randint(0,1)]
        elif self.descricao == 'Extravio (perda) de celular':
            self.tipo = 'Tipo 3'
            self.urgencia = 7
            self.minTempo = randint(5,7)
            opcoes = ['Extravio registrado','Documentação insuficiente']
            self.providencia = opcoes[randint(0,1)]
        elif self.descricao == 'Ata Comunitária':
            self.tipo = 'Tipo 4'
            self.urgencia = 8
            self.minTempo = randint(7,10)
            opcoes = ['Ata emitida','Problemas de autenticação']
            self.providencia = opcoes[randint(0,1)]
        elif self.descricao == 'Comunicação de ocorrência':
            self.tipo = 'Tipo 5'
            self.urgencia = 9
            self.minTempo = randint(12,15)
            opcoes = ['Boletim de ocorrência registrado','Problemas de autenticação']
            self.providencia = opcoes[randint(0,1)]
        elif self.descricao == 'Denúncia do bairro':
            self.tipo = 'Tipo 5'
            self.urgencia = 10
            self.minTempo = randint(12,15)
            opcoes = ['Queixa registrada','Queixa retirada']
            self.providencia = opcoes[randint(0,1)]
        else:
            self.tipo = 'inv'
            self.urgencia = -1
            self.minTempo = -1
            self.providencia = 'inv'
