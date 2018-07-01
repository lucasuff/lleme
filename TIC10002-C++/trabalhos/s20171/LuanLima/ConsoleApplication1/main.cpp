// ConsoleApplication1.cpp : Define o ponto de entrada para a aplicação de console.
//

#include "stdafx.h"
#include <iostream>
#include "QuerySystem.h"


struct MyKeyHash {
	unsigned long operator()(const size_t &k) const {
		return k % unsigned(TABLE_SIZE);
	}
};


int main(int argc, char **argv) {

	
	QuerySystem query; // Criando um objeto do tipo query

   /* Registrando as vendas */

	query.insertSale(29, "10/jul", 14, 198);
	query.insertSale(16, "12/jan", 06, 965);
	query.insertSale(23, "17/fev", 17, 659);
	query.insertSale(33, "14/set", 10, 813);
	query.insertSale(12, "11/mar", 24, 333);
	query.insertSale(17, "14/abr", 23, 871);
	query.insertSale(13, "17/set", 16, 237);
	query.insertSale(16, "10/jun", 26, 896);
	query.insertSale(18, "12/set", 01, 471);
	query.insertSale(07, "15/ago", 18, 384);
	query.insertSale(13, "17/fev", 25, 55);
	query.insertSale(01, "14/jun", 14, 385);
	query.insertSale(35, "12/abr", 10, 759);
	query.insertSale(13, "14/jun", 03, 130);
	query.insertSale(31, "14/jun", 25, 80);
	query.insertSale(25, "11/nov", 25, 547);
	query.insertSale(38, "10/set", 24, 679);
	query.insertSale(9, "10/ago", 18, 537);
	query.insertSale(23, "12/jul", 27, 705);
	query.insertSale(03, "14/set", 27, 80);


	/* Exemplos de consulta*/

	// Total de vendas das filiais com código entre 10 e 20
	std::cout << "O total de venda das filiais foi: "
		<< query.getTotalSalesByBranch(10, 20) << std::endl;

	// Total de vendas das filiais com código entre 10 e 20 no mês 17/jan até 17/jun
	std::cout << "O total de venda das filiais foi: "
		<< query.getTotalSalesByDateAndBranch("17/jan", "17/jun", 10, 20) << std::endl;

	// Total de vendas de todas as filiais entre de 17/ago e 17/out
	std::cout << "O total de venda das filiais foi: "
		<< query.getTotalSalesByDate("17/ago", "17/out") << std::endl;

	system("pause"); // System pause para windows

	return 0;
}