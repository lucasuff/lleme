#include "fluxo.cpp"

#define SIZE 365

class Hash
{
public:
    ListaFluxo lista;
};

class TabelaHash
{
public:
    Hash m_hash[SIZE];

    /*! Retorna a chave hash do Fluxo de acordo com seu setor x dia
     */
    int funcao_hash( std::string );

    /*! Insere o Fluxo na tabela hash
     */
    Fluxo inserir_hash( Fluxo& );

    /*! Imprime a tabela hash
     */
    //void imprimir_hash( );
};