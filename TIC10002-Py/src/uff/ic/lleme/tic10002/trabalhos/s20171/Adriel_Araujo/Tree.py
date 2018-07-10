from .Hash import Hash
from .List import List


class Tree:
    def __init__(self, list=None, left=None, right=None):
        if list is None:list = List()
        self.list = list
        self.left = left
        self.right = right


    def __add(self, node, sale, key):

        if node.list.is_empty():
            node.list.add(sale)
        else:
            if sale.get_value(key) < node.list.get_value(key):
                # print(sale.get_value(key) ,"<", node.list.get_value(key))
                if node.left is None: node.left = Tree()
                self.__add(node.left, sale, key)
            elif sale.get_value(key) > node.list.get_value(key):
                # print(sale.get_value(key), ">", node.list.get_value(key))
                if node.right is None: node.right = Tree()
                self.__add(node.right, sale, key)
            else:
                # print(sale.get_value(key), "=", node.list.get_value(key))
                node.list.add(sale)
        node.executeBalance()


    def add(self, sale, key):
        self.__add(self, sale, key)

    def executeBalance(self):
        bal = self.balance()
        if bal > 1:
            if self.left.balance() > 0: self.right_rotation()
            else: self.left_right_rotation()
        elif bal < -1:
            if self.right.balance() < 0:
                self.left_rotation()
            else: self.right_left_rotation()

    def balance(self, depth=False):
        depth_left, depth_right = 0, 0
        if self.left:
            depth_left = self.left.balance(depth=True)
        if self.right:
            depth_right = self.right.balance(depth=True)
        if depth: return 1 + max(depth_left, depth_right)

        return depth_left - depth_right

    def left_rotation(self):
        self.list, self.right.list = self.right.list, self.list
        old_left = self.left
        self.left, self.right = self.right, self.right.right
        self.left.left, self.left.right = old_left, self.left.left

    def right_rotation(self):
        self.list, self.left.list = self.left.list, self.list
        old_right = self.right
        self.left, self.right = self.left.left, self.left
        self.right.left, self.right.right = self.right.right, old_right

    def left_right_rotation(self):
        self.left.left_rotation()
        self.right_rotation()

    def right_left_rotation(self):
        self.right.right_rotation()
        self.left_rotation()

    def search_range(self, minimum, maximum):
        if type(minimum) == int and type(maximum) == int: key = 'filial'
        elif type(minimum) == str and type(maximum) == str: key = 'date'
        else: return "Invalid Range"
        nodes = []

        def recursive_search(node):
            if node is None:
                return None

            if node.list.get_value(key) > minimum:
                recursive_search(node.left)
            if minimum <= node.list.get_value(key) <= maximum:
                nodes.append(node)  # nodes.append([node.list.get_value(key), node])
            if node.list.get_value(key) < maximum:
                recursive_search(node.right)

        recursive_search(self)
        return nodes

    @staticmethod
    def sum_sales(filials=None, dates=None):
        total = 0
        if (filials != None and dates == None):
            for node in filials:
                total += node.list.get_value('total_vendido')
        elif filials is None and dates != None:
            for node in dates:
                total += node.list.get_value('total_vendido')
        else:
            hashFilials = Hash(filials, key='id')
            nodes_intersection = hashFilials.join(dates)
            for node in nodes_intersection:
                total += node.value.get_value('total_vendido')
        return total




    def print_tree(self, indent=0, key="filial"):
        print(("  " * indent + str(self.list.get_value(key))))
        if self.left:
            self.left.print_tree(indent + 2, key)
        if self.right:
            self.right.print_tree(indent + 2, key)

        return
