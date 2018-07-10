
// A clase venda cont�m o objeto venda

#include "Venda.h"

Venda::Venda() {
    filial = 0;
    ano = 0;
    mes = 0;
    cod_vendedor = 0;
    total_vendido = 0.0f;
    next = 0; // null
}

Venda::Venda(int F, int A, int M, int CV, float TV) {
    filial = F;
    ano = A;
    mes = M;
    cod_vendedor = CV;
    total_vendido = TV;
    next = 0; // null
}

int Venda::GetFilial() {
    return filial;
}

int Venda::GetAno() {
    return ano;
}

int Venda::GetMes() {
    return mes;
}

int Venda::GetDate() {
    return 100 * ano + mes;
}

int Venda::GetCodVendedor() {
    return cod_vendedor;
}

float Venda::GetTotalVendido() {
    return total_vendido;
}

void Venda::SetFilial(int F) {
    if (F > 0)
        filial = F;
}

Venda* Venda::GetNext() {
    return next;
}

void Venda::SetNext(Venda *Next) {
    next = Next;
}

