#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef struct List {
 int ano_mes, cod_vendedor, total_venda;
 struct List *next;
}Lista;

typedef struct nodo {
 int filial;
 struct List *vendas;
 int fatbal;
 struct nodo *esq, *dir;
} arvoreAVL;

struct List *head;
struct nodo *raiz = NULL;

// Inserir no inicio da lista--------------------------------------------------
struct List * add(struct List *lista, int ano_mes, int cod_vendedor, int total_venda) {
    struct List *temp;
    temp=(struct List *)malloc(sizeof(struct List));
    temp->ano_mes = ano_mes;
    temp->cod_vendedor = cod_vendedor;
    temp->total_venda = total_venda;
    if (lista!=NULL) temp->next=lista;
    return temp;
}
 
// Inicializa a Árvore AVL------------------------------------------------------
void InicializaAVL (arvoreAVL **eainicio){
 	*eainicio = malloc (sizeof (arvoreAVL));
 	(*eainicio)->esq = NULL;
 	(*eainicio)->dir = NULL;
 }

// Insere na Árvore AVL---------------------------------------------------------
void InsereAVL (arvoreAVL *adesc, int dadonovo, int d, int e, int f) {
 	arvoreAVL *paux, *pant, *pP, *pQ, *pantP, *pnovo;
 	struct List *lista;
 	int poschave;
 	int achou; 
 	
// Parte do código que faz a rotação simples-------------------------------------
	void RotacaoSimples (){
 		if (pP->fatbal == 1) {
   			pP->dir = pQ->esq;
   			pQ->esq = pP;
 		}
 		else {
   			pP->esq = pQ->dir;
   			pQ->dir = pP;
 		}
 		paux = pQ;
 		pP->fatbal = 0;
 		pQ->fatbal = 0;
	}

// Parte do cógido que faz a rotação dupla --------------------------------------
	void RotacaoDupla (){
 		if (pP->fatbal == 1) {
   			paux = pQ->esq;
   			pQ->esq = paux->dir;
  			paux->dir = pQ;
   			pP->dir = paux->esq;
   			paux->esq = pP;
 		}
 		else {
   			paux = pQ->dir;
   			pQ->dir = paux->esq;
   			paux->esq = pQ;
 			pP->esq = paux->dir;
  			paux->dir = pP;
 		}
 		if (paux->fatbal == -poschave){
   			pP->fatbal = 0;
   			pQ->fatbal = poschave;
 		}
 		else 
   			if (paux->fatbal == 0) {
     			pP->fatbal = 0;
     			pQ->fatbal = 0;
  			}
   			else {
     			pP->fatbal = -poschave;
     			pQ->fatbal = 0;
  			}
 		paux->fatbal = 0;
	}

// Essa parte do código faz o ajuste de fatores de balanceamento ---------------
	void AjustaFatoresAVL (){
 		if (dadonovo < pP->filial){
   			pQ = pP->esq;
   			paux = pP->esq;
 		}
 		else {
   			pQ = pP->dir;
   			paux = pP->dir;
 		}
 		while (paux->filial != dadonovo)
   			if (dadonovo < paux->filial) {
     			paux->fatbal = paux->fatbal - 1;
     			paux = paux->esq;
   			}
   			else {
     			paux->fatbal = paux->fatbal + 1;
     			paux = paux->dir;
   			}
	}	
	
// Parte que é realizado o balanceamento da Árvore AVL -------------------------
void BalanceiaAVL (){
 	if (dadonovo < pP->filial)
  		poschave = -1;
 	else
   		poschave = 1;
 		if (pP->fatbal == 0)
   			pP->fatbal = poschave;
 		else 
   			if (pP->fatbal == -poschave)
     			pP->fatbal = 0;
   			else {
     			if (pQ->fatbal * poschave > 0)
       				RotacaoSimples ();
     			else
       				RotacaoDupla ();
     				if (pantP->dir == pP)
       					pantP->dir = paux;
     				else
       					pantP->esq = paux;
   				}
	}

// Inicia a parte do código que Insere na Árvore AVL ----------------------------
 	paux = adesc->dir;
 
 	pP = paux;
 	pant = adesc;
 	pantP = adesc;
 	achou = 0;
 	while ((!achou) && (paux != NULL)) {
   		if (paux->fatbal != 0) {
     		pP = paux;
     		pantP = pant;
   		}
   		pant = paux;
  		if (dadonovo == paux->filial){
    		paux->vendas = add(paux->vendas, d, e, f);
	 		achou = 1;
		}
   	else
     	if (dadonovo < paux->filial)
    	   paux = paux->esq;
     	else
       		paux = paux->dir;
 	}	
 	if (achou);
 	else {
   		pnovo = malloc (sizeof (arvoreAVL));
   		pnovo->filial = dadonovo;
   		pnovo->vendas = add(NULL, d, e, f);
   		pnovo->esq = NULL;
   		pnovo->dir = NULL;
   		pnovo->fatbal = 0;
   		if (adesc->dir == NULL) adesc->dir = pnovo;
		else {
     		if (dadonovo < pant->filial) pant->esq = pnovo;
     		else pant->dir = pnovo;
     		AjustaFatoresAVL ();
     		BalanceiaAVL ();
   		}	
 	}
}

