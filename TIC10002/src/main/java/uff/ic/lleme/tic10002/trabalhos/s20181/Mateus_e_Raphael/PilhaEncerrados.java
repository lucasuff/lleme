package uff.ic.lleme.tic10002.trabalhos.s20181.Mateus_e_Raphael;

import uff.ic.lleme.tic10002.trabalhos.s20181.Mateus_e_Raphael.model.Atendimento;

/**
 *
 * @author mateu
 */
public class PilhaEncerrados {

    public class No {

        private Atendimento atendimento;
        private No proximo;

        public No(Atendimento atendimento) {
            this.atendimento = atendimento;
        }

        public No getProximo() {
            return this.proximo;
        }

        public void setProximo(No no) {
            this.proximo = no;
        }

        public Atendimento getAtendimento() {
            return atendimento;
        }

        public void setAtendimento(Atendimento atendimento) {
            this.atendimento = atendimento;
        }
    }

    private No primeiro = null;
    private int tamanhoPilha;

    public int getTamanhoPilha() {
        return tamanhoPilha;
    }

    public void setTamanhoPilha(int i) {
        this.tamanhoPilha = i;
    }

    public No getPrimeiro() {
        return this.primeiro;
    }

    public void setPrimeiro(Atendimento atend) {
        this.primeiro = new No(atend);
    }

    public void incluir(Atendimento atendimento) {
        if (primeiro == null) {
            primeiro = new No(atendimento);
            tamanhoPilha++;
        } else {
            incluir(atendimento, primeiro);
            tamanhoPilha++;
        }
    }

    private void incluir(Atendimento atendimento, No no) {
        if (no.proximo == null)
            no.proximo = new No(atendimento);
        else
            incluir(atendimento, no.proximo);
    }

    public Atendimento remover() {
        if (tamanhoPilha > 0) {
            No curr_node = primeiro;
            No pai = null;

            while (curr_node.getProximo() != null) {
                pai = curr_node;
                curr_node = curr_node.getProximo();
            }

            if (pai == null) {
                primeiro = null;
                tamanhoPilha--;
            } else {
                pai.setProximo(null);
                tamanhoPilha--;
            }
            return curr_node.getAtendimento();
        } else
            return null;
    }

    public void imprimir() {
        No curr_node = primeiro;
        while (curr_node != null) {
            System.out.println(curr_node.getAtendimento().getCliente().getNome());
            curr_node = curr_node.getProximo();
        }
    }
}
