
/* A clase OrderedList cont�m os m�todos para criar a lista ordenada,
   calcular os totais de vendas, e print as estruturas */

#include "OrderedList.h"
#include <iostream>
#include <climits>

using namespace std;

OrderedList::OrderedList() {
    head = 0; // NULL
}

OrderedList::~OrderedList() {
    Node *x;

    while (head != 0) {
        x = head;
        head = x->GetNext();
        delete x;
    }
}

Node* OrderedList::Search(int k) {
    Node *x = head;

    while (x != 0 && x->GetValue() <= k) {
        if (x->GetValue() == k) {
            return x;
        } else {
            x = x->GetNext();
        }
    }

    return 0;
}

bool OrderedList::ExistValue(int k) {
    Node *x = head;

    while (x != 0 && x->GetValue() <= k) {
        if (x->GetValue() == k) {
            return true;
        } else {
            x = x->GetNext();
        }
    }

    return false;
}

void OrderedList::OrderedInsert(Node *x) {
    //inser��on dos nos en ordem crescente
    Node *a = head;
    Node *prev = 0;

    while (a != 0 && x->GetValue() > a->GetValue()) {
        prev = a;
        a = a->GetNext();
    }

    x->SetNext(a);

    if (prev != 0) {
        prev->SetNext(x);
    } else {
        head = x;
    }
}

bool OrderedList::IsEmpty() {
    return (head == 0);
}

void OrderedList::PrintList() {
    Node *x = head;

    while (x != 0) {
        cout << x->GetValue();
        x->PrintList();
        cout << "\n|\n";
        x = x->GetNext();
    }

    cout << "null\n";
}

void OrderedList::PrintList2() {
    Node *x = head;

    while (x != 0) {
        cout << x->GetValue();
        x->PrintList2();
        cout << "\n|\n";
        x = x->GetNext();
    }

    cout << "null\n";
}

void OrderedList::InsertVenda_1(Venda *V) {
    Node *N = Search(V->GetFilial());

    if (N != 0) {
        N->InsertVenda(V);
    }
}

void OrderedList::InsertVenda_2(Venda *V) {
    Node *N = Search(V->GetFilial()), *N2;

    if (N != 0) {
        N2 = N->Search(V->GetDate());

        if (N2 != 0) {
            N2->InsertVenda(V);
        }
    }
}

void OrderedList::InsertVenda_3(Venda *V) {
    Node *N = Search(V->GetDate());

    if (N != 0) {
        N->InsertVenda(V);
    }
}

float OrderedList::TotalVendas(int lim_inf, int lim_sup) {
    float total = 0.0f;
    Node *N = head;

    while (N != 0) {
        if (N->GetValue() >= lim_inf) {
            if (N->GetValue() <= lim_sup) {
                total += N->TotalVendas();
            } else {
                return total;
            }
        }

        N = N->GetNext();
    }

    return total;
}

float OrderedList::TotalVendas(int lim_inf, int lim_sup, int lim_inf2, int lim_sup2) {
    float total = 0.0f;
    Node *N = head, *N2;

    while (N != 0) {
        if (N->GetValue() >= lim_inf) {
            if (N->GetValue() <= lim_sup) {
                N2 = N->GetHeadDatas();

                while (N2 != 0) {
                    if (N2->GetValue() >= lim_inf2) {
                        if (N2->GetValue() <= lim_sup2) {
                            total += N2->TotalVendas();
                        } else {
                            break;
                        }
                    }

                    N2 = N2->GetNext();
                }
            } else {
                return total;
            }
        }

        N = N->GetNext();
    }

    return total;
}
