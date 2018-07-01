/*

	Gabriel Duarte
	Cintia Costa

*/

#ifndef TIPOASSUNTO
#define TIPOASSUNTO 1
#include <bits/stdc++.h>
using namespace std;

class TipoAssunto{
private:
	int tipo, urgencia;
	string titulo;
public:
	TipoAssunto(){}
	TipoAssunto(int _tipo, string _titulo, int _urgencia){
		tipo = _tipo;
		titulo = _titulo;
		urgencia = _urgencia;
	}
	void setTipo(int _tipo){ tipo = _tipo; }
	void setTitulo(string _titulo){ titulo = _titulo; }
	void setUrgencia(int _urgencia){ urgencia = _urgencia; }
	int getUrgencia(){ return urgencia; }
	string getTitulo(){	return titulo;	}
	int getTipo(){ return tipo; }

	void imprimirAssunto(){
		printf("Tipo: %d - Urgencia: %d - Titulo: %s\n", tipo, urgencia, titulo.c_str());
	}
};	
#endif