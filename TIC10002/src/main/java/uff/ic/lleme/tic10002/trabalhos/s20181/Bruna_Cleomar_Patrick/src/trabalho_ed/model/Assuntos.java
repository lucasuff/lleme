package uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed.model;

import uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed.ListaEstatica;
import uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed.model.Assunto;

/*  A classe Assuntos representa uma cole��o de Assuntos tratados durante um Atendimento a um Cliente
 *  Herda de ListaEstatica.
 *  Optamos por uma Lista Estatica pois durante um atendimento cada Assunto precisa ser acessado de forma linear para ter a provid�ncia
 *  e a dura��o do atendimento informados.
 *  */
public class Assuntos extends ListaEstatica {

    private int sumUrgencias;

    public Assuntos(int tamanho) {
        super(tamanho);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean Inserir(Object e) {
        if (this.getTamanho() + 1 <= tamanho) {
            Assunto ass = (Assunto) e;
            this.sumUrgencias += ass.getTipoAssunto().getUrgencia();
            ((Object[]) this.lista)[indice++] = e;
            return true;
        }
        return false;
    }

    public int getUrgenciaMedia() {
        return this.sumUrgencias / (indice + 1);
    }
}
