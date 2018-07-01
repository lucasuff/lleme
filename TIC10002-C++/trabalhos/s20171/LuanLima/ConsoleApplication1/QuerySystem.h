//
// Created by luan on 01/06/17.
//

#include <iostream>
#include "HashMap.h"
#include "Manager.h"
#include "Avl.h"
#include <sstream>
#include <fstream>
#include <functional>


class QuerySystem {


#define TABLE_SIZE 100000
#define TABLE_AUX_SIZE 200


typedef  LinkedList<std::string, Manager::CompareDate>::Node Lnode;


public:

    // Construtor com arquivo de entrada
    QuerySystem(std::string inputFile, bool debug=false) {

        my_hash = new HashMap<size_t, Manager *, MyKeyHash>(TABLE_SIZE);
        branch_tree = Avl<int, Manager::CompareInt>();
        date_tree = Avl<std::string, Manager::CompareDate>();
        this->debug = debug;

        readFile(inputFile);
    }

    // Construtor sem arquivo de entrada
    QuerySystem(bool debug=false) {
        my_hash = new HashMap<size_t, Manager *, MyKeyHash>(TABLE_SIZE);
        branch_tree = Avl<int, Manager::CompareInt>();
        date_tree = Avl<std::string, Manager::CompareDate>();
        this->debug = debug;


    }


    ~QuerySystem(){
        delete my_hash;
    }


    int getTotalSalesByDate(std::string d1, std::string d2){

        LinkedList<std::string, Manager::CompareDate> range = date_tree.getRange(d1, d2);
        if(debug){
            std::cout << "Datas: " << std::endl;
            range.print();
        }


                  int sum = 0;
        Manager *aux;
        //Para cada data válida (dentro do range) soma o total de vendas
        for(LinkedList<std::string, Manager::CompareDate>::Node *it = range.head; it != nullptr; it = it->next){
            if(my_hash->get(getHash(it->value), aux)){
                sum += aux->getTotal();
            }
        }
        return sum;
    }

    int getTotalSalesByBranch(int b1, int b2){

        LinkedList<int, Manager::CompareInt> range = branch_tree.getRange(b1, b2);

        if(debug){
            std::cout << "Filiais: " << std::endl;
            range.print();
        }

        int sum = 0;
        Manager *aux;
        // Para cada filial válida (dentro do range) soma o total de vendas
        for(LinkedList<int, Manager::CompareInt>::Node *it = range.head; it != nullptr; it = it->next){
            if(my_hash->get(getHash(std::to_string(it->value)), aux)){
                sum += aux->getTotal();
            }
        }
        return sum;
    }

    int getTotalSalesByDateAndBranch(std::string d1, std::string d2, int b1, int b2){
        //Recupera o range de datas
        LinkedList<std::string, Manager::CompareDate> range_date = date_tree.getRange(d1, d2);
        //Recupera o range de filiais
        LinkedList<int, Manager::CompareInt> range_branch = branch_tree.getRange(b1, b2);

        if(debug) { // DEBUG
            std::cout << "Datas: " << std::endl;
            range_date.print();

            std::cout << "Filiais: " << std::endl;
            range_branch.print();
        }


        //Aloca uma HAsh Auxiliar para melhorar o desempenho da consulta
        HashMap<size_t, std::string, MyKeyHashAux> * aux_hash = new HashMap<size_t, std::string, MyKeyHashAux>(TABLE_AUX_SIZE);
        //Cria as entradas na HASH auxiliar (a data será a chave da consulta)
        for(LinkedList<std::string, Manager::CompareDate>::Node *it = range_date.head; it != nullptr; it = it->next) {
            aux_hash->put(getHash(it->value), it->value);
        }

        int sum = 0;
        int aux;
        Manager *aux_manager;
        //Para cara filial consulta as vendas nas datas válidas (utilizando o aux_hash)
        for(LinkedList<int, Manager::CompareInt>::Node *it = range_branch.head; it != nullptr; it = it->next){
            if(my_hash->get(getHash(std::to_string(it->value)), aux_manager)) { //Recupera da HASH principal
                // a função getTotalByDAte utiliza o hash auxiliar para que essa consulta seja feita em O(N)
                sum += aux_manager->getTotalByDate(aux_hash);
            }
        }

        //Deleta a hash auxiliar
        delete aux_hash;

        return sum;


    }

