from datetime import datetime

class No(object):

    def __init__(self, cliente, horaChegada):
        self.cliente = cliente
        self.horaChegada = horaChegada
        self.horaAtendimento = None
        self.duracaoAtendimento = 0
        self.prioridade = self.calcularPrioridade()

    def calcularPrioridade(self):
        self.espera = (datetime.now() - self.horaChegada).total_seconds()
        self.prioridade = ((self.cliente.idade / 65) + (self.espera / 15) + (self.cliente.urgencia / 10)) / 3

class Heap(object):

    def __init__(self):
        self.vetor = []
        self.n = len(self.vetor)

    def isEmpty(self):
        empty = bool(0)
        if self.n == 0:
            empty = bool(1)
        return empty

    def inserir(self, cliente, horaChegada):
        no = No(cliente, horaChegada)
        self.vetor.append(no)
        self.n = len(self.vetor)
        self.reconstruirHeap()

    def descer(self, i, fim):

        n = fim
        j = 2 * i + 1  # filho esquerda
        if j <= n - 1:
            if j < n - 1 and self.vetor[j + 1].prioridade > self.vetor[j].prioridade:  # se tem irmao e eh maior
                j = j + 1
            if self.vetor[j].prioridade > self.vetor[i].prioridade:
                aux = self.vetor[i]
                self.vetor[i] = self.vetor[j]
                self.vetor[j] = aux
                self.descer(j, n)

    def remover(self):

        if self.isEmpty() == True:
            pass
        else:
            self.reconstruirHeap()
            aux = self.vetor[0]
            self.vetor[0] = self.vetor[self.n - 1]
            self.vetor.pop(self.n - 1)
            self.n = len(self.vetor)
            self.descer(0, self.n)
            return aux

    def reconstruirHeap(self):

        for i in range(0, len(self.vetor)):
            self.vetor[i].calcularPrioridade()

        for i in range(int(self.n / 2), -1, -1):
            self.descer(i, self.n)

    def estadoHeap(self):
        self.reconstruirHeap()
        print(f'Heap em {datetime.now().strftime("%Y-%m-%d %H:%M")}')
        print('-'*50)
        for i in range(0, self.n):
            print('{} --> {:.2f}'.format(self.vetor[i].cliente.nome, self.vetor[i].prioridade))
        print('-'*50)