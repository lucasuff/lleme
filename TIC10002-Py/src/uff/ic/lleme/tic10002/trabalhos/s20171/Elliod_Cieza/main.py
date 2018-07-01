from collections import Counter
from random import randint
import sys


diretorio = "dados.txt"
op_valida = False
terminou_consulta = False
resposta_valida = False
codigo_filial_ini_valido = False
codigo_filial_fim_valido = False
codigo_vendedor_ini_valido = False
codigo_vendedor_fim_valido = False
data_ini_valida = False
data_fim_valida = False
soma_parcial = 0


class StructVendas(object):
    def __init__(self, filial, data, cod_vendedor, total_vendido):
        self.filial = filial
        self.data = data
        self.cod_vendedor = cod_vendedor
        self.total_vendido = total_vendido

    def imprime_venda(self):
        return "{:d} {} {:d} {:d}".format(self.filial, self.data, self.cod_vendedor, self.total_vendido)


class No(object):
    def __init__(self, conteudo):
        self.conteudo = conteudo
        self.esquerda = None
        self.direita = None
        self.pai = None


class ArvoreBinariaBalanceada(object):
    def __init__(self):
        self.raiz = None

    def inserir(self, conteudo):
        no_novo = No(conteudo)
        if self.raiz is None:
            self.raiz = no_novo
        else:
            no = self.raiz
            existe_no_a_esquerda = True
            while existe_no_a_esquerda is True:
                if conteudo.filial < no.conteudo.filial:
                    if no.esquerda is None:
                        no.esquerda = no_novo
                        no_novo.pai = no
                        existe_no_a_esquerda = False
                    no = no.esquerda
                else:
                    if no.direita is None:
                        no.direita = no_novo
                        no_novo.pai = no
                        break
                    no = no.direita
        self.rebalancear(no_novo)

    def balancear_direita(self, n1):
        n2 = n1.esquerda
        n2.pai = n1.pai
        if n2.pai is None:
            self.raiz = n2
        else:
            if n2.pai.esquerda is n1:
                n2.pai.esquerda = n2
            elif n2.pai.direita is n1:
                n2.pai.direita = n2
        n1.esquerda = n2.direita
        if n1.esquerda is not None:
            n1.esquerda.pai = n1
        n2.direita = n1
        n1.pai = n2
        calcula_altura(n1)
        calcula_altura(n2)

    def balancear_esquerda(self, n1):
        n2 = n1.direita
        n2.pai = n1.pai
        if n2.pai is None:
            self.raiz = n2
        else:
            if n2.pai.esquerda is n1:
                n2.pai.esquerda = n2
            elif n2.pai.direita is n1:
                n2.pai.direita = n2
        n1.direita = n2.esquerda
        if n1.direita is not None:
            n1.direita.pai = n1
        n2.esquerda = n1
        n1.pai = n2
        calcula_altura(n1)
        calcula_altura(n2)

    def rebalancear(self, no):
        while no is not None:
            calcula_altura(no)
            if altura(no.esquerda) >= 2 + altura(no.direita):
                if altura(no.esquerda.esquerda) >= altura(no.esquerda.direita):
                    self.balancear_direita(no)
                else:
                    self.balancear_esquerda(no.esquerda)
                    self.balancear_direita(no)
            elif altura(no.direita) >= 2 + altura(no.esquerda):
                if altura(no.direita.direita) >= altura(no.direita.esquerda):
                    self.balancear_esquerda(no)
                else:
                    self.balancear_direita(no.direita)
                    self.balancear_esquerda(no)
            no = no.pai

    def busca_em_ramificacao(self, no):
        global soma_parcial
        global op_busca
        global filial_codigo_ini
        global filial_codigo_fim
        global data_ini
        global data_fim
        global codigo_vendedor_ini
        global codigo_vendedor_fim

        if no is None:
            return None
        else:
            self.busca_em_ramificacao(no.esquerda)
            if op_busca == 1:
                if filial_codigo_ini <= no.conteudo.filial <= filial_codigo_fim:
                    soma_parcial += no.conteudo.total_vendido

            elif op_busca == 2:
                objeto_data = int(no.conteudo.data.split("/")[1]) + int(no.conteudo.data.split("/")[0])/100
                if data_ini <= objeto_data <= data_fim:
                    soma_parcial += no.conteudo.total_vendido

            elif op_busca == 3:
                objeto_data = int(no.conteudo.data.split("/")[1]) + int(no.conteudo.data.split("/")[0])/100
                if filial_codigo_ini <= no.conteudo.filial <= filial_codigo_fim and data_ini <= objeto_data <= data_fim:
                    soma_parcial += no.conteudo.total_vendido

            elif op_busca == 4:
                if filial_codigo_ini <= no.conteudo.filial <= filial_codigo_fim \
                        and codigo_vendedor_ini <= no.conteudo.cod_vendedor <= codigo_vendedor_fim:
                    soma_parcial += no.conteudo.total_vendido

            elif op_busca == 5:
                objeto_data = int(no.conteudo.data.split("/")[1]) + int(no.conteudo.data.split("/")[0])/100
                if filial_codigo_ini <= no.conteudo.filial <= filial_codigo_fim \
                        and codigo_vendedor_ini <= no.conteudo.cod_vendedor <= codigo_vendedor_fim \
                        and data_ini <= objeto_data <= data_fim:
                    soma_parcial += no.conteudo.total_vendido

            self.busca_em_ramificacao(no.direita)

    def buscar(self):
        global soma_parcial
        global filial_codigo_ini
        global filial_codigo_fim
        no = self.raiz

        if op_busca == 2:
            filial_codigo_ini = -sys.maxsize
            filial_codigo_fim = sys.maxsize

        if no.conteudo.filial > filial_codigo_fim:
            no = no.esquerda
            self.busca_em_ramificacao(no)

        elif no.conteudo.filial < filial_codigo_ini:
            no = no.direita
            self.busca_em_ramificacao(no)

        else:
            self.busca_em_ramificacao(no)

        return soma_parcial