    void insertSale(int branch_int, std::string date, int seller_code,  int total_sold){

        std::string branch = std::to_string(branch_int);

        // Cria uma estrutura do tipo venda (sale)
        Sale *sale = new Sale(branch, date, seller_code, total_sold);


        //Verifica se já existe um Manager com key=branch
        Manager *aux1 = nullptr;
        if(!my_hash->get(getHash(branch), aux1)) {// caso NAO exista cria um novo manager key=branch.
            aux1 = new Manager(branch);
            branch_tree.insert(std::stoi(branch));
        }

        Manager *aux2 = nullptr;
        //Verificar se já existe um Manager com key=Date
        if(!my_hash->get(getHash(date), aux2)) { // Caso NAO exista, cria um novo manager key=Date
            aux2 = new Manager(date);
            date_tree.insert(date);
        }


        aux1->add(sale);
        aux2->add(sale);

        my_hash->put(getHash(branch), aux1);
        my_hash->put(getHash(date), aux2);
    }


private:

    struct MyKeyHash {
        unsigned long operator()(const size_t &k) const {
            return k % unsigned(TABLE_SIZE);
        }
    };

    struct MyKeyHashAux {
        unsigned long operator()(const size_t &k) const {
            return k % unsigned(TABLE_AUX_SIZE);
        }
    };



    // Dado uma string, retorna um valor numerico (MD5, eu acho)
    /* Função utilizada para gerar chaves para os elementos que serão inseridos na minha tabela hash*/
    size_t getHash(std::string key) {
        std::hash<std::string> hasher;
        return hasher(key);
    }


    /*
     * Ler arquivo de entrada

     * Padrão de entrada:

     filial      data       cod_vend    quant.
     <int>, "<ano>/<mes>", "<string>", <int>
     exemplo:
     15, 11/fev, 2, 62
     37, 11/set, 10, 700
     30, 14/dez, 10, 49
     13, 11/fev, 8, 502

     #OBS1: ano com dois digitos
     #OBS2: meses abreviados:
                 "jan"
                 "fev"
                 "mar"
                 "abr"
                 "mai"
                 "jun"
                 "jul"
                 "ago"
                 "set"
                 "out"
                 "nov"
                 "dez"
      */
    void readFile(std::string file_name) {
        //Reading file
        std::string line;
        std::ifstream infile(file_name);


        // Le o arquivo de entrada linha por linha
        while (std::getline(infile, line)) {
            std::string token = "";

            int flag = 0;

            std::string branch = "";
            std::string date = "";
            int seller_code = 0;
            int total_sold = 0;

            //Input parse - executa um parse basico do arquivo
            for (auto it = line.begin(); it != line.end(); it++) {
                if (*it != ',' && it + 1 != line.end()) {
                    if (*it != ' ') token += *it;
                } else {
                    if (flag == 0) {//branch code
                        branch = token;
                        flag += 1;
                    } else if (flag == 1) {
                        date = token;
                        flag += 1;
                    } else if (flag == 2) {
                        seller_code = std::stoi(token);
                        flag += 1;
                    } else if (flag == 3) {
                        token += *it;
                        total_sold = std::stoi(token);
                    }
                    token = "";
                }
            }

            insertSale(std::stoi(branch), date, seller_code, total_sold);

        }
        if(debug){
            std::cout << "Leu o arquivo de entrada..." << std::endl;
        }
    }


    HashMap <size_t, Manager *, MyKeyHash> *my_hash;
    Avl<int, Manager::CompareInt> branch_tree;
    Avl<std::string, Manager::CompareDate> date_tree;
    bool debug;

};


























