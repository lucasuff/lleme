package uff.ic.lleme.tcc00328.aulas.concurrency.rascunho;

import uff.ic.lleme.tcc00328.aulas.Conta;
import uff.ic.lleme.tcc00328.aulas.Pilha;

public class NewClass2 {

    public static void main(String[] args) {
        int i = 0;
        Conta c = new Conta();

        Pilha<String> pilha = new Pilha<String>();

        pilha.push("Luiz");
        pilha.push("Andre");

        System.out.println(pilha.pop());
        System.out.println(pilha.pop());

    }
}
