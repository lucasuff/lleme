from classCliente import Cliente
from classEstatisticas import Estatisticas
from classHeapNo import Heap
from datetime import datetime
from time import sleep

class PF(object):
    def __init__(self):
        self.fila = Heap()
        self.estatisticas = Estatisticas()
        print('\nPolicia Federal aberta.')
        #sleep(2)

    def recepcionar(self, cliente):
        self.cliente = cliente
        self.horaChegada = datetime.now()
        self.fila.inserir(self.cliente, self.horaChegada)
        print(f'\n{self.cliente.nome} chegou.\n')
        self.fila.estadoHeap()

    def atender(self):
        if self.fila.isEmpty() == True:
            print('\n[33mAtendimentos encerrados.')

        else:
            print(f'\nQual proximo cliente?\n')
            self.fila.estadoHeap()
            proximo = self.fila.remover()
            proximo.horaAtendimento = datetime.now()
            print(f'\n{proximo.cliente.nome} em atendimento.')

            for i in range(0, proximo.cliente.nAssuntos):
                proximo.duracaoAtendimento += proximo.cliente.assuntos[i].tempomin

            print(f'Duracao atendimento: {proximo.duracaoAtendimento} minutos.')

            for i in range(0, proximo.cliente.nAssuntos):
                self.estatisticas.contarTempo(proximo.cliente.assuntos[i].tipo, proximo.cliente.assuntos[i].tempomin)
            sleep(proximo.duracaoAtendimento)
            self.encerrar(proximo)

    def encerrar(self, proximo):
        print(f'\nProvidencia(s) para {proximo.cliente.nome}:\n')
        for i in range(0, proximo.cliente.nAssuntos):
            print(f'{i+1}: {proximo.cliente.assuntos[i].providencia}')

    def gerarEstatisticas(self):
        print('\n[33mAtendimentos encerrados.\n')
        self.estatisticas.construirHash()
        self.estatisticas.imprimirEstatisticas()
