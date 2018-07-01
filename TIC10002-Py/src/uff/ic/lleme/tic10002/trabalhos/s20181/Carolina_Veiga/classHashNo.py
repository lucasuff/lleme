class No(object):
    def __init__(self, tipo):
        self.tipo = tipo
        self.mediaTempos = 0

class Hash(object):

    def __init__(self):
        self.n = 10
        self.hash = [None] * 10
        self.cont = 0

    def funcaoHash(self, x):
        i = round(x.mediaTempos) % 7
        return i

    def isFull(self):
        if self.cont == self.n:
            return True
        else:
            return False

    def isEmpty(self):
        if self.cont == 0:
            return True
        else:
            return False

    def inserir(self, novo):
        if self.isFull() == True:
            print(f'Hash cheio. {novo} nao incluido')
            pass
        else:
            i = self.funcaoHash(novo)
            if self.hash[i] == None:
                self.hash[i] = novo
            else:
                i = self.n - 1
                while not self.hash[i] == None:
                    i -= 1
                    if i == -1:
                        i = self.n - 1
                self.hash[i] = novo

            self.cont += 1

    def buscar(self, no):
        if self.isEmpty() == True:
            print('Hash vazio.')
            pass
        i = self.funcaoHash(no)
        if self.hash[i] == no:
            print(f'i = {i}')
            return i
        else:
            j = self.n - 1

            while not self.hash[j] == no:
                j -= 1
                if j == -1:
                    j = None
                    break
            print(f'i = {i}, j = {j}')
            return j

    def remover(self, no):
        if self.isEmpty() == True:
            print('Hash vazio.')
            pass
        else:
            i = self.buscar(no)
            aux = None
            if i != None:
                aux = self.hash[i]
                self.hash[i] = None
                self.n = self.n - 1
            return aux

    def estadoHash(self):

        for i in range(0, self.n):
            if self.hash[i] == None:
                pass
            else:
                print('hash[{}] = Media dos assuntos {} -> {: .3f} minutos '.format(i, self.hash[i].tipo, self.hash[i].mediaTempos))

    def verificarColisoes(self):
        for i in range(0, self.n):
            if self.hash[i] == None:
                pass
            else:
                self.buscar(self.hash[i])
