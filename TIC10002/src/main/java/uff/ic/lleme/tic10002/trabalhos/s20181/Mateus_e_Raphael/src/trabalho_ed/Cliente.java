package trabalho_ed;

/**
 *
 * @author mateu
 */
public class Cliente {

    private int id = 0;
    private int idade = 0;
    private String CPF = null;
    private String nome = null;

    public Cliente(int id, int idade, String CPF, String nome) {
        this.id = id;
        this.idade = idade;
        this.CPF = CPF;
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