struct nodo* buscar(arvoreAVL *adesc, int n)
{
	arvoreAVL *paux;
    
	paux = adesc->dir;
    
    while ((paux) && (paux->filial != n)) {
   
	    if (n < paux->filial)
	       paux = paux->esq;
	     else
	       paux = paux->dir;
	}
    return paux;
}

int exercicio3(arvoreAVL *arvore, int data1, int data2){
	if(arvore==NULL) return 0;
	int totalgeral=0;	
	struct List *temp  =  arvore->vendas;
	//printf("\n =====> filial= %d ", arvore->filial);
	while (temp != NULL){
		if(temp->ano_mes>=data1 && temp->ano_mes<=data2){
			//printf("\n Data: %d - Venda = %d", temp->ano_mes, temp->total_venda);
			totalgeral += temp->total_venda;
 		}
 		temp=temp->next;
	}	
	totalgeral+=exercicio3(arvore->esq, data1, data2);
	totalgeral+=exercicio3(arvore->dir, data1, data2);	
	return totalgeral;	
} 
// Inicia o MENU ----------------------------------------------------------------
int menu ()
{
char res;
 	system("cls");
	printf ("DIGITE A OPCAO DE ESCOLHA ENTRE 1 E 4: \n");
	printf ("1 - CONSULTA POR FILIAIS. \n");
	printf ("2 - CONSULTA POR FILIAIS E MES. \n");
	printf ("3 - CONSULTA POR MES. \n");
	printf ("4 - SAIR. \n");
    res = getch();
return (res);
}


