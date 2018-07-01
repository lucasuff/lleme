#pragma once

#pragma once
#include "TreeAVLFilial.h"

template<typename T>
class HashFilial
{
private:
	int _size;
	TreeAVLFilial<T>* _table;
public:
	HashFilial(const int&);
	~HashFilial();


	void insert(T&, Filial*);
	Filial* search(T&);
};

template<typename T> HashFilial<T>::HashFilial(const int& size)
{
	this->_size = size;
	this->_table = new TreeAVLFilial<T>[size];
}

template<typename T> void HashFilial<T>::insert(T& key, Filial* data)
{
	int index = key % _size;

	_table[index].insert(key, data);
}

template <typename T> Filial* HashFilial<T>::search(T& key)
{
	int index = key % _size;
	return _table[index].search(key);
}

template<typename T> HashFilial<T>::~HashFilial()
{

}
