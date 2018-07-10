#include "BinarySearchTree.h"
#include <iostream>

BinarySearchTree::BinarySearchTree() {
    root = 0;
}

BinarySearchTree::~BinarySearchTree() {
    DeleteTree(root);
    root = 0;

}

void BinarySearchTree::DeleteTree(Node* x) {
    // Recorrido em pos ordem
    if (x != 0) {
        DeleteTree(x->GetLeft());
        DeleteTree(x->GetRight());
        delete x;
    }
}

Node* BinarySearchTree::Search(int k) {
    Node* x = root;

    while (x != 0 && k != x->GetKey()) {
        if (k < x->GetKey())
            x = x->GetLeft();
        else
            x = x->GetRight();
    }

    return x;
}

void BinarySearchTree::Insert(Node* z) {
    Node* y = 0;
    Node* x = root;

    while (x != 0) {
        y = x;

        if (z->GetKey() < x->GetKey())
            x = x->GetLeft();
        else
            x = x->GetRight();
    }

    z->SetParent(y);

    if (y == 0)
        root = z;
    else if (z->GetKey() < y->GetKey())
        y->SetLeft(z);
    else
        y->SetRight(z);
}

void BinarySearchTree::InsertVenda1(Venda* V) {
    Node* N = Search(V->GetFilial());

    if (N != 0)
        N->InsertVenda(V);
}

void BinarySearchTree::InsertVenda2(Venda* V) {
    Node* N = Search(V->GetFilial()), *N2; //Buscando na arvore de filiais o n� que contenha a mesma filial da venda a ser inserida

    if (N != 0) {
        N2 = N->Search(V->GetAnoMes()); //Buscando na �rvore de datas do n� encontrado, o n� que contenha a mesma data da venda a ser inserida

        if (N2 != 0)
            N2->InsertVenda(V); //Inserindo a venda na lista de vendas do n� encontrado
    }
}

void BinarySearchTree::InsertVenda3(Venda* V) {
    Node* N = Search(V->GetAnoMes());

    if (N != 0)
        N->InsertVenda(V);
}

void BinarySearchTree::TreeWalk(Node* x, int LI, int LS, float& total) {
    if (x != 0) {
        if (x->GetKey() > LI)
            TreeWalk(x->GetLeft(), LI, LS, total);

        if (x->GetKey() < LS)
            TreeWalk(x->GetRight(), LI, LS, total);

        if (x->GetKey() >= LI && x->GetKey() <= LS)
            total += x->TotalVendas();
    }
}

float BinarySearchTree::TotalVendas(int limiteInferior, int limiteSuperior) {
    float total = 0.0f;
    TreeWalk(root, limiteInferior, limiteSuperior, total);
    return total;
}

void BinarySearchTree::TreeWalk(Node* x, int LI, int LS, int LI2, int LS2, float& total) {
    if (x != 0) {
        if (x->GetKey() > LI)
            TreeWalk(x->GetLeft(), LI, LS, LI2, LS2, total);

        if (x->GetKey() < LS)
            TreeWalk(x->GetRight(), LI, LS, LI2, LS2, total);

        if (x->GetKey() >= LI && x->GetKey() <= LS)
            TreeWalk(x->GetRootDatas(), LI2, LS2, total);
    }
}

float BinarySearchTree::TotalVendas(int limiteInferior, int limiteSuperior, int limiteInferior2, int limiteSuperior2) {
    float total = 0.0f;
    TreeWalk(root, limiteInferior, limiteSuperior, limiteInferior2, limiteSuperior2, total);
    return total;
}

void BinarySearchTree::PreorderTreeWalk(Node* x, int nivel) {
    for (int i = 1; i < nivel; i++)
        cout << "\t";

    if (x != 0) {
        cout << x->GetKey() << " --> ";
        x->PrintList();
        cout << endl;
        PreorderTreeWalk(x->GetRight(), nivel + 1);
        PreorderTreeWalk(x->GetLeft(), nivel + 1);
    } else
        cout << "null\n";
}

void BinarySearchTree::PreorderTreeWalk2(Node* x, int nivel) {
    for (int i = 1; i < nivel; i++)
        cout << "\t";

    if (x != 0) {
        cout << x->GetKey() << " --> \n";
        x->PrintTree(nivel + 1);
        cout << endl;
        PreorderTreeWalk2(x->GetRight(), nivel + 1);
        PreorderTreeWalk2(x->GetLeft(), nivel + 1);
    } else
        cout << "null\n";
}

void BinarySearchTree::PrintTree() {
    PreorderTreeWalk(root, 1);
}

void BinarySearchTree::PrintTree2() {
    PreorderTreeWalk2(root, 1);
}
