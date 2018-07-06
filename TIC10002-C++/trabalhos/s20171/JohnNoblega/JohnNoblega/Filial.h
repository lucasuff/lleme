#pragma once
#include <iostream>
#include <fstream>
#include <string>

using namespace std;


//CLASS FILIAL

class Filial {
private:
    int _cod_filial = 0;
    string _mes_ano = "";
    int _cod_vendedor = 0;
    int _total_vendido = 0;



public:
    Filial();
    Filial(int&, string&, int&, int&);

    //getters

    inline int getCod_Filial() {
        return this->_cod_filial;
    }

    inline string getMes_Ano() {
        return this->_mes_ano;
    }

    inline int getCod_Vendedor() {
        return this->_cod_vendedor;
    }

    inline int getTotal_Vendido() {
        return this->_total_vendido;
    }

    //Setters

    inline void getCod_Filial(int& filial) {
        this->_cod_filial = filial;
    }

    inline void getMes_Ano(string& mes_ano) {
        this->_mes_ano = mes_ano;
    }

    inline void getCod_Vendedor(int& cod_vendedor) {
        this->_cod_vendedor = cod_vendedor;
    }

    inline void getTotal_Vendido(int& total_vendido) {
        this->_total_vendido = total_vendido;
    }


    //Operator
    friend ostream& operator<<(ostream &, const Filial &);


    ~Filial();

};
