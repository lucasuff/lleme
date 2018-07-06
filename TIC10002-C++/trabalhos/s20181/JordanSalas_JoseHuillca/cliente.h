#ifndef CLIENTE_H
#define CLIENTE_H

#include <iostream>
#include <time.h>
#include <stdlib.h> /* srand, rand */

#define MAX_IDADE_RAND 70
#define MIN_IDADE_RAND 18

using namespace std;

class Cliente {
private:
    double cpf;
    string nome;
    int idade;
public:

    Cliente() {
    }

    Cliente(double cpf_, string nome_, int idade_) {
        cpf = cpf_;
        nome = nome_;
        idade = idade_;
    }

    Cliente(double cpf_, string nome_) {
        srand(time(NULL));
        cpf = cpf_;
        nome = nome_;
        idade = rand() % MAX_IDADE_RAND + MIN_IDADE_RAND;
    }

    inline double getCpf() {
        return cpf;
    }

    inline string getNome() {
        return nome;
    }

    inline int getIdade() {
        return idade;
    }

    inline void setCpf(double cpf_) {
        cpf = cpf_;
    }

    inline void setNome(string nome_) {
        nome = nome_;
    }

    inline void setIdade(int idade_) {
        idade = idade_;
    }
};

#endif // CLIENTE_H
