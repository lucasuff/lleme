
from uff.ic.lleme.tic10002.trabalhos.s20181.Carolina_Veiga.classCliente import Cliente
from uff.ic.lleme.tic10002.trabalhos.s20181.Carolina_Veiga.classPF_sc import PF

cliente1 = Cliente(124, 'Carolina', 28, ['informacoes', 'cancelar agendamento', 'requerimento de passaporte eletronico'])
cliente2 = Cliente(12890937763, 'Clara', 27, ['emissao de GRU', 'emissao de certidao de antecedentes criminais'])
cliente3 = Cliente(4565767, 'Alice', 55, ['credenciamento de instrutores de armamento e tiro', 'comunicacao de ocorrencia com documentos de viagem'])
cliente4 = Cliente(234654, 'Soterio', 59, ['comunicacao de ocorrencia com arma de fogo'])
cliente5 = Cliente(6565674, 'Ayla', 20, ['informacoes', 'credenciamento de instrutores de armamento e tiro'])
cliente6 = Cliente(685475, 'Laura', 30, ['Criticas', 'consultar agendamento', 'reagendar fotografia', 'requerimento de passaporte para estrangeiro'])
cliente7 = Cliente(685475, 'Lara', 29, ['porte de arma de fogo'])

policia_federal = PF()

# Carolina chega
policia_federal.recepcionar(cliente1)

# sleep(1)
# Clara chega
policia_federal.recepcionar(cliente2)

# sleep(1)
# Alice chega
policia_federal.recepcionar(cliente3)

# sleep(1)
# Soterio chega
policia_federal.recepcionar(cliente4)

# sleep(1)
# Proximo eh atendido
policia_federal.atender()

# Ayla chega
policia_federal.recepcionar(cliente5)

# sleep(1)
# Proximo eh atendido
policia_federal.atender()

# Proximo eh atendido
policia_federal.atender()

# Proximo eh atendido
policia_federal.atender()

# Laura chega
policia_federal.recepcionar(cliente6)

# sleep(1)
# Proximo eh atendido
policia_federal.atender()

# Lara chega
policia_federal.recepcionar(cliente7)

# Proximo eh atendido
policia_federal.atender()

# Proximo eh atendido
policia_federal.atender()

# Estatisticas
policia_federal.gerarEstatisticas()
