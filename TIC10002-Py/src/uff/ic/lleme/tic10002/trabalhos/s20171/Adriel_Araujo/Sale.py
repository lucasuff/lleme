class Sale(object):

    def __init__(self, id=None, filial=None, ano_mes=None, cod_vendedor=None, total_vendido=None):
        self.id = id
        self.filial = filial
        self.ano_mes = ano_mes
        self.cod_vendedor = cod_vendedor
        self.total_vendido = total_vendido

    def print_venda(self):
        print(("Dados da venda:\nID:", self.id,
              "\nFilial:", self.filial,
              "\nData:", self.ano_mes,
              "\nCod_Vendedor:", self.cod_vendedor,
              "\nTotal Vendido: R$", self.total_vendido))

    def get_sale_elements(self):
        return [self.id, self.filial, self.ano_mes, self.cod_vendedor, self.total_vendido]

    def get_filial(self):
        return int(self.filial)

    def get_value(self, value="filial"):
        if value=="filial": return int(self.filial)
        elif value=='date':return self.ano_mes
        elif value=='id': return int(self.id)
        elif value=='total_vendido': return float(self.total_vendido)
        else: return None


