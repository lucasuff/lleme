/*

	Gabriel Duarte
	Cintia Costa

*/

#ifndef CLIENTE
#define CLIENTE 1
#include <bits/stdc++.h>
using namespace std;

class Cliente{
private:
	int id, idade;
	string nome;
public:
	Cliente(){}
	Cliente(int _id, int _idade, string _nome){
		id = _id;
		idade = _idade;
		nome = _nome;
	}
	void setId(int _id){ id = _id; }
	void setIDade(int _idade){ idade = _idade; }
	void setNome(string _nome){ nome = _nome; }
	int getIdade(){ return idade; }
	string getNome(){	return nome;	}
	int getId(){ return id; }

	void imprimirCliente(){
		printf("Id: %d - Idade: %d - Nome: %s\n", id, idade, nome.c_str());
	}

};	
#endif