from classHashNo import Hash, No

class Estatisticas(object):

    def __init__(self):
        self.h = Hash()
        self.tempos = [0]*5
        self.cont = [0]*5
        self.no1 = No('tipo1')
        self.no2 = No('tipo2')
        self.no3 = No('tipo3')
        self.no4 = No('tipo4')
        self.no5 = No('tipo5')

    def contarTempo(self, tipo, tempo):

        if tipo == 'tipo1':
            self.tempos[0] += tempo
            self.cont[0] += 1
        elif tipo == 'tipo2':
            self.tempos[1] += tempo
            self.cont[1] += 1
        elif tipo == 'tipo3':
            self.tempos[2] += tempo
            self.cont[2] += 1
        elif tipo == 'tipo4':
            self.tempos[3] += tempo
            self.cont[3] += 1
        elif tipo == 'tipo5':
            self.tempos[4] += tempo
            self.cont[4] += 1

    def construirHash(self):

        for i in range(0, 5):
            media = self.tempos[i]/self.cont[i]
            if i == 0:
                self.no1.mediaTempos = media
                self.h.inserir(self.no1)
            elif i == 1:
                self.no2.mediaTempos = media
                self.h.inserir(self.no2)
            elif i == 2:
                self.no3.mediaTempos = media
                self.h.inserir(self.no3)
            elif i == 3:
                self.no4.mediaTempos = media
                self.h.inserir(self.no4)
            elif i == 4:
                self.no5.mediaTempos = media
                self.h.inserir(self.no5)

    def buscar(self, no):
        return self.h.buscar(no)

    def remover(self, no):
        return self.h.remover(no)

    def imprimirEstatisticas(self):
        self.h.estadoHash()
        print('\nVerificacao de colisoes na tabela hash:\n')
        self.h.verificarColisoes()