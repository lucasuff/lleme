//############################################################################################
//#### Universidade Federal Fluminense - Programa de Pós-graduação em Ciência da Computação	##
//#### Estruturas de Dados e Algoritmos (2017.1) - Prof. Luiz André Portes Paes Leme		##
//#### Discente: FABIANO DA GUIA ROCHA														##
//############################################################################################

#include <stdio.h>
#include "lib.h"

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

//#################################################
//#### Funções referente a AVL1 - chave Filial ####
//#### Função responsável por inserir Nó na AVL ####
void insere(int filial, int ano, int mes, float totalVendido){
    struct arvore *aux1 = NULL, *aux2 = NULL;
    int n = filial;
    
    //Cria nó raiz
	if(!raiz){
        raiz = (struct arvore*) malloc(sizeof(struct arvore));

        if(!raiz){
            printf("Erro ao alocar! Tecle para continuar..");
            exit(1);
        }
        raiz->n = n;
        raiz->totalVendido = totalVendido;
        raiz->esquerda = NULL;
        raiz->direita = NULL;
        raiz->pai = NULL;
        raiz->fb = 0;
        raiz->auxiliar=insere2(NULL, ano, mes, totalVendido);
    }

	//Caso já exista nó raiz, efetua a insersão do nó a esquerda ou a direita da AVL
    else{
        aux1 = raiz;
        aux2 = aux1;

        while(aux2){
            if(n < aux2->n){
                aux2 = aux2->esquerda;

                if(!aux2){
                    aux1->esquerda = (struct arvore*) malloc(sizeof(struct arvore));

                    if(!aux1->esquerda){
                        printf("Erro ao alocar! Tecle para continuar..");
                        exit(1);
                    }
					
                    aux2 = aux1->esquerda;
                    aux2->esquerda = NULL;
                    aux2->direita = NULL;
                    aux2->pai = aux1;
                    aux2->fb = 0;
                    aux2->n = n;
                    aux2->totalVendido = totalVendido;
					aux2->auxiliar=insere2(NULL, ano, mes, totalVendido);
					
                    break;
                }
   
                else{
                    aux1 = aux2;
                }
            }

            else{
                aux2 = aux2->direita;

                if(!aux2){
                    aux1->direita = (struct arvore*) malloc(sizeof(struct arvore));

                    if(!aux1->direita){
                        printf("Erro ao alocar! Tecle para continuar..");
                        exit(1);
                    }
					
                    aux2 = aux1->direita;
                    
                    aux2->esquerda = NULL;
                    aux2->direita = NULL;
                    aux2->pai = aux1;
                    aux2->n = n;
                    aux2->fb=0;
                    aux2->totalVendido = totalVendido;
					aux2->auxiliar=insere2(NULL, ano, mes, totalVendido);	
                    break;
                }

                else{
                    aux1 = aux2;
                }
            }
        }
    }

	//Após inserido novo Nó, verifica se AVL está desbalanceada e efetua balanceamento se necessário
    if(aux2){
        while(aux2){
            aux2->fb = altura(aux2->direita) - altura(aux2->esquerda);

            if(aux2->fb > 1 || aux2->fb < -1){
                aux2 = balanceia(aux2);
            }

            aux2 = aux2->pai;
        }
    }
}

//#### Função responsável por buscar pela chave desejada na AVL
struct arvore * buscar(struct arvore *arv, int n){
    struct arvore *aux;

    aux = arv;

    while(aux && aux->n != n){
        if(n < aux->n){
            aux = aux->esquerda;
        }

        else{
            aux = aux->direita;
        }
    }

    return aux;
}

//#### Função responsável por calcular a altura da AVL ####
int altura(struct arvore *aux){
    if(!aux){
        return 0;
    }

    int ae = altura(aux->esquerda);
    int ad = altura(aux->direita);

    return (ae > ad) ? ae + 1: ad + 1;
}

//#### Função responsável por efetuar a rotação a direita ####
struct arvore* rot_direita(struct arvore *aux){
    struct arvore *esq = aux->esquerda;

