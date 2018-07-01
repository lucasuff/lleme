package uff.ic.lleme.tic10002.trabalhos.s20181.Luiz_Gustavo_Dias;


public class ListaAtendimentosEncerrados {

    int qtdAtendimentosEncerrados;
    Atendimento primeiroAtendimento;
    Atendimento ultimoAtendimento;

    public ListaAtendimentosEncerrados() {
        this.qtdAtendimentosEncerrados = 0;
        this.primeiroAtendimento = null;
        this.ultimoAtendimento = null;
    }

    public void insereAtendimentoEncerrado(ListaAtendimentosEncerrados lista, Atendimento atendimentoEncerrado) {
        if (lista.qtdAtendimentosEncerrados == 0) {
            //novo.setProximoAssunto(ultimoAssunto);
            lista.primeiroAtendimento = atendimentoEncerrado;
            lista.ultimoAtendimento = atendimentoEncerrado;

        } else {
            lista.ultimoAtendimento.proximo = atendimentoEncerrado;
            lista.ultimoAtendimento = atendimentoEncerrado;

        }

        this.qtdAtendimentosEncerrados++;
    }

    public void imprimeListaEncerrados() {
        if (this.qtdAtendimentosEncerrados == 0)
            System.out.println("A lista est� vazia!");
        else {
            Atendimento aux = primeiroAtendimento;
            System.out.println("\n**Lista de Atendimentos Encerrados**");
            for (int i = 0; i < this.qtdAtendimentosEncerrados; i++) {
                System.out.println("Cliente: " + aux.cliente.nome + " / Tipo do assunto: " + aux.assuntos.primeiroAssunto.descricao + " / Tipo: " + aux.assuntos.primeiroAssunto.tipoAssunto.titulo);
                aux = aux.proximo;
            }
        }
    }
}
