#include <stdio.h>
#include <stdlib.h>


struct venda {
	int codigo;
	float total;
	int ano;
	int mes;
	char filial[30];
};

typedef struct hash Hash;

struct hash {
	int qtd, TABLE_SIZE;
	struct venda **atributos;
};

Hash* criaHash(int TABLE_SIZE) {
	Hash* ha = (Hash*) malloc(sizeof (Hash));
	if(ha != NULL) {
		int i;
		ha->TABLE_SIZE = TABLE_SIZE;
		ha->atributos = (struct venda**) malloc(TABLE_SIZE * sizeof(struct venda*));
		if(ha->atributos == NULL) {
			free(ha);
			return NULL;
		}
		ha->qtd = 0;
		for(i = 0; i < ha->TABLE_SIZE; i++) {
			ha->atributos[i] = NULL;
		}
	}
	return ha;
}

int chaveDivisao(int chave, int TABLE_SIZE) {
	return (chave & 0x7FFFFFFF) % TABLE_SIZE;
}

int sondagemLinear (int pos, int i, int TABLE_SIZE) {
	return((pos + i) & 0x7FFFFFFF) % TABLE_SIZE;
}

int insereHash_EnderAberto(Hash* ha, struct venda val) {
	if(ha == NULL || ha->qtd == ha->TABLE_SIZE) {
		return 0;
	}
	int chave = val.codigo;
	int i, pos, newPos;
	pos = chaveDivisao(chave, ha->TABLE_SIZE);
	for (i = 0; i < ha->TABLE_SIZE; i++) {
		newPos = sondagemLinear(pos, i, ha->TABLE_SIZE);
		if(ha->atributos[newPos] == NULL) {
			struct venda* novo;
			novo = (struct venda*) malloc(sizeof(struct venda));
			if(novo == NULL) {
				return 0;
			}
			*novo = val;
			ha->atributos[newPos] = novo;
			ha->qtd++;
			return 1;
		}
	}
	return 0;

}

int busca_total_vendas_1(Hash* ha, int codigo, struct venda* val) {
	if(ha == NULL) {
		printf("\nHash Esta vazio");
	}
	int pos = chaveDivisao(codigo, ha->TABLE_SIZE);
	if(ha->atributos[pos] == NULL) {
		return 0;
	}
	*val = *(ha->atributos[pos]);
	//printf("O codigo e: %i", val->total);
	return val->total;

}

int busca_total_vendas_2(Hash* ha, int codigo, struct venda* val) {
	if(ha == NULL) {
		printf("\nHash Esta vazio");
	}
	int pos = chaveDivisao(codigo, ha->TABLE_SIZE);
	if(ha->atributos[pos] == NULL) {
		return 0;
	}
	*val = *(ha->atributos[pos]);
	if(val->ano == 2017 && (val->mes >= 1 && val->mes <= 6)) {
		return val->total;
	} else {
		return 0;
	}
	
}

int busca_total_vendas_3(Hash* ha, int codigo, struct venda* val) {
	if(ha == NULL) {
		printf("\nHash Esta vazio");
	}
	int pos = chaveDivisao(codigo, ha->TABLE_SIZE);
	if(ha->atributos[pos] == NULL) {
		return 0;
	}
	*val = *(ha->atributos[pos]);
	if(val->ano == 2017 && (val->mes == 8 || val->mes == 9 || val->mes == 10 )) {
		return val->total;
	} else {
		return 0;
	}	
}
void menu_principal() {
	printf("\nDigite");
	printf("\n1-Para Inserir no Hash");
	printf("\n2-Resolver Questao 1");
	printf("\n3-Resolver Questao 2");
	printf("\n4-Resolver Questao 3");
	printf("\n0-Sair");
}


int main() {
	struct venda val;
	Hash* ha = criaHash(221);
	int x = 0;
	int escolha = 0;
	float retorno = 0;
	float total_vendas = 0;
	float aux = 0;
	int busca_codigo = 0;

	menu_principal();
	scanf("%i",&escolha);
	fflush(stdin);
	while(escolha!=0) {
		switch(escolha) {
			case 1:
				printf("Digite o codigo da filial (Entre 0 e 201): ");
				scanf("%i", &val.codigo);
				if (val.codigo > 200){
					printf ("\nCodigo Invalido! Digite novamente");
					scanf("%i", &val.codigo);
				}
				printf("\nDigite o total de vendas: ");
				scanf("%f", &val.total);
				printf("\nDigite o ano: ");
				scanf("%i", &val.ano);
				printf("\nDigite o mes (Ex-Mes:8): ");
				scanf("%i", &val.mes);
				printf("\nDigita o nome da filial: ");
				fflush(stdin);
				scanf("%s", val.filial);
				fflush(stdin);
				x = insereHash_EnderAberto(ha,val);
				if(x > 0) {
					printf("\nInserido Com Sucesso");
				}
				break;
			case 2:
				total_vendas = 0;
				aux = 0;
				for(busca_codigo = 11; busca_codigo < 20; busca_codigo++) {
					retorno = busca_total_vendas_1(ha, busca_codigo, &val);
					total_vendas = retorno + aux;
					aux = total_vendas;

				}
				printf("%f", total_vendas);
				break;
			case 3:
				total_vendas = 0;
				aux = 0;
				for(busca_codigo = 11; busca_codigo < 20; busca_codigo++) {
					retorno = busca_total_vendas_2(ha, busca_codigo, &val);
					total_vendas = retorno + aux;
					aux = total_vendas;
				}
				printf("%f", total_vendas);
				break;
			case 4:
				total_vendas = 0;
				aux = 0;
				for(busca_codigo = 1; busca_codigo < 201; busca_codigo++) {
					retorno = busca_total_vendas_3(ha, busca_codigo, &val);
					total_vendas = retorno + aux;
					aux = total_vendas;
				}
				printf("%f", total_vendas);
				break;
		}
		menu_principal();
		scanf("%i",&escolha);
		fflush(stdin);

	}

	system("clear");
	return 0;
}
