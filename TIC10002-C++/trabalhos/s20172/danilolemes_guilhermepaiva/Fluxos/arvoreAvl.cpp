#include "arvoreAvl.h"

/*! Construtor padrão   */
ArvoreAvl::ArvoreAvl( )
{
    this->m_raiz = NULL;
}

/*! Destrutor padrão   */
ArvoreAvl::~ArvoreAvl( )
{
    this->Limpar( );
}

/*! Metódo para efetuar uma limpeza na Árvore AVL*/
void ArvoreAvl::Limpar( )
{
    delete m_raiz;
    this->m_raiz = NULL;
}

/*! Realiza uma rotaçao simples numa determinada direção    */
void ArvoreAvl::rotacaoSimples( NoArvoreAvl*& no, Direcao direcao )
{
    int oposto = this->oposto( direcao );
    NoArvoreAvl* filho = no->m_subArvore[direcao];
    no->m_subArvore[direcao] = filho->m_subArvore[oposto];
    filho->m_subArvore[oposto] = no;
    no = filho;
}

/*! Realiza uma rotação dupla numa determinada direção  */
void ArvoreAvl::rotacaoDupla( NoArvoreAvl*& no, Direcao direcao )
{
    int oposto = this->oposto( direcao );
    NoArvoreAvl* filho = no->m_subArvore[direcao]->m_subArvore[oposto];
    no->m_subArvore[direcao]->m_subArvore[oposto] = filho->m_subArvore[direcao];
    filho->m_subArvore[direcao] = no->m_subArvore[direcao];
    no->m_subArvore[direcao] = filho;
    filho = no->m_subArvore[direcao];
    no->m_subArvore[direcao] = filho->m_subArvore[oposto];
    filho->m_subArvore[oposto] = no;
    no = filho;
}

// Retorna a direção oposta à direção dada

inline Direcao ArvoreAvl::oposto( Direcao direcao )
{
    return (direcao == DIREITA ) ? ESQUERDA : DIREITA;
}

// Atualiza os fatores de balanceamento após uma rotação

void ArvoreAvl::atualizaBalanceamento( NoArvoreAvl* no, Direcao direcao )
{
    int oposto = this->oposto( direcao );
    int balanceamento = no->m_subArvore[direcao]->m_subArvore[oposto]->m_balance;

    if ( balanceamento == direcao )
    {
        no->m_balance = IGUAL;
        no->m_subArvore[direcao]->m_balance = oposto;
    }
    else if ( balanceamento == oposto )
    {
        no->m_balance = direcao;
        no->m_subArvore[direcao]->m_balance = IGUAL;
    }
    else
    {
        no->m_balance = no->m_subArvore[direcao]->m_balance = IGUAL;
    }
    no->m_subArvore[direcao]->m_subArvore[oposto]->m_balance = IGUAL;
}

/*! Efetua o rebalancamento após uma operação de inserção   */
void ArvoreAvl::insereRebalanceando( NoArvoreAvl*& no, Direcao direcao, bool& mudou )
{
    int oposto = this->oposto( direcao );
    /*! Se o fator de balancamento do nó era igual a direçao em que houve
     * a inserção
     */
    if ( no->m_balance == direcao )
    {
        /*! ou seja, o nó foi inserido na subárvore de maior altura
         * Temos 2 casos:
         */
        if ( no->m_subArvore[direcao]->m_balance == direcao )
        {
            no->m_subArvore[direcao]->m_balance = 2;
            no->m_balance = IGUAL;
            rotacaoSimples( no, direcao );
        }
        else
        {
            /*! Seu filho estava equilibrado ou pendendo para o lado oposto */
            atualizaBalanceamento( no, direcao );
            rotacaoDupla( no, direcao );
        }
        mudou = false;
    }
        /*! Já se foi exatamento o oposto   */
    else if ( no->m_balance == oposto )
    {
        /*! O nó agora está balancamento    */
        no->m_balance = 2;
        mudou = false;
    }
    else
    {
        /*! Se não, o nó já estava equilibrado   */
        no->m_balance = direcao;
    }
}

/*! Efetua o rebalancamento após uma operação de remoção    */
void ArvoreAvl::removeRebalanceando( NoArvoreAvl*& no, Direcao direcao, bool& mudou )
{
    Direcao oposto = this->oposto( direcao );
    if ( no->m_balance == direcao )
    {
        no->m_balance = IGUAL;
    }
    else if ( no->m_balance == oposto )
    {
        if ( no->m_subArvore[oposto]->m_balance == oposto )
        {
            no->m_subArvore[oposto]->m_balance = IGUAL;
            no->m_balance = IGUAL;
            rotacaoSimples( no, oposto );
        }
        else if ( no->m_subArvore[oposto]->m_balance == IGUAL )
        {
            no->m_subArvore[oposto]->m_balance = direcao;
            rotacaoSimples( no, oposto );
        }
        else
        {
            atualizaBalanceamento( no, oposto );
            rotacaoDupla( no, oposto );
        }
        mudou = false;
    }
    else
    {
        no->m_balance = oposto;
        mudou = false;
    }
}

/*! Insere um novo nó de fluxo setor x dia na árvore avl*/
void ArvoreAvl::Inserir( const double valor, Fluxo& fluxo, Fluxo& anterior )
{
    bool mudou = false;
    this->inserir( valor, fluxo, anterior, this->m_raiz, mudou );
}

