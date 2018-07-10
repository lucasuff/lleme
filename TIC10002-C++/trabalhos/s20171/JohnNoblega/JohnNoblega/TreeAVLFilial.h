#pragma once
#include <iostream>
#include <fstream>
#include <string>
#include "NodeFilial.h"
#include "Filial.h"
#include "ListFilial.h"

using namespace std;

template <typename K>
class TreeAVLFilial {
private:
    NodeFilial<K>* _root;
    int _sizeTree;

public:
    TreeAVLFilial();

    //Methods
    void insert(K&, Filial*);
    void insert(NodeFilial<K>*&, K&, Filial*);

    void print(string);
    void print(NodeFilial<K>*, ofstream&);

    Filial* search(const K&);
    Filial* search(NodeFilial<K>*, const K&);

    ListFilial searchInterval(const K&, const K&);
    void searchInterval(NodeFilial<K>*, const K&, const K&, ListFilial&);

    int height();
    int height(NodeFilial<K>*);

    void SingRotateLeft(NodeFilial<K>* &);
    void SingRotateRight(NodeFilial<K>* &);
    void DoubleRotateLR(NodeFilial<K>* &);
    void DoubleRotateRL(NodeFilial<K>* &);
};

template <typename K> TreeAVLFilial<K>::TreeAVLFilial() {
    _root = NULL;
    _sizeTree = 0;
}

template <typename K> void TreeAVLFilial<K>::insert(K& key, Filial* data) {
    if (_root == NULL)
        _root = new NodeFilial<K>(key, data);
    else
        insert(_root, key, data);
}

template <typename K> void TreeAVLFilial<K>::insert(NodeFilial<K>* &current, K& key, Filial* data) {

    if (current == NULL) {
        current = new NodeFilial<K>(key, data);
        return;
    }
    if (key < current->_key) {
        insert(current->_leftSon, key, data);
        if (2 == height(current->_leftSon) - height(current->_rightSon))
            if (key < current->_leftSon->_key)
                SingRotateLeft(current);
            else
                DoubleRotateLR(current);
    } else {
        insert(current->_rightSon, key, data);
        if (-2 == height(current->_leftSon) - height(current->_rightSon))
            if (key >= current->_rightSon->_key)
                SingRotateRight(current);
            else
                DoubleRotateRL(current);
    }
    current->_height = max(height(current->_leftSon), height(current->_rightSon)) + 1;
}

template <typename K> int TreeAVLFilial<K>::height() {
    return height(_root);
}

template <typename K> int TreeAVLFilial<K>::height(NodeFilial<K>* current) {
    if (current == NULL)
        return -1;
    return current->_height;
}

template <typename K> void TreeAVLFilial<K>::SingRotateLeft(NodeFilial<K>* &A) {
    NodeFilial<K>* father = A->_father;
    NodeFilial<K>* B = A->_leftSon;
    NodeFilial<K>* C = B->_rightSon;

    A->_leftSon = C;
    B->_rightSon = A;
    A->_height = max(height(A->_leftSon), height(A->_rightSon)) + 1;
    B->_height = max(height(B->_leftSon), A->_height) + 1;

    A = B;
}

template <typename K> void TreeAVLFilial<K>::SingRotateRight(NodeFilial<K>* &A) {
    NodeFilial<K>* father = A->_father;
    NodeFilial<K>* B = A->_rightSon;
    NodeFilial<K>* C = B->_leftSon;

    A->_rightSon = C;
    B->_leftSon = A;
    A->_height = max(height(A->_leftSon), height(A->_rightSon)) + 1;
    B->_height = max(height(B->_rightSon), A->_height) + 1;
    A = B;
}

template <typename K> void TreeAVLFilial<K>::DoubleRotateLR(NodeFilial<K>* &A) {
    SingRotateRight(A->_leftSon);
    SingRotateLeft(A);
}

template <typename K> void TreeAVLFilial<K>::DoubleRotateRL(NodeFilial<K>* &A) {
    SingRotateLeft(A->_rightSon);
    SingRotateRight(A);
}

template <typename K> void TreeAVLFilial<K>::print(string file_name) {
    ofstream file;
    file.open(file_name.c_str());
    file << "digraph G{" << endl;
    file << "node [shape = record,height=.1];" << endl;
    print(_root, file);
    file << "}" << endl;

}

template <typename K> void TreeAVLFilial<K>::print(NodeFilial<K>* nodo, ofstream& file) {
    if (nodo == NULL)
        return;
    file << "\"" << nodo->_data << "\"[label=\" <f0> |<f1> key:" << nodo->_key << " total: " << nodo->_data->getTotal_Vendido() << "|<f2> \"];" << endl;
    if (nodo->_leftSon != 0)
        file << "\"" << nodo->_data << "\":f0 -> \"" << nodo->_leftSon->_data << "\":f1;" << endl;
    if (nodo->_rightSon != 0)
        file << "\"" << nodo->_data << "\":f2 -> \"" << nodo->_rightSon->_data << "\":f1;" << endl;


    print(nodo->_leftSon, file);
    print(nodo->_rightSon, file);
}

template <typename K> Filial* TreeAVLFilial<K>::search(const K& key) {
    return search(_root, key);
}

template <typename K> Filial* TreeAVLFilial<K>::search(NodeFilial<K>* current, const K& key) {
    if (current != NULL) {
        if (current->_key == key)
            return current->_data;
        else if (key < current->_key)
            return search(current->_leftSon, key);
        else
            return search(current->_rightSon, key);
    }
    return NULL;

}

template <typename K> ListFilial TreeAVLFilial<K>::searchInterval(const K& key_begin, const K& key_end) {
    ListFilial result;

    searchInterval(_root, key_begin, key_end, result);
    return result;
}

template <typename K> void TreeAVLFilial<K>::searchInterval(NodeFilial<K>* current, const K& key_begin, const K& key_end, ListFilial& result) {
    if (current == NULL)
        return;
    if (key_begin <= current->_key && current->_key <= key_end) {
        searchInterval(current->_leftSon, key_begin, key_end, result);
        result.insert(current->_data);
        searchInterval(current->_rightSon, key_begin, key_end, result);
    } else if (current->_key < key_begin)
        searchInterval(current->_rightSon, key_begin, key_end, result);
    else if (current->_key > key_end)
        searchInterval(current->_leftSon, key_begin, key_end, result);
}