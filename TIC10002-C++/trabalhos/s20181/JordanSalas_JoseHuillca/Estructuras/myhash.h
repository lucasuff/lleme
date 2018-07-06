#ifndef MYHASH_H
#define MYHASH_H

#include <iostream>

using namespace std;

class MyHash {
public:

    struct No {
        int tipo;
        float duracaoAtendimentoAcumulado;
        int contador;

        No() {
            tipo = -1;
            contador = 0;
            duracaoAtendimentoAcumulado = 0.0;
        }
    };
private:
    No *myHash;
    int tamanho;
public:

    MyHash() {
        myHash = 0;
        tamanho = 0;
    }

    MyHash(int tamanho_) {
        myHash = new No[tamanho_];
        tamanho = tamanho_;
    }

    inline int funcaoHash(int k) {
        return k - 1;
    }

    inline int getTamanho() {
        return tamanho;
    }
    bool estaVazio(int k);

    void inserir(int k, float duracao);
    float getMedia(int k);

};

bool MyHash::estaVazio(int k) {
    int index = funcaoHash(k);
    if (index < tamanho && index >= 0) {
        if (myHash[index].tipo == -1) {
            return true;
        } else {
            return false;
        }
    }
    return true;
}

void MyHash::inserir(int k, float duracao) {
    int index = funcaoHash(k);
    if (index < tamanho && index >= 0) {
        if (myHash[index].tipo == -1) {
            No aux;
            aux.tipo = k;
            aux.duracaoAtendimentoAcumulado = duracao;
            aux.contador = 1;
            myHash[index] = aux;
        } else {
            myHash[index].duracaoAtendimentoAcumulado += duracao;
            myHash[index].contador += 1;
        }
    }
}

float MyHash::getMedia(int k) {
    int index = funcaoHash(k);
    return myHash[index].duracaoAtendimentoAcumulado / myHash[index].contador;
}

#endif // MYHASH_H
