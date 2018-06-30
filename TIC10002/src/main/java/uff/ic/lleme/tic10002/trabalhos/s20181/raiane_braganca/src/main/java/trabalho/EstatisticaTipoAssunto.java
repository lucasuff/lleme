package trabalho;

public class EstatisticaTipoAssunto {

    public int idTipo;
    public int totalAtendimentos = 1;
    public double tempoTotal = 0.0;

    public EstatisticaTipoAssunto(int idTipo, double tempoTotal) {
        this.idTipo = idTipo;
        this.tempoTotal = tempoTotal;
    }

    public void atualizaTempo(double tempoTotal) {
        this.tempoTotal += tempoTotal;
        this.totalAtendimentos++;
    }

    public static EstatisticaTipoAssunto buscaElemento(
            int tipo,
            ListaLigada<EstatisticaTipoAssunto> listaPorTipoAssuntos
    ) {
        EstatisticaTipoAssunto tipoAssunto;
        for (int i = 0; i < listaPorTipoAssuntos.tamanho(); i++) {
            tipoAssunto = listaPorTipoAssuntos.obtem(i);
            if (tipoAssunto.idTipo == tipo)
                return tipoAssunto;
        }
        return null;
    }
}
