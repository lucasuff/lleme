/*

	Gabriel Duarte
	Cintia Costa

*/

#include <bits/stdc++.h>
#include "Cliente.cpp"
#include "Assunto.cpp"
using namespace std;

class Atendimento{
private:
	Cliente cliente;
	vector<Assunto> assuntos;
	int horaChegada, horaAtendimento;
	int somaUrgencia;
public:
	Atendimento(){ somaUrgencia = 0; }
	Atendimento(Cliente c, vector<Assunto> a, int hC){
		cliente = c;
		assuntos = a;
		horaChegada = hC;
		somaUrgencia = 0;
		for(auto a : assuntos)
			somaUrgencia += a.getTipoAssunto().getUrgencia();
	}
	void setCliente(Cliente c){ cliente = c; }
	void setAssuntos(vector<Assunto> a){ assuntos = a; }
	void setHoraChegada(int h){ horaChegada = h; }
	void setHoraAtendimento(int h){ horaAtendimento = h; }
	Cliente getCliente(){ return cliente; }
	vector<Assunto> &getAssuntos(){ return assuntos; }
	int getHoraChegada(){ return horaChegada; }
	int getHoraAtendimento(){ return horaAtendimento; }

	void adicionarAssunto(Assunto a){ 
		assuntos.push_back(a); 
		somaUrgencia += a.getTipoAssunto().getUrgencia();
	}

	double getPrioridade(int tempoAtual){
		double a = cliente.getIdade()/65.0;
		double b = (horaChegada-tempoAtual)/15.0; // Pensar nessa parte
		double c = 0;
		if(assuntos.size())
			c = (double)somaUrgencia/assuntos.size();
		c /= 10.0;

		return (a+b+c)/3;
	}

};	