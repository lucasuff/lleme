package uff.ic.lleme.tic10002.trabalhos.s20181.Luiz_Gustavo_Dias;

import uff.ic.lleme.tic10002.trabalhos.s20181.Luiz_Gustavo_Dias.model.Assunto;


public class ListaAssuntos {

    int qtdAssuntos;
    Assunto primeiroAssunto;
    Assunto ultimoAssunto;

    //inicializando a lista de assuntos vazia
    public ListaAssuntos() {
        this.qtdAssuntos = 0;
        this.primeiroAssunto = null;
        this.ultimoAssunto = null;
    }

    /*Inserir no final = fila*/
    public void insere(ListaAssuntos lista, Assunto novo) {
        if (lista.qtdAssuntos == 0) {
            //novo.setProximoAssunto(ultimoAssunto);
            lista.primeiroAssunto = novo;
            lista.ultimoAssunto = novo;

        } else {
            lista.ultimoAssunto.setProximoAssunto(novo);
            lista.ultimoAssunto = novo;

        }

        this.qtdAssuntos++;
    }

    public void imprimeListaAssuntos() {
        if (this.qtdAssuntos == 0)
            System.out.println("A lista est� vazia!");
        else {
            Assunto aux = primeiroAssunto;
            System.out.println(qtdAssuntos);
            for (int i = 0; i < this.qtdAssuntos; i++) {
                System.out.println("Urg�ncia: " + aux.getTipoAssunto().urgencia + " / Tipo do assunto: " + aux.getTipoAssunto().titulo + " / Descri��o: " + aux.getDescricao() + " / Providencia: "
                        + aux.getProvidencia() + " / Dura��o Atendimento: " + aux.getDuracaoAtendimentoAssunto() + " Contador: " + aux.getCont());
                aux = aux.proximoAssunto;
            }
        }
    }

}
