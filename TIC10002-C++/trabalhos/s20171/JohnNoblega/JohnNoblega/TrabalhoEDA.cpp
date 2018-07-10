// TrabalhoEDA.cpp: define el punto de entrada de la aplicaci�n de consola.
//

#include "stdafx.h"
#include "Sale.h"
#include <stdlib.h>

void menu(Sale& dados) {
    int opcao;

    int cod_inicial;
    int cod_final;

    string data_inicial;
    string data_final;

    int total = 0;

    do {
        system("cls");
        cout << "Menu principal" << endl << endl;
        cout << "Selecione uma opcao" << endl;
        cout << "1 - Pesquisa por Codigo de Filial" << endl;
        cout << "2 - Pesquisa por Data" << endl;
        cout << "3 - Pesquisa por Codigo e Data" << endl;
        cout << "4 - Sair" << endl;
        cout << "Selecao: ";
        cin >> opcao;
        system("cls");

        switch (opcao) {
            case 1:
                cout << "Pesquisa por Codigo de Filial" << endl;
                cout << "Digite o intervalo de Codigos" << endl;
                cout << "Desde: ";
                cin >> cod_inicial;
                cout << "Ate: ";
                cin >> cod_final;
                total = dados.total_searchByCodeInterval(cod_inicial, cod_final);
                cout << "Total Vendido: " << total << endl;
                break;
            case 2:
                cout << "Pesquisa por Data" << endl;
                cout << "Digite o intervalo de datas (Ex.: mes-ano = 24-2017)" << endl;
                cout << "Numeros menores de 10  complete com 0 (Ex.: 03-2017)" << endl;
                cout << "Desde: ";
                cin >> data_inicial;
                cout << "Ate: ";
                cin >> data_final;
                total = dados.total_searchByDateInterval(data_inicial, data_final);
                cout << "Total Vendido: " << total << endl;
                break;
            case 3:
                cout << "Pesquisa por Codigo e Data" << endl;
                //Primeiro o c�digo
                cout << "Digite o intervalo de Codigos" << endl;
                cout << "Desde: ";
                cin >> cod_inicial;
                cout << "Ate: ";
                cin >> cod_final;
                //Depois a data
                cout << "Digite o intervalo de datas" << endl;
                cout << "Digite o intervalo de datas (Ex.: mes-ano = 24-2017)" << endl;
                cout << "Numeros menores de 10  complete com 0 (Ex.: 03-2017)" << endl;
                cout << "Desde: ";
                cin >> data_inicial;
                cout << "Ate: ";
                cin >> data_final;
                total = dados.total_searchByInterval(cod_inicial, cod_final, data_inicial, data_final);
                cout << "Total Vendido: " << total << endl;
                break;
            case 4:
                cout << "Adeus!" << endl;
                break;
            default:
                break;
        }
        system("PAUSE");
    } while (opcao != 4);


}

int main() {
    Sale prova("Dados.txt");
    menu(prova);

    return 0;
}

