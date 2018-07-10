class Venda:
    def __init__(self, id=None, filial=None, data=None, vendedor=None, total=None):
        self.id = id
        self.filial = filial
        self.data = data
        self.vendedor = vendedor
        self.total = total

    def getVenda(self):
        return [self.id, self.filial, self.data, self.vendedor, self.total]