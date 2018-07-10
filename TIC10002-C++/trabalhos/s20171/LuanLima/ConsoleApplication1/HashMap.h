/*
 * Estrutura de uma tabela HASH Genérica, é necessario passar chave (k), valor (V) e função hash (F)
 *  A função Hash (F) sempre deverá retornar um inteiro. [fiz isso para facilitar a implementação]
 * Created by luan on 5/22/17.
 */



#include <cstdio>



// Hash map class template

template<typename K, typename V, typename F>

class HashMap {
private:

    /*Iner Clash: Noh da tabela HashTable
     *
     * Como a tabela genérica, optei por criar um "encapsulador" para o objeto utilizado na entrada.
     * Nesse caso, o objeto fica armazenado em value
     *
     * */
    class NodeHash {
    public:
        K key; // chave da tabela hash
        V value; // Valor (conteudo) do nó hash
        NodeHash *next; // Ponteiro para o proximo noh com o mesmo 'value'

        NodeHash(const K &key, const V &value) :
        key(key), value(value), next(nullptr) {
        }

    };


public:

    /*Construtor: recebe o tamanho da tabela e cria a tabela Hash (vazia)*/
    HashMap(int TABLE_SIZE = 10000) {
        this->TABLE_SIZE = TABLE_SIZE;
        this->table = new NodeHash *[TABLE_SIZE](); // Inicializa a tabela
    }

    //    //Desconstrutor

    ~HashMap() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            NodeHash *ptr_hash = table[i];
            NodeHash *aux;
            while (ptr_hash != nullptr) {
                aux = ptr_hash;
                ptr_hash = ptr_hash->next;
                delete aux;
            }
        }

    }

    /*
     * Retorna em  'value', o conteudo do noh referente a chave key
     * saida: True -> encontrado
     *         False -> nao encotrado
     * */
    bool get(const K &key, V &value) const {

        unsigned long hashValue = hashFunc(key);
        NodeHash *ptr_hash = table[hashValue];

        while (ptr_hash != nullptr) {
            //Encotrado
            if (ptr_hash->key == key) {
                value = ptr_hash->value;
                return true;
            }
            ptr_hash = ptr_hash->next;
        }
        //Não encontrado
        return false;
    }

    /* Insere um elemento na tabela hash
     *
     */
    void put(const K &key, const V &value) {
        unsigned long int hashValue = hashFunc(key);


        NodeHash *prev = nullptr;
        NodeHash *ptr_hash = table[hashValue]; // primeiro elemento do hash

        while (ptr_hash != nullptr && ptr_hash->key != key) {
            prev = ptr_hash;
            ptr_hash = ptr_hash->next;
        }

        if (ptr_hash == nullptr) { // Não existe elementos com a chave key, portanto um novo NodeHash deverá ser criado
            ptr_hash = new NodeHash(key, value); //
            if (prev == nullptr)
                table[hashValue] = ptr_hash;
            else
                prev->next = ptr_hash;

        } else { // Noh existe, atualizar value
            //atualiza o valor
            ptr_hash->value = value;
        }
    }

    /*
     * Remove um valor da tabelaHash
     * entrada: key
     * saida: true -> removido com sucesso
     *        false-> noh não encontrado
     * */

    // Implementei soh pra ficar completa. Não era necessario

    bool remove(const K &key) {
        unsigned long int hashValue = hashFunc(key);
        NodeHash *prev = NULL;
        NodeHash *ptr_hash = table[hashValue];

        // Encontra o no hash relacionado com a key
        while (ptr_hash != nullptr && ptr_hash->key != key) {
            prev = ptr_hash;
            ptr_hash = ptr_hash->next;
        }

        // noh não encontrado
        if (ptr_hash == nullptr)
            return false;
        else {
            if (prev == nullptr) {
                // remove o primeiro elemento da lista
                table[hashValue] = ptr_hash->next;
            } else { // ligar a lista, para não perder os elementos (caso haja colisão)
                prev->next = ptr_hash->next;
            }
            delete ptr_hash; // deleta o elemento da memoria
            return true;
        }
    }

    /*DEBUG*/
    //    void print(){
    //        for(unsigned  long int i = 0; i < TABLE_SIZE; i++){
    //            NodeHash *ptr_hash = table[i];
    //            while(ptr_hash != nullptr) {
    //                ptr_hash->value->print();
    //                ptr_hash = ptr_hash->next;
    //            }
    //
    //        }
    //    }

private:
    // hash table
    NodeHash **table;
    F hashFunc;
    int TABLE_SIZE;


};
