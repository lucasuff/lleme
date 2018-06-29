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
from Estatistica_arq import Estatistica
from Heap_arq import Heap

# Classe principal, que encapsula as funções recepcionar(), atender(), encerrar()
# e gerarEstatistica
class ServAtend:

    # Inicialização das variáveis
    def __init__(self):
        self.fila = Heap()
        self.estatistica = Estatistica
        print('\nInício do atendimento')

    # Função recepcionar()
    def recepcionar(self,cliente):
        self.cliente = cliente
        self.horaChegada = datetime.now()
        self.fila.inserirNo(self.cliente,self.horaChegada)
        print(f'\n{self.cliente.nome} chegou.\n')
        self.fila.estadoHeap()

    # Função atender()
    def atender(self):
        if self.fila.estahVazio() == True:
            print('\nAtendimentos encerrados.')
        else:
            print(f'\nProximo cliente?\n')
            self.fila.estadoHeap()
            prox = self.fila.removerNo()
            prox.horaAtendimento = datetime.now()
            print(f'\n{proximo.cliente.nome} em atendimento.')
            for i in range(0,prox.cliente.nAssuntos):
                prox.duracaoAtendimento = prox.duracaoAtendimento + prox.cliente.assuntos[i].minTempo
            print(f'Duracao atendimento: {proximo.duracaoAtendimento} minutos.')
            for i in range(0,prox.cliente.nAssuntos):
                self.estatistica.contarTempo((proximo.cliente.assuntos[i].tipo, proximo.cliente.assuntos[i].minTempo))
            sleep(prox.duracaoAtendimento)
            self.encerrar(prox)

    # Função encerrar()
    def encerrar(self,prox):
         print(f'\nProvidencia(s) para {proximo.cliente.nome}:\n')
         for i in range(0,prox.cliente.nAssuntos):
             print(f'{i+1}: {proximo.cliente.assuntos[i].providencia}')

    # Função gerarEstatistica()
    def gerarEstatistica(self):
        print('\nAtendimentos encerrados.\n')
        self.estatistica.construirHash()
        self.estatistica.imprimirEstatisticas()