/*! Método recursivo para inserir na árvore avl*/
void ArvoreAvl::inserir( const double valor, Fluxo& fluxo, Fluxo& anterior, NoArvoreAvl*& no, bool& mudou )
{
    if ( no == NULL )
    {
        no = new NoArvoreAvl( valor, fluxo );
        mudou = true;
    }
    else if ( no->m_listaFluxo.m_raiz->m_fluxo.valor( ) == fluxo.valor( ) )
    {
        no->m_listaFluxo.inserir( fluxo );
    }
    else
    {
        Direcao direcao = ( valor > no->m_valor ) ? DIREITA : ESQUERDA;
        mudou = false;
        inserir( valor, fluxo, anterior, no->m_subArvore[direcao], mudou );
        if ( mudou )
        {
            this->insereRebalanceando( no, direcao, mudou );
        }
    }
}

/*! Atualiza a árvore avl   */
void ArvoreAvl::Atualizar( Fluxo& anterior )
{
    bool encontrou = false;
    this->atualizar( anterior, this->m_raiz, &encontrou );
}

/*! Método recursivo para atualizar a árvore avl*/
void ArvoreAvl::atualizar( Fluxo& anterior, NoArvoreAvl* no, bool* encontrou )
{
    if ( no != NULL )
    {
        if ( no->m_valor == anterior.valor( ) )
        {
            ListaFluxo* lista = &no->m_listaFluxo;
            bool removeNo = lista->m_raiz->proximo == NULL;

            *encontrou = ListaFluxo::remover( lista, anterior );
            if ( *encontrou )
            {
                if ( removeNo )
                    Remover( anterior.valor( ) );
            }
        }
        if ( !*encontrou )
        {
            Direcao direcao = ( anterior.valor( ) > no->m_valor ) ? DIREITA : ESQUERDA;
            atualizar( anterior, no->m_subArvore[ direcao ], encontrou );
        }
    }
}

/*! Remove o nó na árvore que contem o valor do fluxo   */
bool ArvoreAvl::Remover( const double valor )
{
    bool mudou = false;
    return this->remover( valor, this->m_raiz, mudou );
}

/*! Método recursivo para remover   */
bool ArvoreAvl::remover( const double valor, NoArvoreAvl*& no, bool& mudou )
{
    bool sucesso = false;

    if ( no == NULL )
    {
        mudou = false;
        return false;
    }
    else if ( valor == no->m_valor )
    {
        if ( no->m_subArvore[ESQUERDA] != NULL && no->m_subArvore[DIREITA] != NULL )
        {
            NoArvoreAvl* substitute = no->m_subArvore[ESQUERDA];
            while ( substitute->m_subArvore[DIREITA] != NULL )
            {
                substitute = substitute->m_subArvore[DIREITA];
            }

            /*! Atualiza os valores e a lista de nós para a nova posição    */
            no->m_valor = substitute->m_valor;
            no->m_listaFluxo = substitute->m_listaFluxo;
            sucesso = remover( no->m_valor, no->m_subArvore[ESQUERDA], mudou );
            if ( mudou )
            {
                removeRebalanceando( no, ESQUERDA, mudou );
            }
        }
        else
        {
            NoArvoreAvl* aux = no;
            Direcao direcao = ( no->m_subArvore[ESQUERDA] == NULL ) ? DIREITA : ESQUERDA;
            no = no->m_subArvore[direcao];
            aux->m_subArvore[ESQUERDA] = NULL;
            aux->m_subArvore[DIREITA] = NULL;
            delete aux;
            mudou = true;
        }
        return true;
    }
    else
    {
        Direcao direcao = ( valor > no->m_valor ) ? DIREITA : ESQUERDA;
        if ( no->m_subArvore[direcao] != NULL )
        {
            sucesso = this->remover( valor, no->m_subArvore[direcao], mudou );
        }
        else
        {
            mudou = false;
            return false;
        }
        if ( mudou )
        {
            this->removeRebalanceando( no, direcao, mudou );
        }
        return sucesso;
    }
}

void ArvoreAvl::ImprimirRaiz( )
{
    std::cout << "Root: " << this->m_raiz->m_valor << std::endl;
}

void ArvoreAvl::ImprimirDetalhado( const double& restricao, const bool& imprimirLados )
{
    this->imprimirDetalhado( this->m_raiz, restricao, imprimirLados );
}
// Imprime detalhes sobre cada nó da árvore,
//  percorrendo-a em ordem (recursivo).

void ArvoreAvl::imprimirDetalhado( NoArvoreAvl* no, const double& restricao, const bool& imprimirLados )
{
    if ( no != NULL )
    {
        if ( imprimirLados )
        {
            if ( no->m_subArvore[ESQUERDA] != NULL )
                std::cout << "- Left: " << no->m_subArvore[ESQUERDA]->m_valor << std::endl;
            if ( no->m_subArvore[DIREITA] != NULL )
                std::cout << "- Right: " << no->m_subArvore[DIREITA]->m_valor << std::endl;
            std::cout << std::endl;
        }
        if( restricao < no->m_valor )
            this->imprimirDetalhado( no->m_subArvore[ESQUERDA], restricao, imprimirLados );
        /*! Caso o valor do nó de fluxo seja MAIOR que a impressão */
        if ( no->m_valor > restricao )
            no->m_listaFluxo.imprimir( );
        this->imprimirDetalhado( no->m_subArvore[DIREITA], restricao, imprimirLados );
    }
}

Fluxo ArvoreAvl::min( NoArvoreAvl* no )
{
    if ( no->m_subArvore[ ESQUERDA ] == NULL )
        return no->m_listaFluxo.m_raiz->m_fluxo;
    else
        return min( no->m_subArvore[ ESQUERDA ] );
}

Fluxo ArvoreAvl::max( NoArvoreAvl* no )
{
    if ( no->m_subArvore[ DIREITA ] == NULL )
        return no->m_listaFluxo.m_raiz->m_fluxo;
    else
        return max( no->m_subArvore[ DIREITA ] );
}