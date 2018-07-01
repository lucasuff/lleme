//############################################################################################
//#### Universidade Federal Fluminense - Programa de Pós-graduação em Ciência da Computação	##
//#### Estruturas de Dados e Algoritmos (2017.1) - Prof. Luiz André Portes Paes Leme		##
//#### Discente: FABIANO DA GUIA ROCHA														##
//############################################################################################

//#### Estrutura da AVL2 ####
struct arvore2{
    int n; //chave da arvore, considerado como o valor do ano na AVL2
	int fb;
	float *vendasAnoMes;//acumulado por mês de cada filial
    struct arvore *esquerda;
    struct arvore *direita;
    struct arvore *pai;
};

//#### Estrutura da AVL1 ####
struct arvore{
    int n; //chave da arvore, considerado como o valor da filial na AVL1
	int fb;
	float totalVendido;
	struct arvore2 *auxiliar;//ponteiro para a AVL2 (Ano) e para o Array de Mês
    struct arvore *esquerda;
    struct arvore *direita;
    struct arvore *pai;
};

//#### Assinaturas ####
struct arvore *raiz; //ponteiro para raiz da AVL1 que irá tratar a filial
struct arvore2 *raiz2;//ponteiro para raiz da AVL2 que irá tratar o ano

//#### Assinaturas das funções ####
void insere(int filial, int ano, int mes, float totalVendido);
struct arvore * buscar(struct arvore *arv, int n);
int altura(struct arvore *aux);
struct arvore* rot_direita(struct arvore *aux);
struct arvore* rot_esquerda(struct arvore *aux);
struct arvore* balanceia(struct arvore *aux);
struct arvore2 * insere2(struct arvore2 *raiz2, int ano, int mes, float totalVendido);
struct arvore2* buscar2(int n, struct arvore2 *arv);
int altura2(struct arvore2 *aux);
struct arvore2* rot_direita2(struct arvore2 *aux);
struct arvore2* rot_esquerda2(struct arvore2 *aux);
struct arvore2* balanceia2(struct arvore2 *aux);
void liberaAVL(struct arvore *avl1);

//#### Funções referente as Consultas ####
void consulta_01();
void consulta_02();
void consulta_03();
float consulta01(struct arvore *aux, int filial01, int filial02);
float consulta02(struct arvore *aux, int filial01, int filial02, int ano1, int mes1, int ano2, int mes2);
float consulta03(struct arvore *aux,  int ano1, int mes1, int ano2, int mes2);
float somaAnoMes(struct arvore2 *aux, int ano1, int mes1, int ano2, int mes2);
