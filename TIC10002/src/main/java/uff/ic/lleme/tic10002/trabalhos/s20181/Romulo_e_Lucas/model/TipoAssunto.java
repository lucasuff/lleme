package uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.model;

public class TipoAssunto {

    private int tipo;
    private String titulo;
    private int urgencia;

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(int urgencia) {
        this.urgencia = urgencia;
    }

    public TipoAssunto(int tipo, String titulo, int urgencia) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.urgencia = urgencia;
    }
}
