from .List import List

class Hash(object):
    def __init__(self, obj, key='filial'):
        self.build_hash(obj,key)

    def build_hash(self,obj, key):
        self.hash = []
        self.size = 99#obj if type(obj) is int else len(obj)
        for i in range(self.size):
            aux = List()
            self.hash.append(aux)


        if type(obj) is list:
            for node in obj:
                value = node.list.get_value(key)
                pos_hash = self.hash_function(value)
                self.add(pos_hash,node.list)


    def add(self, pos, node):
        if type(node) is List:
            current = node.head
            while current != None:
                self.hash[pos].add(current.value, origem='hash')
                current = current.__next__
#        self.hash[pos].add(node)


    def hash_function(self,key):
        if type(key) in [float, int]:
            return key % self.size
        else: return -1

    def get_position(self,key):
        pos = self.hash_function(key)
        return self.hash[pos]

    def find(self, value):
        hash_position = self.get_position(value)
        current = hash_position.head
        while current != None:
            if value == current.value.get_value('id'): return current
            current = current.__next__

    def join(self, nodes):
        intersection = []
        for node in nodes:
            current = node.list.head
            while current != None:
                id = current.value.get_value('id')
                result = self.find(id)

                if result is not None: intersection.append(result)
                current = current.__next__
        return intersection



    def print_hash(self):
        print("\nHASH\n")
        for i in self.hash:
            print(("\nValor",i.get_value()))
            current = i.head
            while current != None:
                print(("   valor:", current.value.get_value(), " | id:",current.value.get_value('id')))
                current = current.__next__

