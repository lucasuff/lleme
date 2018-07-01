class Hash(object):
	
	def __init__(self, venda=None, chave=None):
		self.tamanho = 11
		self.hash = self.tamanho*[None]
		if venda is not None and chave is not None:
			self.insere(venda, chave)

	def insere(self, venda, chave):
		pos = self.funcaoHash(chave)
		if self.hash[pos] == None:
			self.hash[pos] = []
		self.hash[pos].append([venda,chave])


	def funcaoHash(self, chave):
		return chave % self.tamanho

	def chaveExiste(self, chave):
		pos = self.funcaoHash(chave)
		if self.hash == None or self.hash[pos] == None:
			return False
		for i in self.hash[pos]:
			try:
				if i[1] == chave:
					return True
			except IndexError:
				return False

	def hashJoin(self, lista1, lista2):
		inter = []
		for lista in lista1:
			for venda in lista:
				self.insere(venda, venda.filial)
		for lista in lista2:
			for venda in lista:
				if self.chaveExiste(venda.filial):
					inter.append(venda)
		return inter
	
	def somaVendas(self, lista):
		total = 0
		for item in lista:
			total += item.total
		return total