    aux->esquerda = esq->direita;
    esq->direita = aux;

    if(aux->esquerda){
        aux->esquerda->pai = aux;
    }

    esq->pai = aux->pai;
    aux->pai = esq;

    if(aux == raiz){
        raiz = esq;
    }

    if(esq->pai){
        if(esq->n < esq->pai->n){
            esq->pai->esquerda = esq;
        }

        else{
            esq->pai->direita = esq;
        }
    }

    esq->fb = altura(esq->direita) - altura(esq->esquerda);
    aux->fb = altura(aux->direita) - altura(aux->esquerda);

    return esq;
}

//#### Função responsável por efetuar a rotação a esquerda ####
struct arvore* rot_esquerda(struct arvore *aux){
    struct arvore *dir = aux->direita;

    aux->direita = dir->esquerda;
    dir->esquerda = aux;

    if(aux->direita){
        aux->direita->pai = aux;
    }

    dir->pai = aux->pai;
    aux->pai = dir;

    if(aux == raiz){
        raiz = dir;
    }

    if(dir->pai){
        if(dir->n < dir->pai->n){
            dir->pai->esquerda = dir;
        }

        else{
            dir->pai->direita = dir;
        }
    }

    dir->fb = altura(dir->direita) - altura(dir->esquerda);
    aux->fb = altura(aux->direita) - altura(aux->esquerda);

    return dir;
}

//#### Função responsável por efetuar o balanceamento da AVL ####
struct arvore* balanceia(struct arvore *aux){
    if(aux->fb < -1){
        if(aux->esquerda->fb > 0){
            aux->esquerda = rot_esquerda(aux->esquerda);
        }

        aux = rot_direita(aux);
    }

    else if(aux->fb > 1){
        if(aux->direita->fb < 0){
            aux->direita = rot_direita(aux->direita);
        }

        aux = rot_esquerda(aux);
    }

    return aux;
}

//#################################################
//#### Funções referente a AVL2 - chave Ano ####
//#### Função responsável por inserir Nó na AVL ####
struct arvore2 * insere2(struct arvore2 *raiz2, int ano, int mes, float totalVendido){
	struct arvore2 *aux1 = NULL, *aux2 = NULL;
	int n = ano;
	int i;
    
	if(!raiz2){
        raiz2 = (struct arvore2*) malloc(sizeof(struct arvore2));

        if(!raiz2){
            printf("Erro ao alocar! Tecle para continuar..");
            exit(1);
        }
		
        raiz2->n = n;
        raiz2->esquerda = NULL;
        raiz2->direita = NULL;
        raiz2->pai = NULL;
        raiz2->fb = 0;
        float *vetorVendas = (float*) malloc(12 * sizeof(float));
        
		for(i=0;i<12;i++) 
			vetorVendas[i]=0.0;
        
		vetorVendas[mes-1]=totalVendido;
        raiz2->vendasAnoMes= vetorVendas;
        return raiz2;
    }

    else{
        aux1 = raiz2;
        aux2 = aux1;

        while(aux2){
            if(n < aux2->n){
                aux2 = aux2->esquerda;

                if(!aux2){
                    aux1->esquerda = (struct arvore2*) malloc(sizeof(struct arvore2));

                    if(!aux1->esquerda){
                        printf("Erro ao alocar! Tecle para continuar..");
                        exit(1);
                    }
                    
                    aux2= aux1->esquerda;
                    aux2->n = n;
      				aux2->esquerda = NULL;
       				aux2->direita = NULL;
        			aux2->pai = NULL;
        			aux2->fb = 0;
        			
					float *vetorVendas = (float*) malloc(12 * sizeof(float));
        			
					for(i=0;i<12;i++) 
						vetorVendas[i]=0.0;
       				
					vetorVendas[mes-1]=totalVendido;
        			aux2->vendasAnoMes= vetorVendas;
                    break;
                }
   
                else{
                    aux1 = aux2;
                }
            }

            else{
                aux2 = aux2->direita;

                if(!aux2){
                    aux1->direita = (struct arvore2*) malloc(sizeof(struct arvore2));

                    if(!aux1->direita){
                        printf("Erro ao alocar! Tecle para continuar..");
                        exit(1);
                    }
					aux2= aux1->direita;
                    aux2->n = n;
      				aux2->esquerda = NULL;
       				aux2->direita = NULL;
        			aux2->pai = NULL;
        			aux2->fb = 0;
        			
					float *vetorVendas = (float*) malloc(12 * sizeof(float));
        			
					for(i=0;i<12;i++) 
						vetorVendas[i]=0.0;
       				
					vetorVendas[mes-1]=totalVendido;
        			aux2->vendasAnoMes= vetorVendas;
                    break;
                }

                else{
                    aux1 = aux2;
                }
            }
        }
    }
    
