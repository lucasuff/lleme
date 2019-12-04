package uff.ic.lleme.tcc00328.trabalhos.s20192.trab_final.src.trab_final;

public class produto {

    private String nome;
    private int cod;
    private float preco;

    public produto(String nome, int cod, float preco) {
        this.nome = nome;
        this.cod = cod;
        this.preco = preco;
    }

    public void info() {
        System.out.printf("=================================\n");
        System.out.printf("nome: %s\ncodigo: %d\npre�o: %f\n", this.nome, this.cod, this.preco);
        System.out.printf("=================================\n");

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
}
