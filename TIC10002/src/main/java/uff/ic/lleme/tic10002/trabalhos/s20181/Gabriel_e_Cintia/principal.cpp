/*

	Gabriel Duarte
	Cintia Costa

*/

#include <bits/stdc++.h>
#ifndef CLIENTE
	#include "Cliente.cpp"
#endif
#ifndef TIPOASSUNTO
	#include "TipoAssunto.cpp"
#endif
#ifndef ATENDIMENTO
	#include "Assunto.cpp"
#endif
#include "Atendimento.cpp"
#include "avlTree.cpp"
using namespace std;

#define pb push_back


// Objetos globais
vector<TipoAssunto> tipoAssuntos; 	// Tipos de assuntos existentes
vector<Cliente> clientes; 		  	// Clientes 

Avl atendimentos; 				    // Todos os atendimentos em aberto 
vector<vector<Atendimento>> atendimentosEncerrados; // Matriz com os atendimentos encerrados
												    // Cada linha é um dia

// Hash tables para geração das estatisticas do dia
unordered_map<int, int> tiposAgrupadosTotal;    // Quantos vezes cada tipo aparece
unordered_map<int, int> tiposAgrupadosDuracao;  // Soma dos tempos de duração
int horaAtual;

// Carrega da memória alguns tipos de assunto
void carregarAssuntos(){
	freopen("tabelaAssunto", "rt", stdin);
	int tipo, urgencia;
	string titulo;
	while(cin >> tipo >> urgencia){
		getline(cin, titulo);
		tipoAssuntos.pb(TipoAssunto(tipo, titulo, urgencia));
	}
	printf("%d assunto(s) carregado(s).\n", (int)tipoAssuntos.size());
}

// Gera uma string aleatória
string genString(int n){
	string ans = "";
	while(n--){
		char c = (rand()%26)+'a';
		ans += c;
	}
	return ans;
}

// Sempre que recepcionamos uma pessoa o tempo de espera das outras aumenta em 1
void recepecionar(){
	horaAtual++;

	// Gerando um cliente aleatório
	int id = clientes.size();
	int idade = (rand()%100)+1;
	string nome = genString((rand()%10)+1);
	Cliente c(id, idade, nome);
	
	vector<Assunto> assuntos;
	int qtdTipoAssuntos = tipoAssuntos.size();

	// Gerando um 'qtdAssuntos' aleatórios
	int qtdAssuntos = (rand()%5)+1;
	while(qtdAssuntos--){
		int tipo = rand()%qtdTipoAssuntos;
		string descricao = genString((rand()%10)+1);
		Assunto as(tipoAssuntos[tipo], descricao);
		assuntos.push_back(as);
	}

	// Cadastrando o atendimento
	Atendimento at(c, assuntos, horaAtual);
	double priori = at.getPrioridade(horaAtual);
	// Atualizar todo mundo com mais 1 minuto de espera = 1/45
	atendimentos.updateTree();
	atendimentos.insert(priori, at);
}

void encerrar(Atendimento at){

	// Remover da árvore
	// Essa funcao sempre apaga a maior prioridade
	atendimentos.deleteNode();

	int tempoTotalGasto = 0; // Soma do tempo de todos os assuntos
	at.setHoraAtendimento(horaAtual);

	// Resolver todas os assuntos, incluindo providencias e o tempo
	for(auto &as : at.getAssuntos()){
		string providencias = genString((rand()%10)+1);
		int duracao = (rand()%10)+1;
		tempoTotalGasto += duracao;
		as.setProvidencias(providencias);
		as.setDuracao(duracao);

		// Agrupando os 'tipo assunto' para gerar estatisticas
		tiposAgrupadosTotal[as.getTipoAssunto().getTipo()]++;
		tiposAgrupadosDuracao[as.getTipoAssunto().getTipo()] += duracao;
	}

	// A hora atual aumenta no tempo gasto
	horaAtual += tempoTotalGasto;

	// Precisa mudar a prioridade de todo mundo com +tempoTotalGasto de tempo de espera
	atendimentos.updateTree(tempoTotalGasto/45.0);

	// Salvando o atendimento encerrado na última lista, que representa o dia atual
	atendimentosEncerrados.back().pb(at);
}

void atender(){
	if(!atendimentos.getTotal()){ // Existe alguém na fila ?
		printf("Não existe atendimento para fazer.\n");
		return;
	}
	
	encerrar(atendimentos.getNext());
}

// Gera uma estatistica simples com os tipos de assunto com o tempo médio gasto
void gerarEstatisticas(){
	printf("ID   Tempo medio\n");
	for(auto t : tiposAgrupadosTotal){
		printf("%03d   %.2lf\n", t.first, (double)tiposAgrupadosDuracao[t.first]/t.second);
	}
}

void iniciarDia(){
	carregarAssuntos();

	// Hora inicial do dia
	horaAtual = 0;
	
	// Criando a lista vazia do dia corrente
	atendimentosEncerrados.push_back(vector<Atendimento>());

	tiposAgrupadosTotal.clear();
	tiposAgrupadosDuracao.clear();

	// Iniciando os atendimentos
	atendimentos.init();
}

int main(){
	srand(time(0));
	iniciarDia();

	int total = 5;
	for(int i = 0; i < total; i++)
		recepecionar();
	for(int i = 0; i < total; i++)
		atender();

	gerarEstatisticas();
	return 0;
}