    //printf("%i Inserido! \n",n);

    if(aux2){
        while(aux2){
            aux2->fb = altura2(aux2->direita) - altura2(aux2->esquerda);

            if(aux2->fb > 1 || aux2->fb < -1){
                aux2 = balanceia2(aux2);
            }

            aux2 = aux2->pai;
        }
    }
    return aux2;
}

//#### Função responsável por buscar pela chave desejada na AVL
struct arvore2* buscar2(int n, struct arvore2 *arv){
    struct arvore2 *aux;

    aux = arv;

    while(aux && aux->n != n){
        if(n < aux->n){
            aux = aux->esquerda;
        }

        else{
            aux = aux->direita;
        }
    }

    return aux;
}

//#### Função responsável por calcular a altura da AVL ####
int altura2(struct arvore2 *aux){
    if(!aux){
        return 0;
    }

    int ae = altura2(aux->esquerda);
    int ad = altura2(aux->direita);

    return (ae > ad) ? ae + 1: ad + 1;
}

//#### Função responsável por efetuar a rotação a direita ####
struct arvore2* rot_direita2(struct arvore2 *aux){
    struct arvore2 *esq = aux->esquerda;

    aux->esquerda = esq->direita;
    esq->direita = aux;

    if(aux->esquerda){
        aux->esquerda->pai = aux;
    }

    esq->pai = aux->pai;
    aux->pai = esq;

    if(aux == raiz){
        raiz2 = esq;
    }

    if(esq->pai){
        if(esq->n < esq->pai->n){
            esq->pai->esquerda = esq;
        }

        else{
            esq->pai->direita = esq;
        }
    }

    esq->fb = altura2(esq->direita) - altura2(esq->esquerda);
    aux->fb = altura2(aux->direita) - altura2(aux->esquerda);

    return esq;
}

//#### Função responsável por efetuar a rotação a esquerda ####
struct arvore2* rot_esquerda2(struct arvore2 *aux){
    struct arvore2 *dir = aux->direita;

    aux->direita = dir->esquerda;
    dir->esquerda = aux;

    if(aux->direita){
        aux->direita->pai = aux;
    }

    dir->pai = aux->pai;
    aux->pai = dir;

    if(aux == raiz2){
        raiz2 = dir;
    }

    if(dir->pai){
        if(dir->n < dir->pai->n){
            dir->pai->esquerda = dir;
        }

        else{
            dir->pai->direita = dir;
        }
    }

    dir->fb = altura2(dir->direita) - altura2(dir->esquerda);
    aux->fb = altura2(aux->direita) - altura2(aux->esquerda);

    return dir;
}

//#### Função responsável por efetuar o balanceamento da AVL ####
struct arvore2* balanceia2(struct arvore2 *aux){
    if(aux->fb < -1){
        if(aux->esquerda->fb > 0){
            aux->esquerda = rot_esquerda2(aux->esquerda);
        }

        aux = rot_direita2(aux);
    }

    else if(aux->fb > 1){
        if(aux->direita->fb < 0){
            aux->direita = rot_direita2(aux->direita);
        }

        aux = rot_esquerda2(aux);
    }

    return aux;
}

