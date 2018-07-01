/*
Para a solução do trabalho se considerou como estrutura de dados: lista simplesmente encadeada.
Especipicamente uma lista de lista. Na hora de criar essas listas se fez de forma ordenada de um
jeito especifico para cada estrutura. Se consideram dois tipos de listas:
Se tem uma lista Nodo, onde cada nodo tem um endereço que aponta a outra lista, e uma
lista venda que contém os objetos vendas com todos os dados refetentes a um reporte de venda.

A. Para o primer inciso a primeira lista nodo se ordena por filiais e cada nodo aponta a uma lista venda.

B. Para o segundo inciso a primeira lista nodo se ordena por filiais e cada nodo aponta a outra lista Nodo
   ordenada por datas, e cada elemento de esta a sua vês aponta a uma terceira lista de venda.

C. Para o terceiro inciso a primeira lista Nodo se ordena por datas e cada nodo aponta a uma lista venda.

Na solução se utiliza o paradigma da POO, em linguaje  C++.

O programa pode ser comprovado a partir de um arquivo txt com o formato indicado.
Na pasta da solução tem um que pode se utilizar.

OBSERVAÇÃO: A solução proposta no trabalho foi baseada em listas encadeadas,
principalmente porque a data de realização se reconhece que não se tinha
conhecimento suficiente para a programação de estruturas que são mais eficientes,
mas mais complexas de implementar. Como é o caso dos ABB que reduzem o tempo
de busca significativamente e além dessa uma árvore AVL garanta que até no pior
dos casos a busca de um elemento seja em O(lg n), mas a implementação é mais
complexa, já que na hora de inserir cada elemento se tem que garantir que a arvore
fique balanceada.*/

//******************************************************************************************

// Declaracão das librerías a utilizar en el main.

#include <iostream>
#include <fstream>
#include <cstring>
#include <sstream>
#include "List.h"
#include "OrderedList.h"

using namespace std;

int main()
{
 /* A seguir se realiza a leitura do arquivo .txt. A leitura se realiza línea a línea do arquivo
    até chegar ao fim do mesmo. */

    ifstream ifs ("vendas.txt", ifstream::in);
    string str;
    int filial, ano, mes, ano_mes, cod_vendedor;
    float total_vendido;
    OrderedList *estrutura_1 = new OrderedList();
    OrderedList *estrutura_2 = new OrderedList();
    OrderedList *estrutura_3 = new OrderedList();
    Node *N, *N2;
    Venda *V1, *V2, *V3;

    while (ifs.peek() != EOF) {
        getline (ifs, str, ',');
        // convert string to int
        stringstream sstr(str);
        sstr >> filial;
        ifs.ignore(2, ' ');

        if (!estrutura_1->ExistValue(filial)) {
            N = new Node(filial);
            estrutura_1->OrderedInsert(N);
        }

        if (!estrutura_2->ExistValue(filial)) {
            N = new Node(filial);
            estrutura_2->OrderedInsert(N);
        }

        getline (ifs, str, '_');
        stringstream sstr2(str);
        sstr2 >> ano;

        getline (ifs, str, ',');
        stringstream sstr3(str);
        sstr3 >> mes;
        ifs.ignore(2, ' ');

        ano_mes = 100 * ano + mes;

        N = estrutura_2->Search(filial);

        if (!N->ExistValue(ano_mes)) {
            N2 = new Node(ano_mes);
            N->OrderedInsert(N2);
        }

        if (!estrutura_3->ExistValue(ano_mes)) {
            N = new Node(ano_mes);
            estrutura_3->OrderedInsert(N);
        }

        getline (ifs, str, ',');
        stringstream sstr4(str);
        sstr4 >> cod_vendedor;
        ifs.ignore(2, ' ');

        getline (ifs, str);
        stringstream sstr5(str);
        sstr5 >> total_vendido;

/* A variável V representa cada objeto venda.
   Neste caso se tem V1, V2, e V3 um por cada estrutura por comodidade na hora de programar */

        V1 = new Venda(filial, ano, mes, cod_vendedor, total_vendido);
        V2 = new Venda(filial, ano, mes, cod_vendedor, total_vendido);
        V3 = new Venda(filial, ano, mes, cod_vendedor, total_vendido);

/* Através do método InsertVenda se adicionam as vendas y se forma cada estrutura de dado*/
        estrutura_1->InsertVenda_1(V1);
        estrutura_2->InsertVenda_2(V2);
        estrutura_3->InsertVenda_3(V3);
    }

    ifs.close();

 /* Uma vez que estão criadas as estruturas de dados, estas se amostram.
    Esta operação não se precisa para a solução do trabalho, mas se adicionou
    para comprovar que foram criadas corretamente.
    Se chama ao método PrintList para imprimir cada estrutura de dado. */

    cout << "\nEstrutura # 1:\n";
    estrutura_1->PrintList();

    cout << "\nEstrutura # 2:\n";
    estrutura_2->PrintList2();

    cout << "\nEstrutura # 3:\n";
    estrutura_3->PrintList();

 /* A continuação se amostra a resposta de cada inciso do trabalho.
    Para isso se emprega o método TotalVendas. */

    cout << "\nTotal de vendas das filiais com códigos entre 10 y 20:\n"
         << estrutura_1->TotalVendas(10, 20) << endl;

    cout << "\nTotal de vendas das filiais com códigos entre 10 y 20 \nnos meses de Jan/17 até Jun/17:\n"
         << estrutura_2->TotalVendas(10, 20, 201701, 201706) << endl;

    cout << "\nTotal de vendas de todas as filiais nos meses de Ago/17 até Out/17:\n"
         << estrutura_3->TotalVendas(201708, 201710) << endl;

 /* Ao final se eliminam as estruturas de dados para liberar o espaço de memória utilizado
    para a criação das estruturas. */

    delete estrutura_1;
    delete estrutura_2;
    delete estrutura_3;

    return 0;
}
