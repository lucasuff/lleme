package uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano;

/**
 * Clase que gera os objetos Tipo de Assunto tal como descritos na especificação
 * do trabalho.
 *
 * @author Nilson e Fabiano
 */
public class TipoAssunto {

    private final int tipo;
    private final String titulo;
    private int urgencia;

    public TipoAssunto(int tipo, String titulo, int urgencia) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.urgencia = urgencia;
    }

    public int getTipo() {
        return tipo;
    }

    public int getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(int urgencia) {
        this.urgencia = urgencia;
    }

    public String getTitulo() {
        return titulo;
    }
}
