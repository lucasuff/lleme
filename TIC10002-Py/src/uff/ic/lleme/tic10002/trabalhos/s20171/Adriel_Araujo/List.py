from . import Sale

class List:

    def __init__(self, Node=None):
        self.head = Node
        self.teal = Node
        self.len = 0

    def get_value(self,  value='filial'):
        if self.head != None:
            if value=="filial":  return int(self.head.value.filial)
            elif value=='date': return self.head.value.ano_mes
            elif value=='id': return int(self.head.value.id)
            elif value == 'total_vendido':
                current = self.head
                total = 0.0
                while current != None:
                    total += float(current.value.total_vendido)
                    current = current.__next__
                return total
        else:
            return None


    def is_empty(self):
        if self.head is None:
            return True
        return False

    def add(self, sale, origem='tree'):

        if self.head == None:
            self.head = ListNode(sale)
            self.teal = self.head
        else:
            new_node = ListNode(sale)
            new_node.previous = self.teal
            self.teal.next = new_node
            self.teal = new_node
        if origem is 'tree': self.len += 1

    def print_list(self):
        current = self.head
        while current!=None:
            print((current.value.get_sale_elements()))
            current = current.__next__



class ListNode:

    def __init__(self, Sale = None):
        self.value = Sale
        self.next, self.previous = None, None

