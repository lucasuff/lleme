#include<iostream>
#include <fstream>
#include<istream>
#include<sstream>
#include<stdio.h>
#include<stdlib.h>
#include "hash.cpp"

/*! Enumeração para acessos das direções necessárias das subárvores */
enum Direcao
{
    ESQUERDA = 0, DIREITA = 1, IGUAL = 2,
};

/*! Classe de Nó da Árvore AVL implementada para armazenar as listas, valores de
 *  cada nó setor x dia
 */
class NoArvoreAvl
{
public:

    /*! Construtor do nó da árvore recebendo um valor e o fluxo
     */
    NoArvoreAvl( const double valor, Fluxo& fluxo )
    {
        this->m_valor = valor;
        this->m_listaFluxo.inserir( fluxo );
        this->m_subArvore[ESQUERDA] = NULL;
        this->m_subArvore[DIREITA] = NULL;
        this->m_balance = IGUAL;
    }
    
    /*! Destrutor
     */
    ~NoArvoreAvl( )
    {
        /*! Note que a destruição de um nó iniciará uma operação em cadeia em
         * todos os seus filhos. Caso um dos filhos não deva ser apagado, 
         * deve-se setar NULL em sua referência antes de destruir este nó
         */
        delete this->m_subArvore[ESQUERDA];
        delete this->m_subArvore[DIREITA];
    }
    
    unsigned short m_balance;
    
    NoArvoreAvl* m_subArvore[2];
    
    double m_valor;
    ListaFluxo m_listaFluxo;
};

/*! Classe Árvore AVL fornece métodos para criar e manipular os nós */
class ArvoreAvl
{
public:
    /*! Construtor padrão   */
    ArvoreAvl( );
    
    /*! Destrutor padrão    */
    ~ArvoreAvl( );
    
    /*! Limpa a árvore e todos os seus nós
     */
    void Limpar();
    
    /*! Insere um novo fluxo na árvore avl
     */
    void Inserir( const double valor, Fluxo& fluxo, Fluxo& anterior );
    
    /*! Atualiza a arvore avl, excluindo o fluxo antes da modificação
     */
    void Atualizar( Fluxo& anterior );
    
    /*! Remove um nó da árvore avl de acordo com o valor de setor x dia
     */
    bool Remover( const double valor );
   
    /*! Imprime toda a árvore avl
     */
    void Imprimir( );
    
    /*! Imprime a raiz da árvore avl
     */
    void ImprimirRaiz( );
    
    /*! Imprime todos os dados da árvore avl, respeitando a restrição
     */
    void ImprimirDetalhado( const double& restricao = 0, const bool& printLeftRight = false );
    
    /*! Retorna o valor de fluxos setor x dia mínimo da árvore avl
     */
    Fluxo min( NoArvoreAvl *node );
    
    /*! Retorna o valor de fluxos setor x dia máximo da árvore avl
     */
    Fluxo max( NoArvoreAvl *no );

    /*! Raiz da árvore avl
     */
    NoArvoreAvl* m_raiz;
private:

    /*! Rotação dupla respeitando a direção
     */
    void rotacaoDupla( NoArvoreAvl*& no, Direcao direcao );
    
    /*! Rotação simples respeitando a direção
     */
    void rotacaoSimples( NoArvoreAvl*& no, Direcao direcao );
    // Rebalanceamentos
    /*! Atualiza o balanceamento
     */
    void atualizaBalanceamento( NoArvoreAvl* arvore, Direcao direcao );
    
    /*! Insere rebalanceando a árvore avl
     */
    void insereRebalanceando( NoArvoreAvl*& arvore, Direcao direcao, bool& mudou );
    
    /*! Remove rebalanceando a árvore avl
     */
    void removeRebalanceando( NoArvoreAvl*& arvore, Direcao dir, bool& mudou );
    
    /*! Insere o fluxo 
     */
    void inserir( const double valor, Fluxo& fluxo, Fluxo& anterior, NoArvoreAvl*& no, bool& mudou );
    
    /*! Atualiza a árvore avl removendo o antigo fluxo
     */
    void atualizar( Fluxo& anterior, NoArvoreAvl* no, bool* encontrou );
    
    /*! Remove um nó da árvore avl de acordo com o valor de setor x dia
     */
    bool remover( const double valor, NoArvoreAvl*& no, bool& mudou );
    
    bool save( std::ofstream& stream, NoArvoreAvl* node );
    
    /*! Imprime todos os dados da árvore avl, respeitando a restrição
     */
    void imprimirDetalhado( NoArvoreAvl*, const double&, const bool& );
         
    /*! Retorna a direção oposta
     */ 
    inline Direcao oposto( Direcao dir );
};