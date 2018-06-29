'''
Rene Cruz Freire
renefreire@id.uff.br
25 de junho de 2018

Universidade Federal Fluminense
Instituto de Computação
Programa de Pós-Graduação em Computação
Estrutura de Dados e Algoritmos
'''

# Importando função para cálculo de hora
from datetime import datetime

# Classe que contém os dados informados pelo cliente, e calcula a sua prioridade
# no atendimento
class Dados:

    # Método que recebe os dados do cliente, sua hora de chegada e hora em que é
    # atendido, e a sua prioridade de atendimento
    def __init__(self,cliente,horaChegada):
        self.cliente = cliente
        self.horaChegada = horaChegada
        self.horaAtendimento = None
        self.duracaoAtendimento = 0
        self.prioridade = self.calcularPrioridade()

    # Método que calcula a prioridade do cliente
    def calcularPrioridade(self):
        self.espera = (datetime.now() - self.horaChegada).total_seconds()
        prioridade = ((self.cliente.idade/65) + (self.espera/15) + (self.cliente.urgencia/10))/3
        return prioridade

# Classe que encapsula as operações básicas de uma lista de prioridades heap, além
# da função de ordenação heapsort
class Heap:
    
    # Inicialização das variáveis nativas da classe
    def __init__(self,heap=[]):
        self.heap = heap
        self.tamanho = len(heap)
        
    # Procedimento para aumento da prioridade de um nó, ou seja, "subir de nível"
    # na árvore binária
    def subirArvore(self,i):
        j = (i-1)//2
        if (j >= 0) and (self.heap[i] > self.heap[j]):
            self.heap[i],self.heap[j] = self.heap[j],self.heap[i]
            self.subirArvore(j)
            
    # Procedimento para redução da prioridade de um nó, ou seja, "descer de nível"
    # na árvore binária
    def descerArvore(self,i):
        j = 2*i + 1
        if j <= self.tamanho-1:
            if j < self.tamanho-1:
                if self.heap[j+1] > self.heap[j]:
                    j = j + 1
            if self.heap[i] < self.heap[j]:
                self.heap[i],self.heap[j] = self.heap[j],self.heap[i]
                self.descerArvore(j)
                
    # Procedimento que insere um novo elemento na posição n + 1 da lista e logo
    # depois o remaneja de modo que a lista continue a ser um heap
    def inserirNo(self,cliente,horaChegada):
        dados = Dados(cliente,horaChegada)
        self.heap.append(dados.prioridade)
        self.tamanho = self.tamanho + 1
        self.subirArvore(self.tamanho-1)
        
    # Procedimento para remover a chave de maior prioridade do heap, remanejando
    # para o seu lugar o último elemento da lista e depois reorganizando o heap
    def removerNo(self):
        raiz = self.heap[0]
        self.heap[0] = self.heap[self.tamanho-1]
        self.tamanho = self.tamanho - 1
        self.heap.pop()
        self.descerArvore(0)
        return raiz
    
    # Procedimento que ordena uma lista qualquer como um heap, partindo do princípio
    # de que se uma lista ordenada constitui um heap, um heap pode ser construído
    # simplesmente ordenando uma lista de prioridades
    def construirHeap(self,lista):
        i = len(lista)//2
        self.heap = lista[:]
        self.tamanho = len(lista)
        for j in range(i,-1,-1):
            self.descerArvore(j)

    # Procedimento que retorna o estado de um heap
    def estadoHeap(self):
        self.construirHeap(self.heap)
        print(f'Heap em {datetime.now().strftime("%Y-%m-%d %H:%M")}')
        print('-'*50)
        #for i in range(0,self.tamanho):
        #    print('{} --> {:.2f}'.format(self.heap[i].cliente.nome, self.heap[i].prioridade))
        #print('-'*50) 

    # Procedimento que indica que o heap está vazio
    def estahVazio(self):
        vazio = bool(0)
        if self.tamanho == 0:
            vazio = bool(1)
        return vazio
        
    # Procedimento que ordena uma lista qualquer em ordem crescente, primeiramente
    # reorganizando-a como um heap e depois efetuando a ordenação em si, usufruindo
    # das propriedades da lista heap para uma ordenação otimizada
    # Complexidade: O(n log n) -> melhor complexidade de um algoritmo de ordenação
    def heapSort(self,lista):
        self.construirHeap(lista)
        while self.tamanho > 1:
            self.heap[0],self.heap[self.tamanho-1] = self.heap[self.tamanho-1],self.heap[0]
            self.tamanho = self.tamanho - 1
            self.descerArvore(0)
