
/* clase que contiene un apuntador de tipo venda.
 Es onde se definem los m�todos para manipular la lista
 de tipo venda. */

#include "List.h"
#include <iostream>

using namespace std;

List::List() {
    head = 0; // NULL
}

List::~List() {
    Venda *x;

    while (!IsEmpty()) {
        x = head;
        head = x->GetNext();
        delete x;
    }
}

void List::Insert(Venda *x) {
    x->SetNext(head);
    head = x;
}

bool List::IsEmpty() {
    return (head == 0);
}

void List::PrintList() {
    Venda *x = head;

    while (x != 0) {
        cout << " --> " << x->GetCodVendedor();
        x = x->GetNext();
    }

    cout << " --> null";
}

void List::PrintList2() {
    Venda *x = head;
    cout << "(";

    while (x != 0) {
        cout << " --> " << x->GetCodVendedor();
        x = x->GetNext();
    }

    cout << " --> null )";
}

float List::TotalVendas() {
    float total = 0.0f;
    Venda *x = head;

    while (x != 0) {
        total += x->GetTotalVendido();
        x = x->GetNext();
    }

    return total;
}
