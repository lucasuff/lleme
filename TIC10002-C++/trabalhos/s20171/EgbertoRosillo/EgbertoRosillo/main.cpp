/*
Para a solu��o do trabalho se considerou como estrutura de dados: lista simplesmente encadeada.
Especipicamente uma lista de lista. Na hora de criar essas listas se fez de forma ordenada de um
jeito especifico para cada estrutura. Se consideram dois tipos de listas:
Se tem uma lista Nodo, onde cada nodo tem um endere�o que aponta a outra lista, e uma
lista venda que cont�m os objetos vendas com todos os dados refetentes a um reporte de venda.

A. Para o primer inciso a primeira lista nodo se ordena por filiais e cada nodo aponta a uma lista venda.

B. Para o segundo inciso a primeira lista nodo se ordena por filiais e cada nodo aponta a outra lista Nodo
   ordenada por datas, e cada elemento de esta a sua v�s aponta a uma terceira lista de venda.

C. Para o terceiro inciso a primeira lista Nodo se ordena por datas e cada nodo aponta a uma lista venda.

Na solu��o se utiliza o paradigma da POO, em linguaje  C++.

O programa pode ser comprovado a partir de um arquivo txt com o formato indicado.
Na pasta da solu��o tem um que pode se utilizar.

OBSERVA��O: A solu��o proposta no trabalho foi baseada em listas encadeadas,
principalmente porque a data de realiza��o se reconhece que n�o se tinha
conhecimento suficiente para a programa��o de estruturas que s�o mais eficientes,
mas mais complexas de implementar. Como � o caso dos ABB que reduzem o tempo
de busca significativamente e al�m dessa uma �rvore AVL garanta que at� no pior
dos casos a busca de um elemento seja em O(lg n), mas a implementa��o � mais
complexa, j� que na hora de inserir cada elemento se tem que garantir que a arvore
fique balanceada.*/

//******************************************************************************************

// Declarac�o das librer�as a utilizar en el main.

#include <iostream>
#include <fstream>
#include <cstring>
#include <sstream>
#include "List.h"
#include "OrderedList.h"

using namespace std;

int main() {
    /* A seguir se realiza a leitura do arquivo .txt. A leitura se realiza l�nea a l�nea do arquivo
       at� chegar ao fim do mesmo. */

    ifstream ifs("vendas.txt", ifstream::in);
    string str;
    int filial, ano, mes, ano_mes, cod_vendedor;
    float total_vendido;
    OrderedList *estrutura_1 = new OrderedList();
    OrderedList *estrutura_2 = new OrderedList();
    OrderedList *estrutura_3 = new OrderedList();
    Node *N, *N2;
    Venda *V1, *V2, *V3;

    while (ifs.peek() != EOF) {
        getline(ifs, str, ',');
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

        getline(ifs, str, '_');
        stringstream sstr2(str);
        sstr2 >> ano;

        getline(ifs, str, ',');
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

        getline(ifs, str, ',');
        stringstream sstr4(str);
        sstr4 >> cod_vendedor;
        ifs.ignore(2, ' ');

        getline(ifs, str);
        stringstream sstr5(str);
        sstr5 >> total_vendido;

        /* A vari�vel V representa cada objeto venda.
           Neste caso se tem V1, V2, e V3 um por cada estrutura por comodidade na hora de programar */

        V1 = new Venda(filial, ano, mes, cod_vendedor, total_vendido);
        V2 = new Venda(filial, ano, mes, cod_vendedor, total_vendido);
        V3 = new Venda(filial, ano, mes, cod_vendedor, total_vendido);

        /* Atrav�s do m�todo InsertVenda se adicionam as vendas y se forma cada estrutura de dado*/
        estrutura_1->InsertVenda_1(V1);
        estrutura_2->InsertVenda_2(V2);
        estrutura_3->InsertVenda_3(V3);
    }

    ifs.close();

    /* Uma vez que est�o criadas as estruturas de dados, estas se amostram.
       Esta opera��o n�o se precisa para a solu��o do trabalho, mas se adicionou
       para comprovar que foram criadas corretamente.
       Se chama ao m�todo PrintList para imprimir cada estrutura de dado. */

    cout << "\nEstrutura # 1:\n";
    estrutura_1->PrintList();

    cout << "\nEstrutura # 2:\n";
    estrutura_2->PrintList2();

    cout << "\nEstrutura # 3:\n";
    estrutura_3->PrintList();

    /* A continua��o se amostra a resposta de cada inciso do trabalho.
       Para isso se emprega o m�todo TotalVendas. */

    cout << "\nTotal de vendas das filiais com c�digos entre 10 y 20:\n"
            << estrutura_1->TotalVendas(10, 20) << endl;

    cout << "\nTotal de vendas das filiais com c�digos entre 10 y 20 \nnos meses de Jan/17 at� Jun/17:\n"
            << estrutura_2->TotalVendas(10, 20, 201701, 201706) << endl;

    cout << "\nTotal de vendas de todas as filiais nos meses de Ago/17 at� Out/17:\n"
            << estrutura_3->TotalVendas(201708, 201710) << endl;

    /* Ao final se eliminam as estruturas de dados para liberar o espa�o de mem�ria utilizado
       para a cria��o das estruturas. */

    delete estrutura_1;
    delete estrutura_2;
    delete estrutura_3;

    return 0;
}