//#### Função responsável por liberar a memória utilizada pelas AVL ####
void liberaAVL(struct arvore *avl1){
	free(avl1);
}
//#################################################
//#### Funções referente as Consultas ####
//#### Função responsável por realizar a consulta tipo 1 
void consulta_01(){
	int teste = 1, aux;
	int filial01, filial02;
	
	while(teste){
   		printf("################################### Exercicio 01 ###################################\n");
      	printf("#### Informe o codigo das filiais separadas por um espaco (Ex.: 10 20): ");
   		scanf("%d%d",&filial01, &filial02);
   		if (filial01<0 || filial02<0){
			printf("\nDados invalidos!\n");
   			system("pause");
			system("cls");
		}
		else teste = 0;
	}
	
	if (filial01 > filial02){
		aux = filial01; 
		filial01=filial02; 
		filial02=aux;
	} 
  	
	printf("####");
  	printf("\n#### Total de vendas das filiais com codigos entre %d e %d: $%.2f \n" , filial01, filial02, consulta01(raiz, filial01, filial02));	
   	printf("####################################################################################\n");
}

void consulta_02(){
	int teste = 1, aux;
	int filial01, filial02, mes01, mes02, ano01, ano02;
	float total = 0.0;
	
	while(teste){
   		printf("\n\n################################### Exercicio 02 ###################################\n");
      	printf("#### Informe o codigo das filiais separadas por um espaco (Ex.: 10 20): ");
   		scanf("%d%d", &filial01, &filial02);
		printf("####");	
		printf("\n#### Data inicial - Mes e Ano separados por um espaco (Ex. 01 2017): ");
   		scanf("%d%d", &mes01, &ano01);
   		printf("#### Data Final - Mes e Ano separados por um espaco (Ex. 12 2017): ");
   		scanf("%d%d", &mes02, &ano02);
   		printf("####");
   	
   		if (filial01 < 0 || filial02 < 0 || mes01 < 1 || mes01 > 12 || mes02 < 1 || mes02 > 12 || ano01 < 0  || ano02 <0){
			printf("\nDados invalidos!\n");
   			system("pause");
			system("cls");
		}

		else teste = 0;
	}

	if (filial01 > filial02){
		aux = filial01; 
		filial01 = filial02; 
		filial02 = aux;
	} 
  	
  	if (ano01 < ano02){
		total = consulta02(raiz, filial01, filial02, ano01, mes01, ano01, 12);
		aux = ano01;
		aux++;
		while (aux < ano02){
			total += consulta02(raiz, filial01, filial02, aux, 01, aux, 12);
			aux++;			
		}
		total += consulta02(raiz, filial01, filial02, ano02, 01, ano02, mes02);
	}
	else if(ano01 > ano02){
		//trata intervalo de data
		aux = ano02;
		ano02 = ano01;
		ano01 = aux;
		aux = mes02;
		mes02 = mes01;
		mes01 = aux;
		
		total = consulta02(raiz, filial01, filial02, ano01, mes01, ano01, 12);
		aux = ano01;
		aux++;
		while (aux < ano02){
			total += consulta02(raiz, filial01, filial02, aux, 01, aux, 12);
			aux++;			
		}
		total += consulta02(raiz, filial01, filial02, ano02, 01, ano02, mes02);
	}
	else if(ano01 == ano02 && mes01 > mes02){
		//trata intervalo de data
		aux = mes02;
		mes02 = mes01;
		mes01 = aux;
		
		total = consulta02(raiz, filial01, filial02, ano01, mes01, ano02, mes02);
	}
	else
		total = consulta02(raiz, filial01, filial02, ano01, mes01, ano02, mes02);
	
	printf("\n#### Total de vendas (Filial: %d a %d - %d/%d a %d/%d): $%.2f \n", 
	  filial01, filial02, mes01, ano01, mes02, ano02, total);
   	printf("####################################################################################\n");
}

