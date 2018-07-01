#include "hash.h"

/*! Parâmetro s se equivale a setor + dia   */
int TabelaHash::funcao_hash( std::string s )
{
    int res = 0;
    for ( int i = 0; i < s.size( ); i++ )
        res += s[i];
    return (res % SIZE );
}

/* Insere e atualiza a Hash */
Fluxo TabelaHash::inserir_hash( Fluxo& fluxo )
{
    int chave = this->funcao_hash( fluxo.chave( ) );
    /*! Fluxo padrão adotado com valores -1 para tratamento posteriomente   */
    Fluxo anterior( "", "", -1, -1 );

    /*! Caso a tabela hash esteja vazia, apenas insere o fluxo na posição de sua chave  */
    if ( this->m_hash[chave].lista.m_raiz == NULL )
    {
        NoFluxo *novo = new NoFluxo( fluxo );
        this->m_hash[chave].lista.inserir( fluxo );
    }
        /*! Caso contrário
         *      se o fluxo existente possuir o mesmo setor e dia, o valor é incrementado
         *      senão é adicionado o fluxo no final da lista    */
    else
    {
        std::string setor = this->m_hash[chave].lista.m_raiz->m_fluxo.setor( );
        int dia = this->m_hash[chave].lista.m_raiz->m_fluxo.dia( );

        if ( setor == fluxo.setor( ) && dia == fluxo.dia( ) )
        {
            anterior = this->m_hash[chave].lista.m_raiz->m_fluxo;

            /*! Guarda o valor do fluxo antes do incremento, para atualizar no 
             *       próprio fluxo para ser inserido na árvore corretamente  */
            double temp = this->m_hash[chave].lista.m_raiz->m_fluxo.valor( );
            this->m_hash[chave].lista.m_raiz->m_fluxo.incValor( fluxo.valor( ) );
            fluxo.incValor( temp );
        }
        else
        {
            /*! Caso seja setor e dia diferentes, é inserido no final da lista  */
            NoFluxo *aux = this->m_hash[chave].lista.m_raiz->proximo;
            int cont = 0;
            while ( aux != NULL )
            {
                if ( aux->m_fluxo.setor( ) == fluxo.setor( ) && aux->m_fluxo.dia( ) == fluxo.dia( ) )
                {
                    anterior = aux->m_fluxo;

                    double temp = aux->m_fluxo.valor( );

                    aux->m_fluxo.incValor( fluxo.valor( ) );
                    fluxo.incValor( temp );
                    return ( anterior );
                }
                aux = aux->proximo;
            }
            if ( aux == NULL ) this->m_hash[chave].lista.inserir( fluxo );
        }
    }
    return ( anterior );
}