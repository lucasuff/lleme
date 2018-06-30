package trabalho_ed;

import java.util.Random;

/**
 *
 * @author raphael
 */
public class HashTipoAssunto {

    private class No {

        private TipoAssunto tipo;
        private No prox;
        private No ant;

        public No(TipoAssunto tipo) {
            this.tipo = tipo;
        }

        public No(TipoAssunto tipo, No ant) {
            this.tipo = tipo;
            this.ant = ant;
        }

        public TipoAssunto getTipo() {
            return tipo;
        }

        public void setTipo(TipoAssunto tipo) {
            this.tipo = tipo;
        }

        public No getProx() {
            return prox;
        }

        public void setProx(No prox) {
            this.prox = prox;
        }

        public No getAnt() {
            return prox;
        }

        public void setAnt(No prox) {
            this.prox = prox;
        }
    }

    private int tamanhoTabela;
    private No[] tipos;
    private int qtdElementos;

    ;
    public HashTipoAssunto(int tam) {
        this.tamanhoTabela = tam;
        tipos = new No[this.tamanhoTabela];
    }

    public int getTamanhoTabela() {
        return tamanhoTabela;
    }

    public void setTamanhoTabela(int tamanhoTabela) {
        this.tamanhoTabela = tamanhoTabela;
    }

    public void inserir(TipoAssunto ta) {
        int pos = funcaoHash(ta);
        if (tipos[pos] == null)
            tipos[pos] = new No(ta);
        else {
            No aux = tipos[pos];
            while (aux.prox != null)
                aux = aux.prox;
            aux.prox = new No(ta, aux);
        }
        this.qtdElementos++;
    }

    private void inserir(No noTa) {
        int pos = funcaoHash(noTa.tipo);
        if (tipos[pos] == null)
            tipos[pos] = noTa;
        else {
            No aux = tipos[pos];
            while (aux.prox != null)
                aux = aux.prox;
            noTa.ant = aux;
            aux.prox = noTa;

        }
        this.qtdElementos++;
    }

    public TipoAssunto getRandomTipoAssunto() {
        Random r = new Random();
        return tipos[r.nextInt(10)].getTipo();
    }

    public TipoAssunto getTipoAssunto(int i) {
        return tipos[i].getTipo();
    }

    public int funcaoHash(TipoAssunto ta) {
        return ta.getUrgencia() % tamanhoTabela;
    }

    public int getQuantiadadeElementos() {
        return qtdElementos;
    }

    public void setQuantiadadeElementos(int quantiadadeElementos) {
        this.qtdElementos = quantiadadeElementos;
    }

    public TipoAssunto alterarUrgencia(TipoAssunto ta, int urgencia) {
        No noTa = buscarNo(ta);
        if (noTa != null) {
            if (noTa.ant == null) {
                int pos = funcaoHash(noTa.getTipo());
                tipos[pos] = noTa.prox;
            } else {
                noTa.ant.prox = noTa.prox;
                noTa.prox.ant = noTa.ant;
            }
            noTa.tipo.setUrgencia(urgencia);
            noTa.prox = null;
            noTa.ant = null;
            this.qtdElementos--;
            inserir(noTa);
            return noTa.tipo;
        } else {
            System.out.println("Objeto não encontrado");
            return null;
        }
    }

    public TipoAssunto buscar(TipoAssunto ta) {
        if (ta != null) {
            No busca = buscarNo(ta);
            if (busca != null)
                return busca.tipo;
            return null;
        } else
            return null;
    }

    public No buscarNo(TipoAssunto ta) {
        int pos = funcaoHash(ta);
        if (ta.getId() == tipos[pos].tipo.getId())
            return tipos[pos];
        else {
            HashTipoAssunto.No curr_node = tipos[pos];
            while (curr_node != null) {
                if (curr_node.tipo.getId() == ta.getId())
                    return curr_node;
                curr_node = curr_node.prox;
            }
        }
        return null;
    }
}
