from .Venda import Venda
from .Hash import Hash
from .Lista import  Lista
from .Arvore import Arvore, NoArvore

arquivo = open('vendas.txt','r')
linhas = arquivo.readlines()
id = 1

arvoreFilial = Arvore()
arvoreData = Arvore()

for linha in linhas:
	venda_linha = linha.split(',')
	ano = venda_linha[1].split('_')[0]
	mes = venda_linha[1].split('_')[1]
	data = int(ano)*100 + int(mes)
	venda = Venda(id, int(venda_linha[0]), data, int(venda_linha[2]), int(venda_linha[3]))
	arvoreFilial.insere(venda, venda.filial)
	arvoreData.insere(venda, venda.data)
	id +=1


print("Arvore Filial:\n")
arvoreFilial.imprimeArvore()

print("Arvore Data:\n")
arvoreData.imprimeArvore()

print("Para arvore data entre 2011_03 e 2014_03:\n")
vendas_data = arvoreData.buscaIntervalo(201103,201403)
total = arvoreData.somaVendas(vendas_data)
print(('Total:', total))

print("Para arvore filiais entre filial 10 e 20:\n")
vendas_filial = arvoreFilial.buscaIntervalo(10,20)
total = arvoreFilial.somaVendas(vendas_filial)	
print(('Total:', total))

print("Para  arvore data entre 2011_03 e 2014_03 e arvore filiais entre filial 10 e 20:\n")
inter = Hash()
vendas_inter = inter.hashJoin(vendas_filial, vendas_data)
total = inter.somaVendas(vendas_inter)
print(('Total:', total))
