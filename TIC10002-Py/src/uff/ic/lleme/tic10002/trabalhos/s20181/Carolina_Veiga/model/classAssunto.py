from random import randint


class Assunto(object):

    def __init__(self, qualassunto):

        self.titulo = qualassunto

        if self.titulo == 'informacoes' or self.titulo == 'Sugestoes' or self.titulo == 'Criticas':
            self.tipo = 'tipo1'
            self.urgencia = 0
            self.tempomin = randint(1, 3)
            if self.titulo == 'informacoes':
                self.providencia = 'Informacoes concedidas.'
            else:
                self.providencia = '{self.titulo} registradas.'

        elif self.titulo == 'cancelar agendamento' or self.titulo == 'consultar agendamento':
            self.tipo = 'tipo2'
            self.urgencia = 1
            self.tempomin = randint(2, 5)
            if self.titulo == 'cancelar agendamento':
                self.providencia = 'Agendamento cancelado com sucesso.'
            else:
                self.providencia = 'Agendamento consultado com sucesso.'

        elif self.titulo == 'agendar fotografia' or self.titulo == 'reagendar fotografia':
            self.tipo = 'tipo2'
            self.urgencia = 2
            self.tempomin = randint(2, 5)
            if self.titulo == 'agendar fotografia':
                self.providencia = 'Fotografia agendada com sucesso.'
            else:
                self.providencia = 'Fotografia reagendada com sucesso.'

        elif self.titulo == 'emissao de GRU':
            self.tipo = 'tipo2'
            self.urgencia = 3
            self.tempomin = randint(2, 5)
            self.opcoesprovidencias = ['GRU emitido com sucesso.', 'Problemas de autenticacao.']
            self.providencia = self.opcoesprovidencias[randint(0, 1)]

        elif self.titulo == 'requerimento de passaporte eletronico':
            self.tipo = 'tipo3'
            self.urgencia = 3
            self.tempomin = randint(8, 12)
            self.opcoesprovidencias = ['Passaporte eletronico requerido.', 'Falta de documentacao.']
            self.providencia = self.opcoesprovidencias[randint(0, 1)]

        elif self.titulo == 'requerimento de passaporte' or self.titulo == 'requerimento de passaporte para estrangeiro':
            self.tipo = 'tipo3'
            self.urgencia = 4
            self.tempomin = randint(8, 12)
            self.opcoesprovidencias = ['Passaporte requerido.', 'Falta de documentacao.']
            self.providencia = self.opcoesprovidencias[randint(0, 1)]

        elif self.titulo == 'requerimento passaporte de emergencia':
            self.tipo = 'tipo3'
            self.urgencia = 6
            self.tempomin = randint(8, 12)
            self.opcoesprovidencias = ['Passaporte de emergencia requerido.', 'Problemas de autenticacao.']
            self.providencia = self.opcoesprovidencias[randint(0, 1)]

        elif self.titulo == 'comunicacao de ocorrencia com documentos de viagem':
            self.tipo = 'tipo3'
            self.urgencia = 10
            self.tempomin = randint(8, 12)
            self.providencia = 'Registro de ocorrencia realizado.'

        elif self.titulo == 'aquisicao de arma de fogo':
            self.tipo = 'tipo4'
            self.urgencia = 3
            self.tempomin = randint(12, 15)
            self.opcoesprovidencias = ['Licenca de arma de fogo concedida.', 'Cliente reprovado na avaliacao psicotecnica.']
            self.providencia = self.opcoesprovidencias[randint(0, 1)]

        elif self.titulo == 'registro de arma de fogo' or self.titulo == 'renovacao de registro de arma de fogo':
            self.tipo = 'tipo4'
            self.urgencia = 4
            self.tempomin = randint(12, 15)
            self.opcoesprovidencias = ['Falta de Documentacao', 'Registro realizado com sucesso', 'Cliente reprovado na avaliacao psicotecnica.']
            self.providencia = self.opcoesprovidencias[randint(0, 2)]

        elif self.titulo == 'porte de arma de fogo':
            self.tipo = 'tipo4'
            self.urgencia = 6
            self.tempomin = randint(12, 15)
            self.opcoesprovidencias = ['Falta de Documentacao', 'Porte de arma concedido.', 'Cliente reprovado na avaliacao psicotecnica.']
            self.providencia = self.opcoesprovidencias[randint(0, 2)]

        elif self.titulo == 'transferencia de arma de fogo':
            self.tipo = 'tipo4'
            self.urgencia = 7
            self.tempomin = randint(12, 15)
            self.providencias = ['Falta de Documentacao', 'Registro realizado com sucesso', 'Cliente reprovado na avaliacao psicotecnica.']
            self.providencia = self.providencias[randint(0, 2)]

        elif self.titulo == 'comunicacao de ocorrencia com arma de fogo':
            self.tipo = 'tipo4'
            self.urgencia = 10
            self.tempomin = randint(12, 15)
            self.providencia = 'Registro de ocorrencia realizado com sucesso'

        elif self.titulo == 'emissao de certidao de antecedentes criminais':
            self.tipo = 'tipo5'
            self.urgencia = 2
            self.tempomin = randint(3, 8)
            self.providencia = 'Certidao de antecedentes criminais emitida com sucesso.'

        elif self.titulo == 'credenciamento de instrutores de armamento e tiro':
            self.tipo = 'tipo5'
            self.urgencia = 3
            self.tempomin = randint(3, 8)
            self.providencia = 'Credenciamento realizado com sucesso.'
