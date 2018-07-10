/*

	Gabriel Duarte
	Cintia Costa

*/

#ifndef ASSUNTO
#define ASSUNTO 1
#include <bits/stdc++.h>
#include "TipoAssunto.cpp"
using namespace std;

class Assunto{
private:
	TipoAssunto tipoAssunto;
	string descricao, providencias;
	int duracaoAtendimento;
public:
	Assunto(){}
	Assunto(TipoAssunto tp, string d){
		tipoAssunto = tp;
		descricao = d;
	}
	void setTipoAssunto(TipoAssunto t){ tipoAssunto = t; }
	void setDescricao(string d){ descricao = d; }
	void setProvidencias(string p){ providencias = p; }
	void setDuracao(int d){ duracaoAtendimento = d; }
	TipoAssunto getTipoAssunto(){ return tipoAssunto; }
	string getDescricao(){	return descricao; }
	string getProvidencias(){ return providencias; }
	int getDuracao(){ return duracaoAtendimento; }

	void imprimirAssunto(){
		printf("Tipo assunto: %s - descricao: %s\n", tipoAssunto.getTitulo().c_str(), descricao.c_str());
	}

};	

#endif