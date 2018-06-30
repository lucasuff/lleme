package uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.src.model;

/*
Cada tipo de assunto está associado a um grau de urgência de
atendimento de 0 a 10, segundo definido em uma tabela de assuntos (TipoAssunto
{tipo, título, urgência}).
 */
public class TipoAssunto {

    private int tipo;
    private String titulo;
    private int urgencia;

    public TipoAssunto(int tipo, String titulo, int urgencia) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.urgencia = urgencia;
    }

    public int getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getUrgencia() {
        return urgencia;
    }

    @Override
    public String toString() {
        return "Tipo: " + tipo + " - (" + titulo + ") - URG:" + urgencia;
    }

}
