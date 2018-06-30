package trabalho;

public class Cliente {

    public int id;
    public String nome;
    public String cpf;
    int idade;

    public Cliente(int id, String nome, String cpf, int idade) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Cliente ID: " + this.id
                + " - Nome: " + this.nome
                + " - CPF: " + this.cpf
                + " - Idade: " + this.idade;
    }

}
