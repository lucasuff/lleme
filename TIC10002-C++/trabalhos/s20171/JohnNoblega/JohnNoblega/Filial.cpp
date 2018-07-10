#include "stdafx.h"
#include "Filial.h"

Filial::Filial() {

}

Filial::Filial(int& cod_filial, string& mes_ano, int& cod_vendedor, int& total_vendido) {
    this->_cod_filial = cod_filial;
    this->_mes_ano = mes_ano;
    this->_cod_vendedor = cod_vendedor;
    this->_total_vendido = total_vendido;
}

Filial::~Filial() {

}

ostream& operator<<(ostream &output, const Filial &f) {
    output << "Cod_filial: " << f._cod_filial << "\tMes_ano: " << f._mes_ano << "\tCod_Vendedor: "
            << f._cod_vendedor << "\tTotal_vendido: " << f._total_vendido;
    return output;
}