#include "fluxo.h"

/*! Retorna o setor do Fluxo    */
std::string Fluxo::setor( )
{
    return (this->m_setor );
}

/*! Retorna a rodovia do Fluxo   */
std::string Fluxo::rodovia( )
{
    return (this->m_rodovia );
}

/*! Retorna o dia do Fluxo   */
int Fluxo::dia( )
{
    return (m_dia );
}

/*! Retorna o valor do Fluxo    */
double Fluxo::valor( )
{
    return (m_valor );
}

/*! Incrementa o valor do Fluxo para o caso do Fluxo ser o mesmo    */
void Fluxo::incValor( double valor )
{
    this->m_valor += valor;
}

/*! Retorna uma chave ( setor + dia ) para encontrar a posição da tabela
 *  Hash
 */
std::string Fluxo::chave( )
{
    /*! Concatena o setor e o dia utilizando o std::stringstream    */
    std::stringstream sstm;
    sstm << this->setor( ) << this->dia( );
    return (sstm.str( ) );
}

/*! Operator de igual entre duas classes Fluxo  */
bool Fluxo::operator==( Fluxo& fluxo )const
{
    if ( this->m_setor != fluxo.setor( ) ) return (false );
    if ( this->m_rodovia != fluxo.rodovia( ) ) return (false );
    if ( this->m_dia != fluxo.dia( ) ) return (false );
    if ( this->m_valor != fluxo.valor( ) ) return (false );
    /*! Retorna verdadeiro somente se todos os atributos de ambos os Fluxos
     *  forem iguais
     */
    return (true );
}

/*! Operator de atribuição entre duas classes Fluxo  */
void Fluxo::operator=( Fluxo& fluxo )
{
    this->m_setor = fluxo.setor( );
    this->m_rodovia = fluxo.rodovia( );
    this->m_dia = fluxo.dia( );
    this->m_valor = fluxo.valor( );
}

/*ListaFluxo*/

/*! Imprime a lista de fluxos
 */
void ListaFluxo::imprimir( )
{
    NoFluxo *aux = this->m_raiz;

    /*! Verifica se o primeiro nó não é nulo, caso não seja, imprime o cabeçalho    */
    if ( aux != NULL )
        std::cout << "Fluxo: " << aux->m_fluxo.valor( ) << std::endl;
    /*! Percorre a lista até o final, imprimindo os setores e os dias   */
    while ( aux != NULL )
    {
        std::cout << "Setor: " << aux->m_fluxo.setor( );
        //std::cout << "Rodovia: " << tabelaHash.hash[i].lista.raiz->_fluxo.rodovia() << std::endl;
        std::cout << " - Dia: " << aux->m_fluxo.dia( ) << std::endl;
        //std::cout << "Fluxo: " << aux->_fluxo.valor() << std::endl;
        //std::cout << std::endl << "Min: " << aux->min() << std::endl;
        //std::cout << "Max: " << aux->max() << std::endl;			
        aux = aux->proximo;
    }
    std::cout << std::endl;
}

/*! Retorna true se a lista estiver vazia, false caso contrário
 */
bool ListaFluxo::vazia( )
{
    return (this->m_raiz == NULL );
}

/*! Retorna um novo nó com os dados do parâmetro fluxo
 */
NoFluxo* ListaFluxo::novoNo( Fluxo fluxo )
{
    /*! Efetua a alocação de um novo NoFluxo    */
    NoFluxo *no = ( NoFluxo* ) malloc( sizeof (NoFluxo ) );
    if ( no == NULL )
        return (NULL );
    no = new NoFluxo( fluxo );
    return (no );
}

/*! Insere um novo fluxo no início da lista
 */
bool ListaFluxo::inserir( Fluxo fluxo )
{
    NoFluxo *aux = this->m_raiz;
    NoFluxo *novo = this->novoNo( fluxo );
    if ( novo == NULL ) return (false );
    /*! Insere um Fluxo no ínicio da lista de Fluxos, visando um melhor desempenho,
     *  as inserções são feitas no início
     */
    novo->proximo = this->m_raiz;
    this->m_raiz = novo;
    return (true );
}

/*! Remove o fluxo da lista
 */
bool ListaFluxo::remover( ListaFluxo* lista, Fluxo& fluxo )
{
    /*! É criado um auxiliar para percorrer a lista, e um anterior para setar o 
     *  anterior ao próximo Fluxo a ser removido
     */
    NoFluxo *aux = lista->m_raiz;
    NoFluxo *ant = aux;
    /*! Percorre a lista até o final*/
    while ( aux != NULL )
    {
        /*! Somente remove caso seja o mesmo setor dia  */
        if ( fluxo.setor( ) == aux->m_fluxo.setor( ) && fluxo.dia( ) == aux->m_fluxo.dia( ) )
        {
            /*! O anterior do Fluxo a ser removido, aponta para o próximo a ser removido,
             *  após isso, é liberado a memória do Fluxo removido
             */
            ant->proximo = aux->proximo;
            aux->proximo = NULL;
            free( aux );
            return ( true );
        }

        ant = aux;
        aux = aux->proximo;
    }

    return ( false );
}