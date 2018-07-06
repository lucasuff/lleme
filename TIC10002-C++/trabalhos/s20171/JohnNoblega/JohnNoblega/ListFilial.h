#pragma once


#include "Filial.h"
#include <iomanip>
#include "HashFilial.h"

class ListFilial {

    class NodeList {
    public:
        Filial* _data;
        NodeList* _next;

    public:

        NodeList() {
            _data = NULL;
            _next = NULL;
        }

        NodeList(Filial* data) {
            this->_data = data;
            this->_next = NULL;
        }
    };

private:
    NodeList* _first;
    int _size;
    int _total;

public:
    ListFilial();
    ~ListFilial();


    void insert(Filial*);

    inline int getTotal() {
        return _total;
    }

    inline int getSize() {
        return _size;
    }
    void interseccion(ListFilial&, ListFilial&);
};

ListFilial::ListFilial() {
    this->_first = NULL;
    this->_size = 0;
    this->_total = 0;
}

ListFilial::~ListFilial() {

}

void ListFilial::insert(Filial* data) {
    NodeList* newFilial = new NodeList(data);
    _total += data->getTotal_Vendido();
    _size += 1;
    if (_first == NULL)
        _first = newFilial;
    else {
        newFilial->_next = _first;
        _first = newFilial;
    }
}

void ListFilial::interseccion(ListFilial& list1, ListFilial& list2) {
    ListFilial result;
    NodeList* current = list1._first;
    HashFilial<long> table(list1._size + list2._size);
    for (; current != NULL; current = current->_next) {
        long x = reinterpret_cast<long> (current->_data);
        table.insert(x, current->_data);
    }

    current = list2._first;
    for (; current != NULL; current = current->_next) {
        long x = reinterpret_cast<long> (current->_data);
        if (table.search(x) != NULL)
            insert(current->_data);
    }
}
