package uff.ic.lleme.tic10002.trabalhos.s20181.Luiz_Gustavo_Dias;


public class TipoAssunto {

    String tipo;
    String titulo;
    int urgencia;

    public TipoAssunto(String tipo, String titulo, int urgencia) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.urgencia = urgencia;
    }

    public void alterarPrioridadeTipoAssunto(int urgencia) {
        this.urgencia = urgencia;
    }
}
