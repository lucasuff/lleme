//
// Created by luan on 5/29/17.
//



#include <string>
#include <vector>
#include "Sale.h"
#include "SimpleLinkedList.h"

class Manager {
public:

    Manager(std::string key) : key(key), total(0) {
        list = new LinkedList<Sale *, CompareSale>(true, true);
    }

    //desconstrutor

    ~Manager() {
        delete list;

    }


    // Compara dois Inteiros

    struct CompareInt {

        int operator()(const int & a, const int & b) const {
            return a - b;
        }
    };

    // Comparação para uma string que representa uma data

    struct CompareDate {

        int operator()(const std::string &a, const std::string &b) const {
            Sale sale;
            int month_a = sale.parseMonth(a);
            int year_a = sale.parseYear(a);

            int month_b = sale.parseMonth(b);
            int year_b = sale.parseYear(b);

            if (year_a < year_b) {
                return -1;
            } else if (year_a > year_b) {
                return 1;
            } else if (month_a < month_b) {
                return -1;
            } else if (month_a > month_b) {
                return 1;
            } else {
                return 0;
            }

        }
    };

    //Compara a Estrutura Sale (ordenado pelo branch)

    struct CompareSale {

        int operator()(const Sale *a, const Sale *b) const {
            return std::stoi(a->getBranch()) - std::stoi(b->getBranch());
        }
    };

    void add(Sale *sale) {
        total += sale->getTotal_sold();

        list->insert(sale);

    }

    void print() {
        std::cout << "Key: " << key << " Total: " << total << std::endl;
        LinkedList<Sale*, CompareSale>::Node * aux = list->head;
        while (aux != nullptr) {
            aux->value->print();
            aux = aux->next;
        }
    }

    int getTotal() {
        return total;
    }

    template<typename K, typename V, typename F>
    int getTotalByDate(HashMap<K, V, F> * aux_hash) {
        int sum = 0;
        V value;
        for (LinkedList<Sale *, CompareSale>::Node *it = list->head; it != nullptr; it = it->next) {
            if (aux_hash->get(getHash(it->value->getDate()), value))
                sum += it->value->getTotal_sold();
        }
        return sum;
    }




private:
    std::string key;
    LinkedList <Sale *, CompareSale> *list;
    int total;

    size_t getHash(std::string key) {
        std::hash<std::string> hasher;
        return hasher(key);
    }
};

