package uff.ic.lleme.tcc00328.aulas;

public class NewClass {

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
