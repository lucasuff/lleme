/* 
 * File:   main.cpp
 * Author: Carlos Alberto Ribeiro da Fonseca
 *
 * Created on 16 de Junho de 2017, 09:03
 */

#include<iostream>
#include<cstdio>
#include<sstream>
#include<algorithm>
#include <cstdlib>
#include <string>
#include <stdio.h>
#include <stdlib.h>
#include <cstring>
#include <fstream>
#include <valarray>

using namespace std;

/*
 * Declarando a data de início das atividades da empresa
 */
int const inauguracao = 2015;

/*
 * Lista com o número de funcionários de cada filial
 */
int filiais[30] = {5, 3, 6, 4, 5};

int filial, ano, mes,funcionario;
float valor;

int opcao, filial_ini, filial_fim, ano_ini, ano_fim, mes_ini, mes_fim;

/*
 * Declarando a estrutura lista de listas
 */
struct Lista{
    int Frente = 0;
    int Tras = -1;
    int funcionario [120];
};

/*
 * Árvores
 */
 
/*
 * Declaração do nó da árvore geral
 */
struct avl_node
{
    int data;
    struct avl_node *left;
    struct avl_node *right;
    struct Lista{
    int Frente = 0;
    int Tras = -1;
    int func [120];
};
Lista funcionarios[];
}*root;


 
/*
 * Declaração da classe árvore
 */
class avlTree
{
    public:
        int height(avl_node *);
        int diff(avl_node *);
        avl_node *rr_rotation(avl_node *);
        avl_node *ll_rotation(avl_node *);
        avl_node *lr_rotation(avl_node *);
        avl_node *rl_rotation(avl_node *);
        avl_node* balance(avl_node *);
        avl_node* insert(avl_node *, int, int, int, int, float);
        avl_node* search(avl_node *, int, int);
        avl_node* search2(avl_node *, int, int, int, int, int, int);
        void display(avl_node *, int);
        void inorder(avl_node *);
        void preorder(avl_node *);
        void postorder(avl_node *);
        avlTree()
        {
            root = NULL;
        }
};

/*
 * Altura da árvore
 */
int avlTree::height(avl_node *temp)
{
    int h = 0;
    if (temp != NULL)
    {
        int l_height = height (temp->left);
        int r_height = height (temp->right);
        int max_height = max (l_height, r_height);
        h = max_height + 1;
    }
    return h;
}
 
/*
 * Diferença das alturas
 */
int avlTree::diff(avl_node *temp)
{
    int l_height = height (temp->left);
    int r_height = height (temp->right);
    int b_factor= l_height - r_height;
    return b_factor;
}
 
/*
 * Rotação direita-direita
 */
avl_node *avlTree::rr_rotation(avl_node *parent)
{
    avl_node *temp;
    temp = parent->right;
    parent->right = temp->left;
    temp->left = parent;
    return temp;
}
/*
 * Rotação esquerda-esquerda
 */
avl_node *avlTree::ll_rotation(avl_node *parent)
{
    avl_node *temp;
    temp = parent->left;
    parent->left = temp->right;
    temp->right = parent;
    return temp;
}
 
/*
 * Rotação esquerda-direita
 */
avl_node *avlTree::lr_rotation(avl_node *parent)
{
    avl_node *temp;
    temp = parent->left;
    parent->left = rr_rotation (temp);
    return ll_rotation (parent);
}
 
/*
 * Rotação direita-esquerda
 */
avl_node *avlTree::rl_rotation(avl_node *parent)
{
    avl_node *temp;
    temp = parent->right;
    parent->right = ll_rotation (temp);
    return rr_rotation (parent);
}
 
/*
 * Balanceamento da árvore
 */
avl_node *avlTree::balance(avl_node *temp)
{
    int bal_factor = diff (temp);
    if (bal_factor > 1)
    {
        if (diff (temp->left) > 0)
            temp = ll_rotation (temp);
        else
            temp = lr_rotation (temp);
    }
    else if (bal_factor < -1)
    {
        if (diff (temp->right) > 0)
            temp = rl_rotation (temp);
        else
            temp = rr_rotation (temp);
    }
    return temp;
}
 
/*
 * Inserindo um novo nó
 */


