/* Uma lista encadeada simples */
//
// Created by luan on 01/06/17.
//

#include <iostream>



template <typename V, typename  F>
class LinkedList{

public:
    class Node{

    public:
        V value;
        Node *next;
        Node(V value) : value(value), next(nullptr) {}
    };


    bool  sorted, canRepeat;
    F compare;
    Node *head;

    LinkedList(bool sorted=false, bool canRepeat=true) : sorted(sorted), canRepeat(canRepeat), head(nullptr), size(0) {}


    // Descontrutor.
    ~LinkedList(){
        Node * aux1, *aux = head;
        while(aux != nullptr){
            aux1 = aux;
            aux = aux->next;
            delete aux1;
        }

    }

    void insert(V value){
        Node * node = new Node(value);

        if(head == nullptr) {
            head = node;
            size += 1;
        }else{
            Node * prev = nullptr;
            Node * aux = head;

            bool flag = false;

            while(aux != nullptr){
                if(sorted && compare(aux->value, value) > 0) // Preciso inserir ordenado?
                    break;
                if(!canRepeat && compare(aux->value, value) == 0) { // Pode repetir valor?
                    flag = true;
                    break;
                }
                prev  = aux;
                aux = aux->next;


            }

            // inserção
            if(!flag){

                if(sorted){ // se o modo ordenado estiver ativo, é necessário verificar se o prev == null (novo nó no inicio)
                    if(prev == nullptr){
                        node->next = aux;
                        head = node;
                    }else{
                        prev->next = node;
                        node->next = aux;
                    }
                }else{ // caso contrário prev sempre será diferente de null (if(head == nullptr) garante isso)
                    prev->next = node;
                }

                // atualiza o tamanho da lista
                size += 1;

            } else // delete o elemento duplicado
                delete node;
        }

    }

    void print(){
        Node *aux = head;

        while(aux != nullptr){
            std::cout << aux->value << ", ";
            aux = aux->next;
        }

        std::cout << std::endl;
    }

    int getSize(){
        return size;
    }

    /*remoção de um elemento da lista
     *
     * Não era necessário, mas fiz para a classe ficar completa.
     * */
    bool remove(V value){
        Node *prev = nullptr, *aux = head;
        while(aux!= nullptr && compare(aux->value, value) != 0){
            prev = aux;
            aux = aux->next;
        }

        if(aux != nullptr){ // elemento encontrado - remover o elemento
            if(prev == nullptr) // elemento na primeira posição
                head = aux->next;
            else
                prev->next = aux->next;
            size -= 1;
            aux->next = nullptr;
            delete aux;
            return true;
        }else
            return false;

    }

    //Todo
    LinkedList * Union(LinkedList  *other);


private:

    int size;

};