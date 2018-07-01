package uff.ic.lleme.tic10002.trabalhos.s20181.Raiane_Braganca.model;

public class TipoAssunto {

    public int tipo;
    public String titulo;
    private int urgencia;

    public TipoAssunto(int tipo, String titulo, int urgencia) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.setUrgencia(urgencia);
    }

    public int getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(int urgencia) {
        if (urgencia < 0 || urgencia > 10)
            throw new IllegalArgumentException("O grau de urgência deve ser de 0 a 10.");
        else
            this.urgencia = urgencia;
    }
}