def altura(no):
    if no is None:
        return -1
    else:
        return no.altura


def calcula_altura(no):
    no.altura = max(altura(no.esquerda), altura(no.direita)) + 1


def gera_arquivo(path):
    print("Gerando arquivo...")
    dados_venda = []
    total_vendido = []

    with open(path, "w") as arq:
        for item in range(1600):
            filial = randint(1, 5000)
            ano = randint(1950, 2017)
            mes = randint(1, 12)
            cod_vendedor = randint(1, 50)
            temp0 = [mes, ano]
            data = "/".join(map(str, temp0))
            temp = [filial, data, cod_vendedor]
            dados_venda.append(",".join(map(str, temp)))
            total_vendido.append(randint(0, 8000))

        dados_venda = list(set(dados_venda))

        if not ([k for k, v in list(Counter(dados_venda).items()) if v > 1]) == 0 and len(dados_venda) >= 1500:
            for t in range(1500):
                arq.write(dados_venda[t] + "," + str(total_vendido[t]) + "\n")
        else:
            raise ValueError("Quantidade de dados insuficiente, nao foi possivel gerar o arquivo")
    return None


def le_arquivo(path, arvore):
    print("Lendo arquivo...")
    with open(path, "r") as arq:
        for linha in arq:
            linha_atual = linha.split(",")
            venda = StructVendas(int(linha_atual[0]), linha_atual[1], int(linha_atual[2]), int(linha_atual[3]))
            arvore.inserir(venda)
    return None

# gera_arquivo(diretorio)
arvore_vendas = ArvoreBinariaBalanceada()
le_arquivo(diretorio, arvore_vendas)

