package uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.src.model;

public class Cliente {

    private String cpf;
    private String nome;
    private int idade;

    public Cliente(String cpf, String nome, int idade) {
        this.cpf = cpf;
        this.nome = nome;
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

}
