#include "Venda.h"

Venda::Venda() {
    filial = 0;
    ano = 0;
    mes = 0;
    codigo_vendedor = 0;
    total_vendido = 0.0f;
    next = 0; // null
}

Venda::Venda(int F, int A, int M, int CV, float TV) {
    filial = F;
    ano = A;
    mes = M;
    codigo_vendedor = CV;
    total_vendido = TV;
    next = 0; // null
}

int Venda::GetFilial() {
    return filial;
}

int Venda::GetAnoMes() {
    return 100 * ano + mes;
}

int Venda::GetCodigoVendedor() {
    return codigo_vendedor;
}

float Venda::GetTotalVendido() {
    return total_vendido;
}

Venda* Venda::GetNext() {
    return next;
}

void Venda::SetNext(Venda *Next) {
    next = Next;
}

