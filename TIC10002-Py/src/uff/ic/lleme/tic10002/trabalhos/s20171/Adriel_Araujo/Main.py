# coding: utf-8

from .Sale import Sale
from .Tree import Tree

class Main:

    #Padrão de entrada da data: YYYY_MM (Ex: 2017_02)
    def __init__(self):
        self.treeFilial = Tree()
        self.treeDate = Tree()

        self.load_file('vendas.txt')
        self.search(min_filial=10, max_filial=20, min_date='2017_01', max_date='2017_06')

    def load_file(self, path):
        file = open(path,'r')
        lines = file.readlines()

        id = 1
        for line in lines:
            line_list = line.split(',')
            sale = Sale(id,line_list[0],line_list[1],line_list[2],line_list[3])
            self.treeFilial.add(sale, key='filial')
            self.treeDate.add(sale, key="date")
            id +=1

    def search(self, min_filial=None, max_filial=None, min_date=None, max_date=None):
        if min_date is None and max_date is None: flag = 1
        elif min_filial is None and max_filial is None: flag =2
        else: flag = 3

        nodes_date, nodes_filial = None,None

        if flag != 1:
            print("Árvore Data")
            self.treeDate.print_tree(key='date')
            nodes_date = self.treeDate.search_range(min_date,max_date)
            print(('Total de vendas de todas as filias com data entre {} e {}:'.format(min_date, max_date), self.treeFilial.sum_sales(dates=nodes_date)))

        if flag != 2:
            print("\n\nArvore Filial")
            self.treeFilial.print_tree(key='filial')
            nodes_filial = self.treeFilial.search_range(min_filial, max_filial)
            print(('Total vendas das filiais {} e {}:'.format(min_filial, max_filial), self.treeFilial.sum_sales(filials=nodes_filial)))

        if flag ==3:
            sum_sales = Tree.sum_sales(nodes_filial, nodes_date)
            print(("\nTotal de vendas das filiais {} e {} entre os meses de {} e {}:".format(min_filial,max_filial, min_date, max_date), sum_sales))




if __name__ == "__main__":
    program = Main()
