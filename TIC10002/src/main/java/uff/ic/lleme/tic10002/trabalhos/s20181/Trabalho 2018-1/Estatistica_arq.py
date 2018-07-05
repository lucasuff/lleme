'''
Rene Cruz Freire
renefreire@id.uff.br
25 de junho de 2018

Universidade Federal Fluminense
Instituto de Computação
Programa de Pós-Graduação em Computação
Estrutura de Dados e Algoritmos
'''

# Importando os métodos das classes "Dados" e "Hash" dentro do arquivo
# "Hash_arq.py"
from Hash_arq import Dados,Hash

# Classe que encapsula os métodos de contabilização estatística do atendimento
# do cliente
class Estatistica:

    # Inicialização das variáveis nativas da classe
    def __init__(self):
        self.hash = Hash()
        self.tempo = [0]*5
        self.cont = [0]*5
        self.key1 = Dados('Tipo 1')
        self.key2 = Dados('Tipo 2')
        self.key3 = Dados('Tipo 3')
        self.key4 = Dados('Tipo 4')
        self.key5 = Dados('Tipo 5')

    # Contagem do tempo de atendimento
    def contarTempo(self,tipo,tempo):
        if tipo == 'Tipo 1':
            self.tempo[0] = self.tempo[0] + tempo
            self.cont[0] = self.cont[0] + 1
        elif tipo == 'Tipo 2':
            self.tempo[1] = self.tempo[1] + tempo
            self.cont[1] = self.cont[1] + 1
        elif tipo == 'Tipo 3':
            self.tempo[2] = self.tempo[2] + tempo
            self.cont[2] = self.cont[2] + 1
        elif tipo == 'Tipo 4':
            self.tempo[3] = self.tempo[3] + tempo
            self.cont[3] = self.cont[3] + 1
        elif tipo == 'Tipo 5':
            self.tempo[4] = self.tempo[4] + tempo
            self.cont[4] = self.cont[4] + 1

    # Construção da tabela Hash
    def construirHash(self):
        for i in range(0,5):
            media = self.tempo[i]/self.cont[i]
            if i == 0:
                self.key1.mediaTempos = media
                self.hash.inserir(self.key1)
            elif i == 1:
                self.key2.mediaTempos = media
                self.hash.inserir(self.key2)
            elif i == 2:
                self.key3.mediaTempos = media
                self.hash.inserir(self.key3)
            elif i == 3:
                self.key4.mediaTempos = media
                self.hash.inserir(self.key4)
            elif i == 4:
                self.key5.mediaTempos = media
                self.hash.inserir(self.key5)

    # Busca de uma chave na tabela Hash
    def buscar(self,key):
        return self.hash.buscar(key)

    # Remoção de uma chave da tabela Hash
    def remover(self,key):
        return self.hash.remover(key)

    # Impressão das estatísticas
    def imprimirEstatisticas(self):
        self.hash.estadoHash()
        print('\nVerificacao de colisoes na tabela hash:\n')
        self.hash.verificarColisoes()
