#include "Lista.h"
#include <iostream>

using namespace std;

Lista::Lista()
{
    head = 0; // NULL
}

Lista::~Lista()
{
    Venda* x;

    while ( !IsEmpty() ) {
        x = head;
        head = x->GetNext();
        delete x;
    }
}

void Lista::Insert( Venda* x )
{
    x->SetNext( head );
    head = x;
}

bool Lista::IsEmpty()
{
    return ( head == 0 );
}

void Lista::PrintList()
{
    Venda *x = head;

    while ( x != 0 ) {
        cout << x->GetCodigoVendedor() << " --> ";
        x = x->GetNext();
    }

    cout << "null";
}

void Lista::PrintList2()
{
    Venda* x = head;
    cout << " (";

    while ( x != 0 ) {
        cout << " --> " << x->GetCodigoVendedor();
        x = x->GetNext();
    }

    cout << " --> null )";
}

float Lista::TotalVendas()
{
    float total = 0.0f;
    Venda *x = head;

    while ( x != 0 ) {
        total += x->GetTotalVendido();
        x = x->GetNext();
    }

    return total;
}
