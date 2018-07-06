// /* Implementação de uma árvore AVL  */]
//
// Created by luan on 5/31/17.
//

#include <iostream>

template<typename V, typename F>
class Avl {

    class Node {
    public:
        V value;
        int fbal;
        Node *left, *right;

        Node(V value) : value(value), fbal(0), left(nullptr), right(nullptr) {
        }
    };

public:

    Avl() : T(nullptr) {
    }

    /*
     * Insere um novo nó na arvore
     * true: inserção realizada com sucesso
     * false: inserção não realizada
     * */

    bool insert(V value) {
        bool flag = false;
        bool isOk = true;
        T = __insert(value, T, flag, isOk);
        return isOk;
    }

    /* Imprime a arvore e verifica se o fbal está correto:
     * true: Correto
     * false: Incorreto
     */
    bool print(bool hastoPrint = true) {
        bool isCorrect = true;
        walk(T, 0, isCorrect, hastoPrint);
        return isCorrect;
    }

    /*Função publica para pegar o range [a, b]*/
    LinkedList<V, F> getRange(V a, V b) {

        // Garante que a <= b
        if (compare(a, b) > 0) {
            V aux = a;
            a = b;
            b = aux;
        }

        LinkedList<V, F> range(false, false);
        // get range
        __getRange(T, a, b, range);


        return range;
    }


private:
    Node *T;
    F compare;

    /*
     * Value: valor a ser inserido
     * ptr: ponteiro para o nó (inicia com a raiz)
     * flag: flag que indica a necessidade de atualizar o fbal na volta da recursividade
     * isOk: indica se a inserção foi realizada com sucesso
     */
    Node * __insert(V value, Node * ptr, bool & flag, bool & isOk) {
        if (ptr == nullptr) {//Cria um novo noh
            flag = true;
            return new Node(value);
        } else {
            if (compare(value, ptr->value) < 0) { //value é menor que ptr->value
                ptr->left = __insert(value, ptr->left, flag, isOk);
                if (flag) {
                    switch (ptr->fbal) {
                        case -1:
                            ptr->fbal = 0; // fbal += 1
                            flag = false; //arvore balanceada
                            break;
                        case 0:
                            ptr->fbal = 1; // fbal += 1 // continua atualizando os fbals
                            break;
                        case 1:
                            ptr = case1(ptr); // fbal == 2, chama caso 1
                            flag = false;
                            break;
                    }
                }
            } else if (compare(value, ptr->value) > 0) { // value é maior que ptr->value
                ptr->right = __insert(value, ptr->right, flag, isOk);
                if (flag) {
                    switch (ptr->fbal) {
                        case 1:
                            ptr->fbal = 0; // fbal -= 1
                            flag = false; // arvore balanceada
                            break;
                        case 0: // fbal -= 1
                            ptr->fbal = -1; // continua atualizando os fbals
                            break;
                        case -1: // fbal == -2!!! chama caso 2
                            ptr = case2(ptr);
                            flag = false;
                            break;
                    }
                }
            } else // Não admite nos repetidos
                isOk = false;
        }
        return ptr;
    }

    Node * case1(Node * ptr) { // caso 1 - chamada a esquerda
        Node * ptu, *ptv;
        ptu = ptr->left;

        if (ptu->fbal == 1) { // rotação simples a direita
            ptr->left = ptu->right;
            ptu->right = ptr;
            ptr->fbal = 0; // fator de balanceamento <- 0
            ptr = ptu; // agora ptu é raiz
        } else {// Rotação Dupla
            Node * ptv = ptu->right; // necessário rotacionar com 3 nós, (cria ptv)

            //ptv será a nova raiz
            //1 - aponta para os filhos de ptv
            ptr->left = ptv->right;
            ptu ->right = ptv->left;
            //2 - ptv aponta para ptr (direita) e para ptu (esquerda)
            ptv->left = ptu;
            ptv->right = ptr;

            //Atualização do fator de balancemaneto de ptr
            if (ptv->fbal == 1) { // subarvore a esquerda era maior, logo atualiza ptr
                ptr->fbal = -1;
            } else { // senão
                ptr->fbal = 0;
            }

            //Atualização do fator de balancemaneto de ptu
            if (ptv->fbal == -1) { // mesmo principio (subarvore  a direita era maior)
                ptu->fbal = 1;
            } else { // senão
                ptu->fbal = 0;
            }
            ptr = ptv; // agora ptv é raiz

        }
        ptr->fbal = 0; // toda rotação faz a nova raiz ter fbal = 0,
        return ptr;
    }

    Node * case2(Node * ptr) { // caso 2 - chamada a direita
        Node * ptu, *ptv;
        ptu = ptr->right;

        if (ptu->fbal == -1) { // rotação simples a esquerda
            ptr->right = ptu->left;
            ptu->left = ptr;
            ptr->fbal = 0; // fator de balanceamento <- 0
            ptr = ptu; // ptu é a nova raiz
        } else { // Rotação Dupla
            Node * ptv = ptu->left; // necessário tres nós, na rotação

            // 1- cuidar dos filhos de ptv (ptv, será a nova raiz)
            ptr->right = ptv->left;
            ptu->left = ptv->right;

            // 2- transformar ptv na nova raiz
            ptv->left = ptr;
            ptv->right = ptu;

            // 3- atualizar o fator de balanceamento de ptr e ptu
            if (ptv->fbal == -1) {//arvore a esquerda era menor, logo ptr fica com:
                ptr->fbal = 1;
            } else { // se nao
                ptr->fbal = 0;
            }

            if (ptv->fbal == 1) {// analogo
                ptu->fbal = -1;
            } else {
                ptu->fbal = 0;
            }
            ptr = ptv; // ptv é a nova raiz


        }
        ptr->fbal = 0; // toda raiz depois da rotação tem fbal=0
        return ptr;
    }

    /*
     * Faz um percurso na arvore retornando os valores que estão dentro do RANGE [a, b]
     *
     * A função getRange (publica) garante que a <= b;
     * */
    void __getRange(Node *ptr, V a, V b, LinkedList<V, F> & range) {
        if (ptr != nullptr) {
            __getRange(ptr->left, a, b, range);

            //verifica se ptr->value pertence [a, b]]
            // se ptr->value >= a && ptr->value <= b, então está no range
            if (compare(ptr->value, a) >= 0 && compare(ptr->value, b) <= 0)
                range.insert(ptr->value);

            __getRange(ptr->right, a, b, range);

        }
    }

    //Imprime o valor de um nó e o fator de balanceamento

    void visit(Node *T) {
        std::cout << T->value << " (" << T->fbal << ") " << std::endl;
    }

    void walk(Node *T, int n, bool & isCorrect, bool hasToPrint) {
        if (T != nullptr) {
            walk(T->right, n + 1, isCorrect, hasToPrint);
            // verifica se o fbal está correto.
            if (T->fbal < -1 || T->fbal > 1)
                isCorrect = false;
            if (hasToPrint) {
                for (int i = 0; i < n; i++) std::cout << "\t";
                visit(T);
            }
            walk(T->left, n + 1, isCorrect, hasToPrint);
        }
    }



};



























