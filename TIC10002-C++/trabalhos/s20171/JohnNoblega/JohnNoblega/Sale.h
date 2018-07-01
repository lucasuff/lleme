#pragma once
#include <iostream>
#include <fstream>
#include <string>
#include "TreeAVLFilial.h"


using namespace std;


class Sale {

private:
	TreeAVLFilial<int> 		_filiales_cod;
	TreeAVLFilial<string>	_filiales_date;


public:
	Sale(const string&);

	void readFile(const string&);


	ListFilial searchByCodeInterval(const int&, const int&);
	ListFilial searchByDateInterval(const string&, const string&);
	ListFilial searchByInterval(const int&, const int&, const string&, const string&);

	int total_searchByCodeInterval(const int&, const int&);
	int total_searchByDateInterval(const string&, const string&);
	int total_searchByInterval(const int&, const int&, const string&, const string&);



};

Sale::Sale(const string& file_name) {
	readFile(file_name);
}

void Sale::readFile(const string& file_name) {

	ifstream file;

	//Features of data
	int cod_filial = 0;
	string mes_ano = "";
	int cod_vendedor = 0;
	int total_vendido = 0;


	//Open a file with database
	file.open(file_name.c_str());


	//read the file
	while (!file.eof()) {
		file >> cod_filial >> mes_ano >> cod_vendedor >> total_vendido;
		Filial* filial = new Filial(cod_filial, mes_ano, cod_vendedor, total_vendido);
		
		_filiales_cod.insert(cod_filial, filial);
		_filiales_date.insert(mes_ano, filial);
	}

	
}



ListFilial Sale::searchByCodeInterval(const int& code_begin, const  int& code_end)
{

	ListFilial result = _filiales_cod.searchInterval(code_begin, code_end);
	return result;
}

ListFilial Sale::searchByDateInterval(const string& date_begin, const string& date_end)
{
	ListFilial result = _filiales_date.searchInterval(date_begin, date_end);
	return result;
}

ListFilial Sale::searchByInterval(const int& cod_begin, const int& cod_end, const string& date_begin, const string& date_end)
{

	ListFilial resultCode = _filiales_cod.searchInterval(cod_begin, cod_end);
	ListFilial resultDate = _filiales_date.searchInterval(date_begin, date_end);

	ListFilial result;
	result.interseccion(resultCode, resultDate);
	return result;
}


int Sale::total_searchByCodeInterval(const int& code_begin, const int& code_end)
{
	ListFilial result = searchByCodeInterval(code_begin, code_end);
	return result.getTotal();
}

int Sale::total_searchByDateInterval(const string& date_begin, const string& date_end)
{
	ListFilial result = searchByDateInterval(date_begin, date_end);
	return result.getTotal();
}

int Sale::total_searchByInterval(const int& cod_begin, const int& cod_end, const string& date_begin, const string& date_end)
{
	ListFilial result = searchByInterval(cod_begin, cod_end, date_begin, date_end);
	return result.getTotal();
}