avl_node *avlTree::insert(avl_node *root, int filial, int funcionario, int ano, int mes, float valor)
{
    int posicao = ((ano-inauguracao)*12)+(mes-1); //calculando a posição na lista do funcionário
    if (root == NULL)
    {
        root = new avl_node;
        root->funcionarios[filiais[filial-1]];
        root->funcionarios[funcionario-1].func[posicao] = valor;
        root->data = filial;
        root->left = NULL;
        root->right = NULL;
        return root;
    }
    else if (filial == root->data)
    {
        root->funcionarios[funcionario-1].func[posicao] = valor;
      
          return root;
    }
    else if (filial < root->data)
    {
        root->left = insert(root->left, filial, funcionario, ano, mes, valor);
        root = balance (root);
    }
    else if (filial >= root->data)
    {
        root->right = insert(root->right, filial, funcionario, ano, mes, valor);
        root = balance (root);
    }
    return root;
}
 
/*
 * Visualizando a árvore
 */
void avlTree::display(avl_node *ptr, int level)
{
    int i;
    if (ptr!=NULL)
    {
        display(ptr->right, level + 1);
        printf("\n");
        if (ptr == root)
        cout<<"Root -> ";
        for (i = 0; i < level && ptr != root; i++)
            cout<<"        ";
        cout<<ptr->data;
        display(ptr->left, level + 1);
    }
}

/*
 * Fazendo busca na árvore
 */
avl_node *avlTree::search(avl_node *root, int filial_ini , int filial_fim){

    float total = 0.00;
    for (int i = filial_ini; i <= filial_fim; i++){
        while (root != NULL){
            if (i > root->data){
            root = root->right;
            }
            else if (i < root->data){
            root = root->left;
            }
            else (i == root->data);{
                int s;
                s = sizeof(root->funcionarios);
                for (int i = 0; i <= s; i++){
                     for (int j = 0; j <= 120; j++){
                   total = total + valor;
                     }
                }
            }
        }
    }   
    cout << "Total: " << total;
}     
        
/*
 * Fazendo busca na árvore
 */
avl_node *avlTree::search2(avl_node *root, int ano_ini, int ano_fim, int mes_ini, int mes_fim, int filial_ini, int filial_fim)
{
    int posicao_ini = ((ano_ini-inauguracao)*12)+(mes_ini-1);
    int posicao_fim = ((ano_fim-inauguracao)*12)+(mes_fim-1);
    float total = 0.00;
    for (int i = filial_ini; i <= filial_fim; i++){
    while (root != NULL){
        if (i > root ->data){
            root = root->right;
        }
        else if (i < root->data){
            root = root->left;
        }
        else (i == root->data);{
                int s;
                s = sizeof(root->funcionarios);
                for (int j = 0; j <= s; j++){
                     for (int k = 0; k <= 120; k++){
                         if (k >= posicao_ini && k<= posicao_fim ){
                   total = total + valor;
                         }
                     }
                }
            }
        }
           
    }  cout << "Total: " << total;
    }
    

      
    


/*
 * Declaração do nó da árvore de meses
 */
 typedef struct celulaLista{
   int informacao;
   struct celulaLista *proximo;
 }celula;
 
struct avl_node1
{
    int data;
    float vendas;
    struct avl_node1 *left;
    struct avl_node1 *right;
    celulaLista celula[];

}*root1;


 
/*
 * Declaração da classe árvore
 */
class avlTree1
{
    public:
        int height(avl_node1 *);
        int diff(avl_node1 *);
        avl_node1 *rr_rotation(avl_node1 *);
        avl_node1 *ll_rotation(avl_node1 *);
        avl_node1 *lr_rotation(avl_node1 *);
        avl_node1 *rl_rotation(avl_node1 *);
        avl_node1* balance(avl_node1 *);
        avl_node1* insert(avl_node1 *, int, int, float );
        avl_node1* search3(avl_node1 *, int, int, int, int);
        void display(avl_node1 *, int);
        void inorder(avl_node1 *);
        void preorder(avl_node1 *);
        void postorder(avl_node1 *);
        avlTree1()
        {
            root1 = NULL;
        }
};

/*
 * Altura da árvore
 */