// Inicio do Main----------------------------------------------------------------
void main () {
	
 	arvoreAVL *aini;
 	struct nodo *arvFilial;
 	char * pch, ch;
 	int c,d,e,f, dados [4], i, numLinhas=0, busca1,busca2, data1, data2, totalgeral, resp,op1;
 	char textoLinha[40],op;
 	FILE *arquivo; 
 	arquivo = fopen("arquivo.txt", "r");
	if(arquivo != NULL)	{	
	    while((ch=fgetc(arquivo)) != EOF)
		{
   			if(ch == '\n'){
			   
   				numLinhas++;
   			}
		}
	}
	fclose(arquivo);
	arquivo = fopen("arquivo.txt", "r");
 	InicializaAVL (&aini);  
	if (arquivo != NULL){
		for (i=1; i <=numLinhas; i++){
			fscanf(arquivo, "%d[^, ]",&dados[0]);
            fseek(arquivo, +1, SEEK_CUR);
            fscanf(arquivo, "%d[^, ]",&dados[1]);
            fseek(arquivo, +1, SEEK_CUR);
            fscanf(arquivo, "%d[^, ]",&dados[2]);
            fseek(arquivo, +1, SEEK_CUR);
            fscanf(arquivo, "%d[^, ]",&dados[3]);
   			InsereAVL(aini, dados[0], dados[1], dados[2], dados[3]);	
		} 
	}
	fclose(arquivo);
	
	totalgeral=0;
	
	do
	    {
	resp = menu();
	
// Switch para as opções do Menu-------------------------------------------------
  switch ( resp )
  {
    case '1' :
     	{
	    do
	    {
		system("cls");
	    printf("##########################CONSULTA POR FILIAIS##########################\n");
		printf("Digite o intevalo de Filiais para a Busca. *Numero inteiro sem separadores (Ex: 1000)*\n");
		printf("Filial Inicio: ");
		scanf("%d",&busca1);
		printf("Filial Fim: ");
		scanf("%d",&busca2);
		
		clock_t tempo;
		tempo = clock ();
		
		for (i = busca1; i<= busca2; i++)	{
			arvFilial = buscar(aini,i); // O(log(n))
			if (arvFilial){ // O(n)
		   		struct List *temp  =  arvFilial->vendas;
		   		//printf("\n =====> filial= %d ", arvFilial->filial);
		   		while (temp != NULL){
		   			//printf("\n Data: %d - Venda = %d", temp->ano_mes, temp->total_venda);
					totalgeral += temp->total_venda;
	 				temp=temp->next;
				}	
	    	}
		}
	    printf("\n\n\nTOTAL GERAL DE VENDAS (1): %d\n\n", totalgeral);
		printf("\n\n\n tempo de execucao %f ms\n\n", (clock() -tempo)*1000 /(double)CLOCKS_PER_SEC);
	    totalgeral=0;
	    printf("Digite 1 para nova consulta ou 2 para sair\n");
    
		op = getch();
		}while ((op=='1') || (op!= '2'));
     	
		 break;
 		}
    case '2':
     	{
     	do
	    {
	    system("cls");
	    printf("#######################CONSULTA POR FILIAIS E PERIODO#######################\n");
		printf("Digite o intevalo de Filiais e Data. *Numero inteiro sem separadores (Ex: 1000 para filiais e 201701 para AnoMes)*\n");
		printf("Filial Inicio: ");
		scanf("%d",&busca1);
		printf("Filial Fim: ");
		scanf("%d",&busca2);
		printf("AnoMes Inicial: ");
		scanf("%d",&data1);
		printf("AnoMes Final: ");
		scanf("%d",&data2);
		
		clock_t tempo;
		tempo = clock ();
			
		for (i = busca1; i<= busca2; i++)	{
			arvFilial = buscar(aini,i); 
			if (arvFilial){ 
			   	struct List *temp  =  arvFilial->vendas;
			   	while (temp != NULL){
		  	 		if(temp->ano_mes>=data1 && temp->ano_mes<=data2){
						totalgeral += temp->total_venda;
	 				}
	 				temp=temp->next;
				}	
	    	}
	    }
	    printf("\n\n\nTOTAL GERAL DE VENDAS (2): %d\n\n", totalgeral);
	    printf("\n\n\n tempo de execucao %f ms\n\n", (clock() -tempo)*1000 /(double)CLOCKS_PER_SEC);
	   	    
		totalgeral=0;
		printf("Digite 1 para nova consulta ou 2 para sair\n");
    
		op = getch();
		}while ((op=='1') || (op!= '2'));
		
	    break;
 		}
    case '3':
     	{
		do
	    {
	    system("cls"); 	
	    printf("##########################CONSULTA POR PERIODO#########################\n");
		printf("Digite o intevalo de Data. *Numero inteiro sem separadores (Ex: 201701)*\n");
		printf("AnoMes Inicial: ");
		scanf("%d",&data1);
		printf("AnoMes Final: ");
		scanf("%d",&data2);
		
		clock_t tempo;
		tempo = clock ();
		
		totalgeral=exercicio3(aini->dir, data1, data2);
		printf("\n\n\nTOTAL GERAL DE VENDAS (3): %d\n\n", totalgeral);
		printf("\n\n\n tempo de execucao %f ms\n\n", (clock() -tempo)*1000 /(double)CLOCKS_PER_SEC);
		printf("Digite 1 para nova consulta ou 2 para sair\n");
    
		op = getch();
		}while ((op=='1') || (op!= '2'));
		
     	break;
     	}
 
    case '4':
        op1=4;
     break;
 
    default :
       printf ("Valor invalido!");
       getch();

  	}
}while (op1!= 4);

}