while terminou_consulta is False:

    print("\n1) Total de vendas das filiais com codigos entre [X] e [Y]\n"
          "2) Total de vendas de todas filiais em um intervalo de datas [P] a [Q] \n"
          "3) Total de vendas das filiais com codigos entre [X] e [Y] em um intervalo de datas [P] a [Q]\n"
          "4) Total de vendas dos vendedores com codigos entre [A] e [B] nas filiais com codigos entre [X] e [Y]\n"
          "5) Total de vendas dos vendedores com codigos entre [A] e [B] nas filiais com codigos entre [X] e [Y]"
          " em um intervalo de datas [P] a [Q]\n"
          "6) Sair\n")

    while op_valida is False:
        try:
            input_busca = eval(input("Digite o numero da operacao de consulta que deseja realizar: "))
            if 1 <= input_busca <= 6 and type(input_busca) is int:
                op_busca = input_busca
                op_valida = True
            else:
                raise ValueError

        except (ValueError, NameError, SyntaxError):
            print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                  ": Opcao invalida, tente novamente.\n" + "\033[0m")

    if op_busca == 1:
        while codigo_filial_ini_valido is False:
            try:
                filial_codigo_ini = eval(input("\nDigite o codigo da filial X:  "))
                if type(filial_codigo_ini) is int:
                    codigo_filial_ini_valido = True
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, tente novamente." + "\033[0m")
            except (NameError, SyntaxError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, insira apenas numeros." + "\033[0m")

        while codigo_filial_fim_valido is False:
            try:
                filial_codigo_fim = eval(input("Digite o codigo da filial Y:  "))
                if type(filial_codigo_fim) is int:
                    codigo_filial_fim_valido = True
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, tente novamente.\n" + "\033[0m")
            except (NameError, SyntaxError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, insira apenas numeros.\n" + "\033[0m")

        totalVendas = arvore_vendas.buscar()
        print("\nTotal de vendas = ", totalVendas)
        resposta_valida = False
        op_valida = False
        codigo_filial_ini_valido = False
        codigo_filial_fim_valido = False
        soma_parcial = 0

    elif op_busca == 2:
        while data_ini_valida is False:
            try:
                data_ini = input("\nDigite a data de inicio P no formato [mes/ano]:  ")
                data_temp_ano = int(data_ini.split("/")[1])
                data_temp_mes = int(data_ini.split("/")[0])

                if 1 <= data_temp_mes <= 12:
                    data_ini_valida = True
                    data_ini = data_temp_ano + data_temp_mes/100
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Data invalida, tente novamente." + "\033[0m")
            except (NameError, SyntaxError, IndexError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Data invalida, insira apenas numeros." + "\033[0m")
        while data_fim_valida is False:
            try:
                data_fim = input("\nDigite a data de termino Q no formato [mes/ano]:  ")
                data_temp_ano = int(data_fim.split("/")[1])
                data_temp_mes = int(data_fim.split("/")[0])
                if 1 <= data_temp_mes <= 12:
                    data_fim_valida = True
                    data_fim = data_temp_ano + data_temp_mes/100
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Data invalida, tente novamente." + "\033[0m")
            except (NameError, SyntaxError, IndexError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Data invalida, insira apenas numeros." + "\033[0m")

        totalVendas = arvore_vendas.buscar()
        print("\nTotal de vendas = ", totalVendas)
        resposta_valida = False
        op_valida = False
        data_ini_valida = False
        data_fim_valida = False
        soma_parcial = 0

    elif op_busca == 3:
        while codigo_filial_ini_valido is False:
            try:
                filial_codigo_ini = eval(input("\nDigite o codigo da filial X:  "))
                if type(filial_codigo_ini) is int:
                    codigo_filial_ini_valido = True
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, tente novamente." + "\033[0m")
            except (NameError, SyntaxError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, insira apenas numeros." + "\033[0m")

        while codigo_filial_fim_valido is False:
            try:
                filial_codigo_fim = eval(input("Digite o codigo da filial Y:  "))
                if type(filial_codigo_fim) is int:
                    codigo_filial_fim_valido = True
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, tente novamente.\n" + "\033[0m")
            except (NameError, SyntaxError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, insira apenas numeros.\n" + "\033[0m")

        while data_ini_valida is False:
            try:
                data_ini = input("\nDigite a data de inicio P no formato [mes/ano]:  ")
                data_temp_ano = int(data_ini.split("/")[1])
                data_temp_mes = int(data_ini.split("/")[0])

                if 1 <= data_temp_mes <= 12:
                    data_ini_valida = True
                    data_ini = data_temp_ano + data_temp_mes/100
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Data invalida, tente novamente." + "\033[0m")
            except (NameError, SyntaxError, IndexError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Data invalida, insira apenas numeros." + "\033[0m")
        while data_fim_valida is False:
            try:
                data_fim = input("\nDigite a data de termino Q no formato [mes/ano]:  ")
                data_temp_ano = int(data_fim.split("/")[1])
                data_temp_mes = int(data_fim.split("/")[0])
                if 1 <= data_temp_mes <= 12:
                    data_fim_valida = True
                    data_fim = data_temp_ano + data_temp_mes/100
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Data invalida, tente novamente." + "\033[0m")
            except (NameError, SyntaxError, IndexError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Data invalida, insira apenas numeros." + "\033[0m")

        totalVendas = arvore_vendas.buscar()
        print("\nTotal de vendas = ", totalVendas)
        resposta_valida = False
        op_valida = False
        codigo_filial_ini_valido = False
        codigo_filial_fim_valido = False
        data_ini_valida = False
        data_fim_valida = False
        soma_parcial = 0

    elif op_busca == 4:
        while codigo_filial_ini_valido is False:
            try:
                filial_codigo_ini = eval(input("\nDigite o codigo da filial X:  "))
                if type(filial_codigo_ini) is int:
                    codigo_filial_ini_valido = True
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, tente novamente." + "\033[0m")
            except (NameError, SyntaxError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, insira apenas numeros." + "\033[0m")

        while codigo_filial_fim_valido is False:
            try:
                filial_codigo_fim = eval(input("Digite o codigo da filial Y:  "))
                if type(filial_codigo_fim) is int:
                    codigo_filial_fim_valido = True
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, tente novamente.\n" + "\033[0m")
            except (NameError, SyntaxError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, insira apenas numeros.\n" + "\033[0m")

        while codigo_vendedor_ini_valido is False:
            try:
                codigo_vendedor_ini = eval(input("\nDigite o codigo do vendedor A:  "))
                if type(codigo_vendedor_ini) is int:
                    codigo_vendedor_ini_valido = True
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, tente novamente." + "\033[0m")
            except (NameError, SyntaxError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, insira apenas numeros." + "\033[0m")

        while codigo_vendedor_fim_valido is False:
            try:
                codigo_vendedor_fim = eval(input("Digite o codigo do vendedor B:  "))
                if type(codigo_vendedor_fim) is int:
                    codigo_vendedor_fim_valido = True
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, tente novamente.\n" + "\033[0m")
            except (NameError, SyntaxError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, insira apenas numeros.\n" + "\033[0m")

        totalVendas = arvore_vendas.buscar()
        print("\nTotal de vendas = ", totalVendas)
        resposta_valida = False
        op_valida = False
        codigo_filial_ini_valido = False
        codigo_filial_fim_valido = False
        codigo_vendedor_ini_valido = False
        codigo_vendedor_fim_valido = False
        soma_parcial = 0

    elif op_busca == 5:
        while codigo_filial_ini_valido is False:
            try:
                filial_codigo_ini = eval(input("\nDigite o codigo da filial X:  "))
                if type(filial_codigo_ini) is int:
                    codigo_filial_ini_valido = True
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, tente novamente." + "\033[0m")
            except (NameError, SyntaxError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, insira apenas numeros." + "\033[0m")

        while codigo_filial_fim_valido is False:
            try:
                filial_codigo_fim = eval(input("Digite o codigo da filial Y:  "))
                if type(filial_codigo_fim) is int:
                    codigo_filial_fim_valido = True
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, tente novamente.\n" + "\033[0m")
            except (NameError, SyntaxError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, insira apenas numeros.\n" + "\033[0m")

        while codigo_vendedor_ini_valido is False:
            try:
                codigo_vendedor_ini = eval(input("\nDigite o codigo do vendedor A:  "))
                if type(codigo_vendedor_ini) is int:
                    codigo_vendedor_ini_valido = True
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, tente novamente." + "\033[0m")
            except (NameError, SyntaxError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, insira apenas numeros." + "\033[0m")

        while codigo_vendedor_fim_valido is False:
            try:
                codigo_vendedor_fim = eval(input("Digite o codigo do vendedor B:  "))
                if type(codigo_vendedor_fim) is int:
                    codigo_vendedor_fim_valido = True
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, tente novamente.\n" + "\033[0m")
            except (NameError, SyntaxError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Codigo invalido, insira apenas numeros.\n" + "\033[0m")

        while data_ini_valida is False:
            try:
                data_ini = input("\nDigite a data de inicio P no formato [mes/ano]:  ")
                data_temp_ano = int(data_ini.split("/")[1])
                data_temp_mes = int(data_ini.split("/")[0])

                if 1 <= data_temp_mes <= 12:
                    data_ini_valida = True
                    data_ini = data_temp_ano + data_temp_mes/100

                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Data invalida, tente novamente." + "\033[0m")
            except (NameError, SyntaxError, IndexError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Data invalida, insira apenas numeros." + "\033[0m")

        while data_fim_valida is False:
            try:
                data_fim = input("\nDigite a data de termino Q no formato [mes/ano]:  ")
                data_temp_ano = int(data_fim.split("/")[1])
                data_temp_mes = int(data_fim.split("/")[0])
                if 1 <= data_temp_mes <= 12:
                    data_fim_valida = True
                    data_fim = data_temp_ano + data_temp_mes/100
                else:
                    raise ValueError
            except ValueError:
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Data invalida, tente novamente." + "\033[0m")
            except (NameError, SyntaxError, IndexError):
                print("\033[95m" + "\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                      ": Data invalida, insira apenas numeros." + "\033[0m")

        totalVendas = arvore_vendas.buscar()
        print("\nTotal de vendas = ", totalVendas)
        resposta_valida = False
        op_valida = False
        codigo_filial_ini_valido = False
        codigo_filial_fim_valido = False
        codigo_vendedor_ini_valido = False
        codigo_vendedor_fim_valido = False
        data_ini_valida = False
        data_fim_valida = False
        soma_parcial = 0

    elif op_busca == 6:
        terminou_consulta = True
        resposta_valida = True

    while resposta_valida is False:
        try:
            resposta = input("\nDeseja realizar outra consulta? [Sim/Nao] ")
            if resposta.lower() == "sim" or resposta.lower() == "s":
                resposta_valida = True
            elif resposta.lower() == "nao" or resposta.lower() == "n":
                resposta_valida = True
                terminou_consulta = True
            else:
                raise ValueError

        except ValueError:
            print("\033[1m" + "\033[93m" + "ALERTA" + "\033[0m" + "\033[1m" + \
                  ": Resposta invalida, responda apenas [Sim ou Nao]." + "\033[0m")
