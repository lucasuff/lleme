'''
Rene Cruz Freire
renefreire@id.uff.br
25 de junho de 2018

Universidade Federal Fluminense
Instituto de Computação
Programa de Pós-Graduação em Computação
Estrutura de Dados e Algoritmos
'''

# Importando os métodos da classe "Assunto" dentro do arquivo "Assunto_arq.py"
from  uff.ic.lleme.tic10002.trabalhos.s20181.Rene_Freire.model.Assunto_arq import Assunto


# Classe que recebe os dados informados pelo cliente, o assunto a ser tratado
# e atribui a sua urgência
class Cliente:

    # Método que recebe os dados do cliente: nome, idade, CPF, assunto a ser
    # tratado e urgência do assunto em questão
    def __init__(self, nome, idade, cpf, listaDescricoes):
        self.nome = nome
        self.idade = idade
        self.cpf = cpf
        self.assuntos = self.listaAssuntos(listaDescricoes)
        self.nAssuntos = len(listaDescricoes)
        self.urgencia = self.urgenciaMedia(listaDescricoes)

    # Método que gera uma lista contendo tipo, urgência, tempo mínimo de atendi-
    # mento e providências a serem tomadas a partir de uma lista contendo as
    # descrições de cada assunto
    def listaAssuntos(self, listaDescricoes):
        assuntos = []
        nAssuntos = len(listaDescricoes)
        for n in range(0, nAssuntos):
            a = Assunto(listaDescricoes[n])
            assuntos.append(a.tipoAssunto())
        return assuntos

    # Método que gera uma lista contendo as urgências médias de cada assunto
    # abordado
    def urgenciaMedia(self, listaDescricoes):
        urgencia = []
        nAssuntos = len(listaDescricoes)
        for n in range(0, nAssuntos):
            a = Assunto(listaDescricoes[n])
            urgencia.append(a.urgencia)
        urgenciaMed = sum(urgencia) / len(urgencia)
        return urgenciaMed