int avlTree1::height(avl_node1 *temp)
{
    int h = 0;
    if (temp != NULL)
    {
        int l_height = height (temp->left);
        int r_height = height (temp->right);
        int max_height = max (l_height, r_height);
        h = max_height + 1;
    }
    return h;
}
 
/*
 * Diferença das alturas
 */
int avlTree1::diff(avl_node1 *temp)
{
    int l_height = height (temp->left);
    int r_height = height (temp->right);
    int b_factor= l_height - r_height;
    return b_factor;
}
 
/*
 * Rotação direita-direita
 */
avl_node1 *avlTree1::rr_rotation(avl_node1 *parent)
{
    avl_node1 *temp;
    temp = parent->right;
    parent->right = temp->left;
    temp->left = parent;
    return temp;
}
/*
 * Rotação esquerda-esquerda
 */
avl_node1 *avlTree1::ll_rotation(avl_node1 *parent)
{
    avl_node1 *temp;
    temp = parent->left;
    parent->left = temp->right;
    temp->right = parent;
    return temp;
}
 
/*
 * Rotação esquerda-direita
 */
avl_node1 *avlTree1::lr_rotation(avl_node1 *parent)
{
    avl_node1 *temp;
    temp = parent->left;
    parent->left = rr_rotation (temp);
    return ll_rotation (parent);
}
 
/*
 * Rotação direita-esquerda
 */
avl_node1 *avlTree1::rl_rotation(avl_node1 *parent)
{
    avl_node1 *temp;
    temp = parent->right;
    parent->right = ll_rotation (temp);
    return rr_rotation (parent);
}
 
/*
 * Balanceamento da árvore
 */
avl_node1 *avlTree1::balance(avl_node1 *temp)
{
    int bal_factor = diff (temp);
    if (bal_factor > 1)
    {
        if (diff (temp->left) > 0)
            temp = ll_rotation (temp);
        else
            temp = lr_rotation (temp);
    }
    else if (bal_factor < -1)
    {
        if (diff (temp->right) > 0)
            temp = rl_rotation (temp);
        else
            temp = rr_rotation (temp);
    }
    return temp;
}
 
/*
 * Inserindo um novo nó
 */


avl_node1 *avlTree1::insert(avl_node1 *root1, int ano, int mes,float valor)
{
    int a = ano;   //Calculando a chave ano/mes
    int b = mes;
    stringstream ss;
    ss << a << b;
    string e = ss.str();
    int c = atoi(e.c_str());
     
    if (root1 == NULL)
    {
        root1 = new avl_node1;
        root1->data = c;
        root1->left = NULL;
        root1->right = NULL;
        return root1;
    }
    else if (c == root1->data)
    {
        root1->celula->informacao=valor;
        root1->celula->proximo++;
        return root1;
    }
    else if (c < root1->data)
    {
        root1->left = insert(root1->left, ano, mes, valor);
        root1 = balance (root1);
    }
    else if (c > root1->data)
    {
        root1->right = insert(root1->right, ano, mes, valor);
        root1 = balance (root1);
    }
    return root1;
}
 
/*
 * Visualizando a árvore
 */
void avlTree1::display(avl_node1 *ptr, int level)
{
    int i;
    if (ptr!=NULL)
    {
        display(ptr->right, level + 1);
        printf("\n");
        if (ptr == root1)
        cout<<"Root -> ";
        for (i = 0; i < level && ptr != root1; i++)
            cout<<"        ";
        cout<<ptr->data;
        display(ptr->left, level + 1);
    }
}

/*
 * Fazendo busca na árvore
 */
avl_node1 *avlTree1::search3(avl_node1 *root1, int ano_ini, int ano_fim, int mes_ini, int mes_fim){

    float total = 0.00;
    int a = ano_ini;   //Calculando a chave ano/mes
    int b = mes_ini;
    int c = ano_fim;
    int d = mes_fim;
    stringstream ss;
    ss << a << b;
    string e = ss.str();
    int c_ini = atoi(e.c_str());
    stringstream rr;
    rr << c << d;
    string f = rr.str();
    int c_fim = atoi(f.c_str());
    cout << c_ini <<" "<<c_fim;
  
    for(int i = c_ini; i <= c_fim; i++){
    while (root != NULL){
        if (i > root1 ->data){
            root1 = root1->right;
        }
        else if (i < root1->data){
            root1 = root1->left;
        }
        else if (i == root1->data) {
            while (root1->celula->proximo != NULL){
               total = total + (root1->celula->informacao);
                 root1->celula->proximo++;
            }
                 }
            }
                 }
           
        
        cout << "Total: "  << total;
        }

