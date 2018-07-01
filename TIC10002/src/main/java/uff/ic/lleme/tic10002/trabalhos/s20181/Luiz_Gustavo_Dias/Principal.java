package uff.ic.lleme.tic10002.trabalhos.s20181.Luiz_Gustavo_Dias;

import uff.ic.lleme.tic10002.trabalhos.s20181.Luiz_Gustavo_Dias.model.TipoAssunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.Luiz_Gustavo_Dias.model.Cliente;
import uff.ic.lleme.tic10002.trabalhos.s20181.Luiz_Gustavo_Dias.model.Assunto;


public class Principal {

    public static void main(String[] args) {
        TipoAssunto a = new TipoAssunto("a", "pessoa f�sica", 2);
        TipoAssunto b = new TipoAssunto("b", "pessoa jur�dica", 5);
        TipoAssunto c = new TipoAssunto("c", "n�o correntista", 10);

        Assunto saquePf = new Assunto();
        saquePf.setTipoAssunto(a);
        saquePf.setDescricao("saque pf	");
        saquePf.setDuracaoAtendimentoAssunto(10);
        saquePf.setProvidencia("sacar");

        Assunto depositoPj = new Assunto();
        depositoPj.setTipoAssunto(b);
        depositoPj.setDescricao("deposito pj");
        depositoPj.setDuracaoAtendimentoAssunto(15);
        depositoPj.setProvidencia("depositar");

        Assunto pagamento = new Assunto();
        pagamento.setTipoAssunto(c);
        pagamento.setDescricao("pagamento");
        pagamento.setDuracaoAtendimentoAssunto(20);
        pagamento.setProvidencia("pagar");

        ListaAssuntos listaPadrao = new ListaAssuntos();
        listaPadrao.insere(listaPadrao, pagamento);
        listaPadrao.insere(listaPadrao, saquePf);
        listaPadrao.insere(listaPadrao, depositoPj);
        //listaPadrao.imprimeListaAssuntos();

        /**
         * ***********************************************************************************
         * Criando clientes(cpf, nome) e suas respectivas listas de assunto
         * ***********************************************************************************
         */
        Cliente LuizC = new Cliente(1011, "Luic C	", 49);
        ListaAssuntos assuntosLuizC = new ListaAssuntos();
        assuntosLuizC.insere(assuntosLuizC, saquePf);
        assuntosLuizC.insere(assuntosLuizC, depositoPj);
        assuntosLuizC.insere(assuntosLuizC, pagamento);

        Cliente Carlos = new Cliente(1013, "Carlos	", 25);
        ListaAssuntos assuntosCarlos = new ListaAssuntos();
        assuntosCarlos.insere(assuntosCarlos, saquePf);
        assuntosCarlos.insere(assuntosCarlos, depositoPj);

        Cliente Kelli = new Cliente(1012, "Kelli	", 40);
        ListaAssuntos assuntosKelli = new ListaAssuntos();
        assuntosKelli.insere(assuntosKelli, saquePf);

        Cliente Alana = new Cliente(1015, "Alana	", 27);
        ListaAssuntos assuntosAlana = new ListaAssuntos();
        assuntosAlana.insere(assuntosAlana, saquePf);

        FilaAtendimentoPrioridade fila = new FilaAtendimentoPrioridade(10);
        ListaAtendimentosEncerrados listaEncerrados = new ListaAtendimentosEncerrados();
        //fila.gerarEstatistica(listaPadrao);
        fila.recepcionar(Carlos, assuntosCarlos, listaPadrao, 9, 10);
        fila.recepcionar(LuizC, assuntosLuizC, listaPadrao, 11, 12);
        fila.recepcionar(Kelli, assuntosKelli, listaPadrao, 13, 14);
        fila.atender();
        //c.alterarPrioridadeTipoAssunto(90); //altera a prioridade do tipo de assunto
        //fila.encerrarAtendimento(listaPadrao);
        //fila.gerarEstatistica(listaPadrao);
        fila.encerrarAtendimento(listaEncerrados, listaPadrao);
        //fila.recepcionar(Alana, assuntosAlana, listaPadrao, 14, 15);
        //fila.atender();
        //fila.encerrarAtendimento(listaPadrao);
        //fila.gerarEstatistica(listaPadrao);

        //fila.encerrarAtendimento(listaPadrao);
        //fila.gerarEstatistica(listaPadrao);
        //listaPadrao.imprimeListaAssuntos();
        //assuntosLuizC.imprimeListaAssuntos();
        //assuntosKelli.imprimeListaAssuntos();
        listaEncerrados.imprimeListaEncerrados();
    }

}
