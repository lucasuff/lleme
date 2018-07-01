from  uff.ic.lleme.tic10002.trabalhos.s20181.Carolina_Veiga.classAssunto import Assunto


class Cliente(object):

    def __init__(self, cpf, nome, idade, vetorAssuntos):
        self.cpf = cpf
        self.nome = nome
        self.idade = idade
        self.assuntos = self.gerarAssuntos(vetorAssuntos)
        self.nAssuntos = len(self.assuntos)
        self.urgencia = self.calculaUrgencia(self.assuntos)

    def gerarAssuntos(self, vetorAssuntos):
        assuntos = []

        for i in range(0, len(vetorAssuntos)):
            assuntos.append(Assunto(vetorAssuntos[i]))
        return assuntos

    def calculaUrgencia(self, assuntos):
        assuntos = assuntos

        urgenciavetor = []
        for i in range(0, len(assuntos)):
            urgenciavetor.append(assuntos[i].urgencia)

        urgenciaMedia = sum(urgenciavetor) / len(urgenciavetor)

        return urgenciaMedia
    
    