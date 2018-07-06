//
// Created by luan on 5/31/17.
//

#include <random>
#include <ctime>
#include <iterator>
#include <iostream>
#include "../LinkedList/SimpleLinkedList.h"
#include "../HashTable/HashMap.h"

struct Compare {

    int operator()(const int &a, const int &b) const {
        return a - b;
    }
};

int random_int(std::mt19937& rng, int low, int high) {
    return std::uniform_int_distribution<int>(low, high)(rng);
}

int main() {
    //    // initialize random number generator with warm-up sequence
    //    // somewhat elaborate because: 'nearly perfect pseudo random numbers'
    //    std::srand(std::time(nullptr) ) ;
    //    std::uint32_t r[ std::mt19937::state_size ] ;
    //    std::seed_seq sseq( std::begin(r), std::end(r) ) ;
    //    std::mt19937 gen(sseq) ;
    //
    //
    //    LinkedList<int, Compare> list(true, false);
    //    for(int i = 0; i < 30; i++){
    //        list.insert(random_int( gen, 0, 100));
    //    }
    //
    //
    //    list.print();
    //    if(list.remove(11)){
    //        std::cout << "Elemento removido com sucesso " << std::endl;
    //    }else{
    //        std::cout << "Elemento n encontrado" << std::endl;
    //    }
    //    list.print();

    return 0;
}

