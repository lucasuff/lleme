#include<iostream>
#include <fstream>
#include<istream>
#include<sstream>
#include<stdio.h>
#include<stdlib.h>

/*! Classe Fluxo para armazenar os valores recebidos pelas instâncias e para 
 *  imprimir o resultado final.
 */
class Fluxo
{
public:

    /* Construtor padrão    */
    Fluxo( )
    {
    };

    /*  Construtor passando todos os valores de um Fluxo individualmente    */
    Fluxo( std::string setor, std::string rodovia, int dia, double valor ) : m_setor( setor ), m_rodovia( rodovia ), m_dia( dia ), m_valor( valor )
    {
    };

    /*! Retorna o setor do Fluxo    */
    std::string setor( );

    /*! Retorna a rodovia do Fluxo   */
    std::string rodovia( );

    /*! Retorna o dia do Fluxo   */
    int dia( );

    /*! Retorna o valor do Fluxo    */
    double valor( );

    /*! Incrementa o valor do Fluxo para o caso do Fluxo ser o mesmo    */
    void incValor( double );

    /*! Retorna uma chave ( setor + dia ) para encontrar a posição da tabela
     *  Hash
     */
    std::string chave( );

    /*! Operator de igual entre duas classes Fluxo  */
    bool operator==( Fluxo& fluxo )const;

    /*! Operator de atribuição entre duas classes Fluxo  */
    void operator=( Fluxo& fluxo );
private:
    std::string m_setor;
    std::string m_rodovia;
    int m_dia;
    double m_valor;
};

class NoFluxo
{
public:

    /*! Construtor padrão   */
    NoFluxo( )
    {
        this->proximo = NULL;
    };

    /*! Construtor passando um Fluxo novo   */
    NoFluxo( Fluxo fluxo )
    {
        this->m_fluxo.operator=( fluxo );
        this->proximo = NULL;
    }

    NoFluxo *proximo;
    Fluxo m_fluxo;
};

class ListaFluxo
{
public:

    /*! Construtor padrão   */
    ListaFluxo( )
    {
        this->m_raiz = NULL;
    }

    /*! Construtor passando um novo Fluxo por parâmetro   */
    ListaFluxo( Fluxo fluxo )
    {
        this->m_raiz = this->novoNo( fluxo );
    }

    NoFluxo *m_raiz;

    /*! Imprime a lista de fluxos
     */
    void imprimir( );

    /*! Retorna true se a lista estiver vazia, false caso contrário
     */
    bool vazia( );

    /*! Retorna um novo nó com os dados do parâmetro fluxo
     */
    NoFluxo *novoNo( Fluxo fluxo );

    /*! Insere um novo fluxo na lista
     */
    bool inserir( Fluxo fluxo );

    /*! Remove o fluxo da lista
     */
    static bool remover( ListaFluxo*, Fluxo& );
};