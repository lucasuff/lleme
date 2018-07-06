#pragma once
#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
#include "Filial.h"

using namespace std;

template <typename K>
class TreeAVLFilial;

template <typename K>
class NodeFilial {
private:
    K _key;
    Filial* _data;
    int _height;
    NodeFilial<K>* _father;
    NodeFilial<K>* _rightSon;
    NodeFilial<K>* _leftSon;

public:

    //CONSTRUCTORS
    NodeFilial();
    NodeFilial(const K &, Filial*);
    NodeFilial(const K&, Filial*, const NodeFilial<K>*&, const NodeFilial<K>*&, const NodeFilial<K>*&);

    //Getters
    Filial* getData();

    //Methods of Course
    bool isLeft();
    bool isRight();
    bool isLeaf();
    bool hasUniqueChild();
    NodeFilial<K>* uniqueChild();
    NodeFilial<K>* childLeaf();
    NodeFilial<K>* brother();
    NodeFilial<K>* connect(NodeFilial<K>*);

    friend class TreeAVLFilial<K>;
};

/***************CONSTRUCTORS****************+***/
template <typename K> NodeFilial<K>::NodeFilial() {
    this->_height = 0;
    this->_father = NULL;
    this->_rightSon = NULL;
    this->_leftSon = NULL;
}

template <typename K> NodeFilial<K>::NodeFilial(const K& key, Filial* data) {
    this->_key = key;
    this->_data = data;
    this->_height = 0;
    this->_father = NULL;
    this->_rightSon = NULL;
    this->_leftSon = NULL;
}

template <typename K> NodeFilial<K>::NodeFilial(const K& key, Filial* data, const NodeFilial<K>*& father, const NodeFilial<K>*& leftSon, const NodeFilial<K>*& rightSon) {
    this->_key = key;
    this->_data = data;
    this->_height = 0;
    this->_father = father;
    this->_rightSon = rightSon;
    this->_leftSon = leftSon;
}


//Getters

template <typename K> Filial* NodeFilial<K>::getData() {
    return _data;
}

/***************METHODS OF COURSE******************/

template <typename K> bool NodeFilial<K>::isLeft() {
    if (_father != NULL)
        return _father->_leftSon == this;

    return false;
}

template <typename K> bool NodeFilial<K>::isRight() {
    if (_father != NULL)
        return _father->_rightSon == this;

    return false;
}

template <typename K> bool NodeFilial<K>::isLeaf() {
    return _rightSon == NULL && _leftSon == NULL;
}

template <typename K> bool NodeFilial<K>::hasUniqueChild() {
    return !(_leftSon != NULL && _rightSon != NULL);
}

template <typename K> NodeFilial<K>* NodeFilial<K>::uniqueChild() {
    if (_leftSon != NULL && _rightSon == NULL)
        return _leftSon;
    else if (_rightSon != NULL && _leftSon == NULL)
        return _rightSon;
    return NULL;
}

template <typename K> NodeFilial<K>* NodeFilial<K>::childLeaf() {
    if (_leftSon != NULL && _leftSon.isLeaf())
        return _leftSon;
    else if (_rightSon != NULL && _rightSon.isLeaf())
        return _rightSon;
    return NULL;
}

template <typename K> NodeFilial<K>* NodeFilial<K>::brother() {
    if (_father != NULL)
        if (_father._leftSon == this)
            return _father._rightSon;
        else
            return _father._leftSon;
    return NULL;
}

template <typename K> NodeFilial<K>* NodeFilial<K>::connect(NodeFilial<K>* son) {
    if (son != NULL)
        if (son->_key < this->_key)
            if (this->_leftSon == NULL) {
                this->_leftSon = son;
                son->_father = this;
                return son;
            } else
                return this->_leftSon->connect(son);
        else
            if (this->_rightSon == NULL) {
            this->_rightSon = son;
            son->_father = this;
            return son;
        } else
            return this->_rightSon->connect(son);
    return NULL;


}



