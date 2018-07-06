
/* A clase Node se utiliza para declarar os m�todos y atributos relacionados
   com os nos das listas ordenadas */

#include "Node.h"
#include <iostream>

Node::Node() {
    value = 0;
    next = 0; // null
    head_datas = 0; // null
    vendas = new List();
}

Node::Node(int V) {
    value = V;
    next = 0; // null
    head_datas = 0; // null
    vendas = new List();
}

Node::~Node() {
    delete vendas;
    Node *x;

    while (!IsEmpty()) {
        x = head_datas;
        head_datas = x->GetNext();
        delete x;
    }
}

void Node::InsertVenda(Venda *x) {
    vendas->Insert(x);
}

float Node::TotalVendas() {
    return vendas->TotalVendas();
}

int Node::GetValue() {
    return value;
}

Node* Node::GetNext() {
    return next;
}

Node* Node::GetHeadDatas() {
    return head_datas;
}

void Node::SetNext(Node *x) {
    next = x;
}

Node* Node::Search(int k) {
    Node *x = head_datas;

    while (x != 0 && x->GetValue() <= k) {
        if (x->GetValue() == k) {
            return x;
        } else {
            x = x->GetNext();
        }
    }

    return 0;
}

bool Node::ExistValue(int k) {
    Node *x = head_datas;

    while (x != 0 && x->GetValue() <= k) {
        if (x->GetValue() == k) {
            return true;
        } else {
            x = x->GetNext();
        }
    }

    return false;
}

void Node::OrderedInsert(Node *x) {
    //inserci�n de los nodos en orden creciente
    Node *a = head_datas;
    Node *prev = 0;

    while (a != 0 && x->GetValue() > a->GetValue()) {
        prev = a;
        a = a->GetNext();
    }

    x->SetNext(a);

    if (prev != 0) {
        prev->SetNext(x);
    } else {
        head_datas = x;
    }
}

bool Node::IsEmpty() {
    return (head_datas == 0);
}

void Node::PrintList() {
    vendas->PrintList();
}

void Node::PrintList2() {
    Node *x = head_datas;

    while (x != 0) {
        cout << " --> " << x->GetValue() << " ";
        x->vendas->PrintList2();
        x = x->GetNext();
    }

    cout << " --> null";
}
