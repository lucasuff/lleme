//############################################################################################
//#### Universidade Federal Fluminense - Programa de P�s-gradua��o em Ci�ncia da Computa��o	##
//#### Estruturas de Dados e Algoritmos (2017.1) - Prof. Luiz Andr� Portes Paes Leme		##
//#### Discente: FABIANO DA GUIA ROCHA														##
//############################################################################################

#include <string.h>
#include <stdio.h>
#include "lib.h"

//#### C�digo principal ####

void main() {
    int filial, data, mes, ano, codVendedor;
    float totalVendido;

    struct arvore *arvFilial;
    struct arvore2 *arvAno;

    FILE *arquivo;

    if ((arquivo = fopen("entrada.txt", "r")) == NULL) {
        printf("Erro ao abrir o arquivo.\n");
    }
    else {
        printf("#################### Carregando dados na Estrutura ####################\n\n");
        while (!feof(arquivo)) {
            fscanf(arquivo, "%d[^,]", &filial);
            fseek(arquivo, +1, SEEK_CUR);
            fscanf(arquivo, "%d[^,]", &data);
            mes = data % 100;
            ano = data / 100;
            fseek(arquivo, +1, SEEK_CUR);
            fscanf(arquivo, "%d[^,]", &codVendedor);
            fseek(arquivo, +1, SEEK_CUR);
            fscanf(arquivo, "%f[^\n]", &totalVendido);

            printf("Lido: %d,%d,%d,%.2f - ", filial, data, codVendedor, totalVendido);

            arvFilial = buscar(raiz, filial);

            if (arvFilial) {
                printf("Status: Filial %d ja encontra-se na AVL\n", arvFilial->n);
                arvFilial->totalVendido += totalVendido;
                arvAno = buscar2(ano, arvFilial->auxiliar);
                if (arvAno) arvAno->vendasAnoMes[mes - 1] += totalVendido;
                else {
                    insere2(arvFilial->auxiliar, ano, mes, totalVendido);
                }
            } else {
                insere(filial, ano, mes, totalVendido);
                printf("Status: Filial %i Inserida na AVL\n", filial);
            }
        }
        printf("\n\n########################## Dados Carregados! ##########################\n\n");
        fclose(arquivo);
    }

    printf("\n");
    system("pause");
    system("cls");

    //Chamada as Fun��es relativas as consultas
    consulta_01();
    consulta_02();
    consulta_03();

    liberaAVL(arvFilial);

    system("pause");
}
