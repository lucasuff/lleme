#include<iostream>
#include <fstream>
#include<stdio.h>
#include<stdlib.h>
#include <string>
#include "arvoreAvl.cpp"

/*! Função de apoio para contar quantos caracteres tem em uma string
 *  Utilizada para verificar quantas vírgulas tem em uma string para setar os valores
 *  lidos da instância
 */
int contaChar( std::string s, char c )
{
    int res = 0;
    for ( int i = 0; i < s.size( ); i++ )
        if ( s[i] == c )
            res++;
    return (res );
}

/*! Separa uma string de acordo com o caractere enviado por parâmetro
 *  Utilizada para obter cada Setor, Rodovia, Dia e Valor dos Fluxos das instâncias
 */
std::string separaString( std::string s, char c, int pos )
{
    std::string res = "";
    int contador = 0;
    for ( int i = 0; i < s.size( ); i++ )
    {
        /*! Caso a letra atual for igual ao caractere de separação e for a posição
         *  desejada para retornar, eu paro o laço, caso não seja a posição
         *  é resetado a string de retorno para obter a próxima string separada
         */
        if ( s[i] == c )
        {
            contador++;
            if ( contador == pos )
                break;
            res = "";
            continue;
        }
        res += s[i];
    }
    return (res );
}

int main( )
{
    TabelaHash tabelaHash;
    ArvoreAvl arvore;
    double restricao = 0;

    std::string nome; // Variavel para inserção das instâncias
    std::string repete = "N"; //Variavel para repetir inserção
    do
    {
        std::cout << "Digite o nome da instância: ";
        std::cin >> nome;

        std::ifstream arq;
        arq.open( nome.c_str( ), std::ifstream::in );
        if ( arq.is_open( ) )
        {
            std::string linha;
            //Realiza a leitura por linha do arquivo
            while ( getline( arq, linha ) )
            {
                //Separa cada atributor pelo ';' presente na instânca
                int qtd = contaChar( linha, ',' );
                std::string setor = separaString( linha, ',', 1 );
                std::string rodovia = separaString( linha, ',', 2 );
                int dia = atoi( separaString( linha, ',', 3 ).c_str( ) );
                float valor = atof( separaString( linha, ',', 4 ).c_str( ) );

                /*! Cria um novo Fluxo a partir dos dados obtidos pela linha
                 *  da instância
                 */
                Fluxo fluxo( setor, rodovia, dia, ( double ) valor );
                /*! Insere ou atualiza o Fluxo na tabela Hash
                 *  Caso seja inserido, é retornado um Fluxo Anterior nulo
                 *      com valor igual a -1
                 *  Caso seja atualizado, o Fluxo anterior é retornado
                 */
                Fluxo anterior = tabelaHash.inserir_hash( fluxo );
                /*! Se o Fluxo anterior não é nulo, significa que houve uma atualização
                 *  na tabela Hash, portanto, é atualizado ( removido ) na Árvore AVL também
                 */
                if ( anterior.valor( ) != -1 )
                {
                    arvore.Atualizar( anterior );
                }
                /*! Insere o novo Fluxo na Árvore AVL   */
                arvore.Inserir( fluxo.valor( ), fluxo, anterior );
            }
            arq.close( );
        }

        Fluxo min = arvore.min( arvore.m_raiz ); //Procura pelo minimo valor na arvore
        Fluxo max = arvore.max( arvore.m_raiz ); //Procura pelo maximo valor na arvore

        /*! Efetua a conta para obter a restrição   */
        double delta = max.valor( ) - min.valor( );
        restricao = min.valor( ) + ( 0.8 * delta );

        //Verifica com usuario se é necessaria a impressão atual do relatorio, se sim, imprime apartir da restrição atual.
        std::string imprime = "N";
        std::cout << "\nDeseja gerar relatorio atual? (S/N)" << std::endl;
        std::cin >> imprime;
        if ( imprime == "s" || imprime == "S" )
        {
            std::cout << "Restricão: " << restricao << std::endl << std::endl;
            arvore.ImprimirDetalhado( restricao );
        }

        //Verifica com usuario se deseja inserir uma nova instância.
        std::cout << "\nDeseja adicionar nova instância? (S/N)" << std::endl;
        std::cin >> repete;

    } while ( repete == "s" || repete == "S" );

    // Imprime fluxo final ao sair do programa.
    std::cout << "\nFluxo final" << std::endl;
    std::cout << "Restrição: " << restricao << std::endl << std::endl;
    arvore.ImprimirDetalhado( restricao );

    return (0 );
}
