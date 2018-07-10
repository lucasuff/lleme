from .Lista import Lista
from .Hash import Hash


class NoArvore:
	def __init__(self, lista=None, esq=None, dire=None, chave=None):
		self.lista = lista
		self.esq = esq
		self.dire = dire
		self.chave = chave

	def isEmpty():
		return (lista == None) or (lista.isEmpty)
		
	def insere(self, venda, chave):
		if self.lista == None or self.lista.isEmpty():
			self.lista = Lista(venda)
			self.chave = chave
		else:
			lista.insere(venda)
		
class Arvore:
	def __init__(self, raiz=None):
		self.raiz = raiz
		self.balanco = 0
		self.altura = -1

	def insere(self, venda, chave):
		if self.raiz == None:
			no = NoArvore()
			no.insere(venda, chave)
			self.raiz = no
			self.raiz.dire = Arvore()
			self.raiz.esq = Arvore()
		
		else:
			if chave < self.raiz.chave:
				self.raiz.esq.insere(venda, chave)
			elif chave > self.raiz.chave:
				self.raiz.dire.insere(venda, chave)
			else:
				self.raiz.lista.insere(venda)
		
		self.atualizaBalanco()
		
	def atualizaBalanco(self):
		bal = self.balance()
		if bal > 1:
			if self.raiz.esq.balance() > 0:
				self.rotacionaDir()
			else: 
				self.raiz.esq.rotacionaEsq()
				self.rotacionaDir()
		elif bal < -1:
			if self.raiz.dire.balance() < 0:
				self.rotacionaEsq()
			else: 
				self.raiz.dire.rotacionaDir()
				self.rotacionaEsq()

	def balance(self, altura=False):
		altura_esq, altura_dir = 0,0
		if self.raiz.esq.raiz:
			altura_esq = self.raiz.esq.balance(True)
		if self.raiz.dire.raiz:
			altura_dir = self.raiz.dire.balance(True)
		if altura: return 1 + max(altura_esq, altura_dir)

		return altura_esq - altura_dir

				
	def rotacionaDir(self):
		A = self.raiz
		B = self.raiz.esq.raiz
		T = B.dire.raiz 
		
		self.raiz = B 
		B.dire.raiz = A 
		A.esq.raiz = T 

	def rotacionaEsq(self):
		A = self.raiz
		B = self.raiz.dire.raiz
		T = B.esq.raiz

		self.raiz = B 
		B.esq.raiz = A 
		A.dire.raiz = T 
		
	def buscaIntervalo(self, minimum, maximum):
		vendas = []

		def busca_recursiva(no):
			if no is None:
				return None

			if no.chave > minimum:
				busca_recursiva(no.esq.raiz)
			if minimum <= no.chave <= maximum:
				vendas.append(no.lista.retornaLista())
			if no.chave < maximum:
				busca_recursiva(no.dire.raiz)

		busca_recursiva(self.raiz)
		return vendas
	
	def imprimeArvore(self, indent=0):
		print(("  " * indent + str(self.raiz.chave)))
		if self.raiz.esq.raiz:
			self.raiz.esq.imprimeArvore(indent + 2)
		if self.raiz.dire.raiz:
			self.raiz.dire.imprimeArvore(indent + 2)

		return
		
	def somaVendas(self, listas):
		total = 0
		for lista in listas:
			for venda in lista:
				total+=venda.total
		return total
