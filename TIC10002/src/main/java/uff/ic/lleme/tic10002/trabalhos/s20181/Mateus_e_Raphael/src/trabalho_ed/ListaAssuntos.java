package trabalho_ed;

/**
 *
 * @author mateus e
 */
public class ListaAssuntos {

    public class No {

        private Assunto assunto;
        private No proximo;

        public Assunto getAssunto() {
            return this.assunto;
        }

        public void setAssunto(Assunto assunto) {
            this.assunto = assunto;
        }

        public No getProximo() {
            return this.proximo;
        }

        public No(Assunto assunto) {
            this.assunto = assunto;
        }

        private void setProximo(No no) {
            this.proximo = no;
        }
    }

    private No primeiro = null;
    private int tamanhoLista = 0;
    private float mediaAssuntos = 0;

    public float getMediaAssuntos() {
        return mediaAssuntos;
    }

    public int getTamanhoLista() {
        return tamanhoLista;
    }

    public No getPrimeiro() {
        return primeiro;
    }

    public float calculaMediaAssuntos() {
        No aux = primeiro;
        int total = 0;
        while (aux != null) {
            total += aux.getAssunto().tipo.getUrgencia();
            aux = aux.proximo;
        }
        this.mediaAssuntos = (float) (total) / tamanhoLista;
        return this.mediaAssuntos;
    }

    public void incluir(Assunto assunto) {
        if (primeiro == null) {
            primeiro = new No(assunto);
            tamanhoLista++;
        } else {
            incluir(assunto, primeiro);
            tamanhoLista++;
        }
        calculaMediaAssuntos();
    }

    private void incluir(Assunto assunto, No no) {
        if (no.proximo == null)
            no.proximo = new No(assunto);
        else
            incluir(assunto, no.proximo);
    }

    public void remover() {
        if (tamanhoLista > 0) {
            if (primeiro.proximo != null)
                primeiro = primeiro.getProximo();
            else
                primeiro = null;
            tamanhoLista--;
        }
    }

    public void remover(String rotulo) {

        No curr_node = primeiro;
        No pai = null;

        while (curr_node != null) {
            if (rotulo.equals(curr_node.getAssunto().tipo.getTitulo()))
                if (curr_node == primeiro)
                    remover();
                else if (curr_node.getProximo() != null) {
                    pai.setProximo(curr_node.getProximo());
                    tamanhoLista--;
                } else
                    pai.setProximo(null);
            pai = curr_node;
            curr_node = curr_node.getProximo();
        }
    }

    public void finalizarAssuntos(String[] providencia, int[] duracao) {
        No aux = primeiro;
        int i = 0;
        while (aux != null) {
            aux.assunto.finalizarAssunto(providencia[i], duracao[i]);
            aux = aux.proximo;
            i++;
        }
    }

    public void imprimir() {
        No curr_node = primeiro;
        while (curr_node != null) {
            System.out.println(curr_node.getAssunto().tipo.getTitulo());
            curr_node = curr_node.getProximo();
        }
    }
}
