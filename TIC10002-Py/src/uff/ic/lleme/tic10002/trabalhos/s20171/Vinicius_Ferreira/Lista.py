from . import Venda

class NoLista:
    def __init__(self, venda = None):
		self.venda = venda
		self.proximo = None
		self.anterior = None

class Lista:
	def __init__(self, venda = None):
		self.cabeca = None
		self.cauda = None
		self.insere(venda)


	def isEmpty(self):
		return (self.cabeca == None)

	def insere(self, venda):
		no = NoLista(venda)
		if self.isEmpty():
			self.cabeca = no
			self.cauda = no
		else:
			no.anterior = self.cauda
			self.cauda.proximo = no
			self.cauda = no

	def retornaLista(self):
		lista = []
		no = self.cabeca
		while no != self.cauda:
			lista.append(no.venda)
			no = no.proximo
		if self.cauda != self.cabeca:
			lista.append(self.cauda.venda)
		return lista
