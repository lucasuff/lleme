from datetime import datetime
from time import sleep

from uff.ic.lleme.tic10002.trabalhos.s20181.Carolina_Veiga.classEstatisticas import Estatisticas
from uff.ic.lleme.tic10002.trabalhos.s20181.Carolina_Veiga.classHeapNo import Heap
from uff.ic.lleme.tic10002.trabalhos.s20181.Carolina_Veiga.model.classCliente import Cliente


class PF(object):

    def __init__(self):
        self.fila = Heap()
        self.estatisticas = Estatisticas()
        print('\nPolicia Federal aberta.')
        # sleep(2)

    def recepcionar(self, cliente):
        self.cliente = cliente
        self.horaChegada = datetime.now()
        self.fila.inserir(self.cliente, self.horaChegada)
        print('\n{self.cliente.nome} chegou.\n')
        self.fila.estadoHeap()

    def atender(self):
        if self.fila.isEmpty() == True:
            print('\n[33mAtendimentos encerrados.')

        else:
            print('\nQual proximo cliente?\n')
            self.fila.estadoHeap()
            proximo = self.fila.remover()
            proximo.horaAtendimento = datetime.now()
            print('\n{proximo.cliente.nome} em atendimento.')

            for i in range(0, proximo.cliente.nAssuntos):
                proximo.duracaoAtendimento += proximo.cliente.assuntos[i].tempomin

            print('Duracao atendimento: {proximo.duracaoAtendimento} minutos.')

            for i in range(0, proximo.cliente.nAssuntos):
                self.estatisticas.contarTempo(proximo.cliente.assuntos[i].tipo, proximo.cliente.assuntos[i].tempomin)
            sleep(proximo.duracaoAtendimento)
            self.encerrar(proximo)

    def encerrar(self, proximo):
        print('\nProvidencia(s) para {proximo.cliente.nome}:\n')
        for i in range(0, proximo.cliente.nAssuntos):
            print('{i+1}: {proximo.cliente.assuntos[i].providencia}')

    def gerarEstatisticas(self):
        print('\n[33mAtendimentos encerrados.\n')
        self.estatisticas.construirHash()
        self.estatisticas.imprimirEstatisticas()
