#include "Node.h"
#include <iostream>

Node::Node()
{
    key = 0;
    left = 0;
    right = 0;
    parent = 0;
    root_datas = 0;
    vendas = new Lista();
}

Node::Node( int k )
{
    key = k;
    left = 0;
    right = 0;
    parent = 0;
    root_datas = 0;
    vendas = new Lista();
}

Node::~Node()
{
    delete vendas;
    DeleteTree( root_datas );
}

void Node::DeleteTree( Node* x )
{
    // Recorrido em pos ordem
    if ( x != 0 ) {
        DeleteTree( x->GetLeft() );
        DeleteTree( x->GetRight() );
        delete x;
    }
}

void Node::InsertVenda( Venda* x )
{
    vendas->Insert( x );
}

float Node::TotalVendas()
{
    return vendas->TotalVendas();
}

int Node::GetKey()
{
    return key;
}

Node* Node::GetLeft()
{
    return left;
}

void Node::SetLeft( Node* x )
{
    left = x;
}

Node* Node::GetRight()
{
    return right;
}

void Node::SetRight( Node* x )
{
    right = x;
}

Node* Node::GetParent()
{
    return parent;
}

void Node::SetParent(Node* x)
{
    parent = x;
}

Node* Node::GetRootDatas()
{
    return root_datas;
}

Node* Node::Search( int k )
{
    Node* x = root_datas;

    while ( x != 0 && k != x->GetKey() ) {
        if ( k < x->GetKey() )
            x = x->GetLeft();
        else
            x = x->GetRight();
    }

    return x;
}

void Node::Insert( Node* z )
{
    Node* y = 0;
    Node* x = root_datas;

    while ( x != 0 ) {
        y = x;

        if ( z->GetKey() < x->GetKey() )
            x = x->GetLeft();
        else
            x = x->GetRight();
    }

    z->SetParent( y );

    if ( y == 0 )
        root_datas = z;
    else if ( z->GetKey() < y->GetKey() )
        y->SetLeft( z );
    else
        y->SetRight( z );
}

void Node::PrintList()
{
    vendas->PrintList();
}

void Node::PreorderTreeWalk( Node* x, int nivel )
{
    for ( int i = 1; i < nivel; i++ )
        cout << "\t";

    if ( x != 0 ) {
        cout << x->GetKey();
        x->vendas->PrintList2();
        cout << endl;
        PreorderTreeWalk( x->GetRight(), nivel + 1 );
        PreorderTreeWalk( x->GetLeft(), nivel + 1 );
    }
    else
        cout << "null\n";
}

void Node::PrintTree( int nivel )
{
    PreorderTreeWalk( root_datas, nivel );
}
