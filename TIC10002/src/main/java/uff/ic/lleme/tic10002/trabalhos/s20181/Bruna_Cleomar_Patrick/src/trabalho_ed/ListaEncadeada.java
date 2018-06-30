package uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed;

public class ListaEncadeada {

    int tamanho = 0;
    protected No lista = null;

    public static class No {

        public ObjetoBase Item;
        public No proximo = null;

        public No(ObjetoBase item) {
            this.Item = item;
        }
    }

    public boolean vazia() {
        return this.lista == null;
    }

    public ObjetoBase Buscar(String chave) {
        No aux = lista;

        while (aux != null) {
            if (aux.Item.getChave().equals(chave))
                return aux.Item;
            aux = aux.proximo;
        }

        return null;
    }

    public void Inserir(ObjetoBase e) {

        if (this.lista == null)
            this.lista = new No(e);
        else {
            No aux = this.lista;
            while (aux.proximo != null)
                aux = aux.proximo;
            aux.proximo = new No(e);
        }
        tamanho++;
    }

    public ObjetoBase Remover(int index) {

        No aux = this.lista;

        boolean indexValido = (index >= 0 && index < tamanho);

        if (!vazia() && indexValido) {
            for (int i = 0; i < index - 1; i++)
                aux = aux.proximo;
            No retorno = aux.proximo;
            aux.proximo = aux.proximo.proximo;
            tamanho--;

            return retorno.Item;
        }

        return null;
    }

}