void initLista(celula **pRecebido);
      void insertLista(celula **pRecebido);
      void buscaListaSimples(celula **pRecebido);
 
           /*
   Inicialização da Lista Encadeada
*/
void initLista(celula **pRecebido){
   (*pRecebido)->proximo = NULL;
}

/*
   Função para Inserção no Início
*/
void insertLista(celula **pRecebido){
   // Declarações
      celula *temporario;
      int valor;
   // Instruções
      printf("\nInforme um Valor a ser Inserido : ");
      scanf("%d", &valor);
      temporario = (celula *)malloc(sizeof(celula));
      temporario->informacao = valor;
      temporario->proximo = (*pRecebido)->proximo;
      (*pRecebido)->proximo = temporario;   
}
/*
   Função para Percorrer Elementos
*/
void buscaListaSimples(celula **pRecebido){
   // Declarações
      celula *temporario;
   // Instruções
      if((*pRecebido)->proximo == NULL){
         printf("Lista Vazia!\n");
      }
      else{
         temporario = (celula *)malloc(sizeof(celula));
         temporario = (*pRecebido)->proximo;
         while(temporario != NULL){
            printf("Valor : %d\n", temporario->informacao);
            temporario = temporario->proximo;
         }
      }   
}
      
/*
 *
 */
int main()
{
    avlTree avl;
    avlTree1 avl1;
    FILE *pFile;
    
    pFile = fopen ("test.txt", "r");
    while (! feof(pFile)) {
    fscanf (pFile, "%d", &filial);
    fscanf (pFile, "%d", &ano);
    fscanf (pFile, "%d", &mes);
    fscanf (pFile, "%d", &funcionario);
    fscanf (pFile, "%f", &valor);
    
    root = avl.insert(root, filial, funcionario, ano, mes, valor);
    root1 = avl1.insert(root1, ano, mes, valor);
    
   
    
    }
   fclose(pFile);
   
   
   while (1)
    {
        cout<<"\n---------------------"<<endl;
        cout<<"\n---------------------"<<endl;
        cout<<"1. Vendas por intervalo de meses"<<endl;
        cout<<"2. Vendas por intervalo de Filiais"<<endl;
        cout<<"3. Vendas por intervalos de filiais e meses "<<endl;
        cout<<"4. Exit"<<endl;
       
        cout<<"Escolha uma opção: ";
        cin>>opcao;
        switch(opcao)
        {
        case 1:
            cout<<"Entre o ano inicial: ";
            cin>>ano_ini;
            cout<<"Entre o ano final: ";
            cin>>ano_fim;
            cout<<"Entre o mes inicial: ";
            cin>>mes_ini;
            cout<<"Entre o mes final: ";
            cin>>mes_fim;
            
            root1 = avl1.search3(root1, ano_ini, ano_fim, mes_ini, mes_fim);
            avl1.display(root1, 1);
            break;
        case 2:
            cout<<"Entre a filial inicial: ";
            cin>>filial_ini;
            cout<<"Entre a filial final: ";
            cin>>filial_fim;
          
            root = avl.search(root, filial_ini, filial_fim);
            avl.display(root, 1);
            break;
        case 3:
            cout<<"Entre o ano inicial: ";
            cin>>ano_ini;
            cout<<"Entre o ano final: ";
            cin>>ano_fim;
            cout<<"Entre o mes inicial: ";
            cin>>mes_ini;
            cout<<"Entre o ano final: ";
            cin>>mes_fim;
            cout<<"Entre a filial inicial: ";
            cin>>filial_ini;
            cout<<"Entre a filial final: ";
            cin>>filial_fim;
            root = avl.search2(root, ano_ini, ano_fim, mes_ini, mes_fim, filial_ini, filial_fim);
            avl.display(root, 1);
            break;
        case 4:
            exit(1);
            break;
        
        default:
            cout<<"Escolha errada."<<endl;
        }
    }
    
    
    
    
    return 0;
}
 




