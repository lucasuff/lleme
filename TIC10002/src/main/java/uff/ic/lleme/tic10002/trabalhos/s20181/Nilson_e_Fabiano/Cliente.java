package uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano;

/**
 * Clase que gera os objetos Cliente tal como descritos na especificação do
 * trabalho.
 *
 * @author Nilson e Fabiano
 */
public class Cliente {

    private final int id;
    private final String nome;
    private final int idade;

    public Cliente(int id, String nome, int idade) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }
}
