package trabalho_ed;

/**
 *
 * @author raphael
 */
public class HashEstatistica {

    private class Estatistica {

        private int quantidade;
        private int totalMinutos;

        public Estatistica() {
            this.quantidade = 0;
            this.totalMinutos = 0;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public int getTotalMinutos() {
            return totalMinutos;
        }

        public void setTotalMinutos(int totalMinutos) {
            this.totalMinutos = totalMinutos;
        }

        public void add(int minutos) {
            this.quantidade++;
            this.totalMinutos += minutos;
        }

        public float getMedia() {
            if (this.quantidade != 0)
                return this.totalMinutos / this.quantidade;
            return 0;
        }
    }

    private int tamanhoTabela;
    private Estatistica[] estatistica;

    public HashEstatistica(int tamanhoTabela) {
        this.tamanhoTabela = tamanhoTabela;
        this.estatistica = new Estatistica[tamanhoTabela];
        for (int i = 0; i < tamanhoTabela; i++)
            this.estatistica[i] = new Estatistica();
    }

    public int getTamanhoTabela() {
        return tamanhoTabela;
    }

    public void setTamanhoTabela(int tamanhoTabela) {
        this.tamanhoTabela = tamanhoTabela;
    }

    public void atualizar(Assunto assunto) {
        int pos = funcaoHash(assunto.getTipo());
        this.estatistica[pos].add(assunto.getDuracaoAtendimento());
    }

    public int funcaoHash(TipoAssunto tipo) {
        return tipo.getId() % tamanhoTabela;
    }

    public void imprimir() {
        for (int i = 0; i < this.tamanhoTabela; i++)
            System.out.println("[" + i + " - " + this.estatistica[i].getMedia() + " ] ");
    }
}
