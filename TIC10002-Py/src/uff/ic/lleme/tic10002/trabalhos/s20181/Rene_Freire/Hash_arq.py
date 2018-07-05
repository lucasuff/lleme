'''
Rene Cruz Freire
renefreire@id.uff.br
25 de junho de 2018

Universidade Federal Fluminense
Instituto de Computação
Programa de Pós-Graduação em Computação
Estrutura de Dados e Algoritmos
'''


# Classe que contém os sobre o tipo de assunto e o tempo médio de atendimento
# dos clientes
class Dados:
    
    # Método que recebe o tipo de assunto abordado e o tempo médio de atendimento
    # do cliente
    def __init__(self, tipo):
        self.tipo = tipo
        self.mediaTempos = 0


# Classe que encapsula as operações básicas de uma tabela Hash
class Hash:

    # Variáveis nativas da classe
    def __init__(self):
        self.m = 10
        self.hash = [None] * 10
        self.cont = 0

    # Método contendo a função de endereçamento do Hash
    def funcaoHash(self, x):
        i = round(x.mediaTempos) % 7
        return i

    # Método que indica se o Hash está ou não completo
    def estahCheio(self):
        if self.cont == self.m:
            return True
        else:
            return False

    # Método que indica se o hash está ou não vazio
    def estahVazio(self):
        if self.cont == 0:
            return True
        else:
            return False

    # Método que insere uma nova chave no Hash
    def inserir(self, novo):
        if self.estahCheio() == True:
            print('Hash cheio. {novo} nao incluido')
        else:
            i = self.funcaoHash(novo)
            if self.hash[i] == None:
                self.hash[i] = novo
            else:
                i = self.m - 1
                while not self.hash[i] == None:
                    i = i - 1
                    if i == -1:
                        i = self.m - 1
                self.hash[i] = novo
            self.cont = self.cont + 1

    # Método que busca uma chave no Hash
    def buscar(self, key):
        if self.estahVazio() == True:
            print('Hash vazio')
        i = self.funcaoHash(key)
        if self.hash[i] == key:
            print('i = {i}')
            return i
        else:
            j = self.m - 1
            while not self.hash[i] == key:
                j = j - 1
                if j == -1:
                    j = None
                    break
            print('i = {i}, j = {j}')
            return j

    # Método que remove uma chave do Hash
    def remover(self, key):
        if self.estahVazio() == True:
            print('Hash vazio')
        else:
            i = self.buscar(key)
            chave = None
            if i != None:
                chave = self.hash[i]
                self.hash[i] = None
                self.m = self.m - 1
            return chave

    # Método que retorna o estado da tabela Hash
    def estadoHash(self):
        for i in range(0, self.m):
            if self.hash[i] == None:
                pass
            else:
                print('hash[{}] = Media dos assuntos {} -> {: .3f} minutos '.format(i, self.hash[i].tipo, self.hash[i].mediaTempos))

    # Método para verificação de colisões
    def verificarColisoes(self):
        for i in range(0, self.m):
            if self.hash[i] == None:
                pass
            else:
                self.buscar(self.hash[i])