void consulta_03(){
	int aux, teste = 1;
	int mes01, mes02, ano01, ano02;
	float total = 0.0;
	
	while(teste){
		printf("\n\n################################### Exercicio 03 ###################################\n");
		printf("#### Data inicial - Mes e Ano separados por um espaco (Ex. 01 2017): ");
	   	scanf("%d%d", &mes01, &ano01);
	   	printf("#### Data Final - Mes e Ano separados por um espaco (Ex. 12 2017): ");
	   	scanf("%d%d", &mes02, &ano02);
	  	printf("####");
	  	
	  	if (mes01 < 1 || mes01 > 12 || mes02 < 1 || mes02 > 12 || ano01 < 0  || ano02 <0){
			printf("\nDados invalidos!\n");
   			system("pause");
			system("cls");
		}

		else teste = 0;
	}
			  
	if (ano01 < ano02){
		total = consulta03(raiz, ano01, mes01, ano01, 12);
		aux = ano01;
		aux++;
		while (aux < ano02){
			total += consulta03(raiz, aux, 01, aux, 12);
			aux++;			
		}
		total += consulta03(raiz, ano02, 01, ano02, mes02);
	}
	else if (ano01 > ano02){
		//trata intervalo de data
		aux = ano02;
		ano02 = ano01;
		ano01 = aux;
		aux = mes02;
		mes02 = mes01;
		mes01 = aux;
		
		total = consulta03(raiz, ano01, mes01, ano01, 12);
		aux = ano01;
		aux++;
		while (aux < ano02){
			total += consulta03(raiz, aux, 01, aux, 12);
			aux++;			
		}
		total += consulta03(raiz, ano02, 01, ano02, mes02);
	}
	else if(ano01 == ano02 && mes01 > mes02){
		//trata intervalo de data
		aux = mes02;
		mes02 = mes01;
		mes01 = aux;
		
		total = consulta03(raiz, ano01, mes01, ano02, mes02);
	}
	else 
		total = consulta03(raiz, ano01, mes01, ano02, mes02);

	printf("\n#### Total de vendas de %d/%d a %d/%d): $%.2f \n", mes01, ano01, mes02, ano02, total);
   	printf("####################################################################################\n\n");	
}

float consulta01(struct arvore *aux, int filial01, int filial02){
	float total=0;
	if(!aux) return total;
	if(aux->n >= filial01 && aux->n <= filial02) total+=aux->totalVendido;
	//printf("%f ---- ", aux->totalVendido);
	total+=consulta01(aux->esquerda, filial01, filial02);
	total+=consulta01(aux->direita, filial01, filial02);
	return total;
}

float consulta02(struct arvore *aux, int filial01, int filial02, int ano1, int mes1, int ano2, int mes2){
	float total=0;
	if(!aux) return total;
	if(aux->n >= filial01 && aux->n <= filial02){
		total+=somaAnoMes(aux->auxiliar, ano1, mes1, ano2, mes2);	
	} 
	total+=consulta02(aux->esquerda, filial01, filial02, ano1, mes1, ano2, mes2);
	total+=consulta02(aux->direita, filial01, filial02, ano1, mes1, ano2, mes2);
	return total;
}

float consulta03(struct arvore *aux,  int ano1, int mes1, int ano2, int mes2){
	float total=0;
	if(!aux) return total;
	total+=somaAnoMes(aux->auxiliar, ano1, mes1, ano2, mes2);	
	total+=consulta03(aux->esquerda, ano1, mes1, ano2, mes2);
	total+=consulta03(aux->direita, ano1, mes1, ano2, mes2);
	return total;
}

float somaAnoMes(struct arvore2 *aux, int ano1, int mes1, int ano2, int mes2){
	float total=0;
	
	if(!aux) return total;
	
	if(aux->n >= ano1 && aux->n <= ano2) {
		int i;
		for	(i=(mes1-1); i < (mes2); i++) total+=aux->vendasAnoMes[i];
	}
	
	total+=somaAnoMes(aux->esquerda, ano1, mes1, ano2, mes2);
	total+=somaAnoMes(aux->direita, ano1, mes1, ano2, mes2);
	return total;
}
