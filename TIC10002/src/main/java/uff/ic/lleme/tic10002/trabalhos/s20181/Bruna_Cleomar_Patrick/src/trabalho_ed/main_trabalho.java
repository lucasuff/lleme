package uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed;

import java.util.Date;
import java.util.Random;

public class main_trabalho {

    public static void main(String[] args) {

        Estatisticas estatisticas = new Estatisticas(365);
        Atendimentos Atendimentos = new Atendimentos(100, estatisticas);

        //Inicia um novo objeto estat�stica para o dia que come�a
        Date hoje = new Date();

        //Acesso para obter Estatisticas do dia corrente em O(1)
        estatisticas.IncluirEstatisticaDiaria(hoje);

        /*Simulando recepcionamento cliente 1*/
        Cliente cliente1 = new Cliente("cpf1", "Ana", 52);
        Atendimento atendimentoCliente1 = new Atendimento(cliente1);
        atendimentoCliente1.IncluirAssunto((TipoAssunto) Atendimentos.getTiposAssuntos().Buscar("BOLETO"), "Cobrança feita em duplicidade.");
        atendimentoCliente1.IncluirAssunto((TipoAssunto) Atendimentos.getTiposAssuntos().Buscar("CANCELAMENTO"), "Cancelar todos os serviços.");
        Atendimentos.Recepcionar(atendimentoCliente1);

        /*Simulando recepcionamento cliente 2*/
        Cliente cliente2 = new Cliente("cpf2", "Joaquim", 85);
        Atendimento atendimentoCliente2 = new Atendimento(cliente2);
        atendimentoCliente2.IncluirAssunto((TipoAssunto) Atendimentos.getTiposAssuntos().Buscar("BOLETO"), "Solicita segunda via de boleto.");
        Atendimentos.Recepcionar(atendimentoCliente2);

        /*Simulando recepcionamento cliente 3*/
        Cliente cliente3 = new Cliente("cpf3", "Paulo", 72);
        Atendimento atendimentoCliente3 = new Atendimento(cliente3);
        atendimentoCliente3.IncluirAssunto((TipoAssunto) Atendimentos.getTiposAssuntos().Buscar("BOLETO"), "Solicita segunda via de boleto.");
        atendimentoCliente3.IncluirAssunto((TipoAssunto) Atendimentos.getTiposAssuntos().Buscar("VELOCIDADE"), "Internet muito lenta.");
        Atendimentos.Recepcionar(atendimentoCliente3);

        /*Simulando recepcionamento cliente 4*/
        Cliente cliente4 = new Cliente("cpf4", "Juvenal", 20);
        Atendimento atendimentoCliente4 = new Atendimento(cliente4);
        atendimentoCliente4.IncluirAssunto((TipoAssunto) Atendimentos.getTiposAssuntos().Buscar("SENHA"), "Esqueceu senha de acesso.");
        Atendimentos.Recepcionar(atendimentoCliente4);

        /*Simulando recepcionamento cliente 4*/
        Cliente cliente5 = new Cliente("cpf5", "Bia", 20);
        Atendimento atendimentoCliente5 = new Atendimento(cliente5);
        atendimentoCliente5.IncluirAssunto((TipoAssunto) Atendimentos.getTiposAssuntos().Buscar("CANCELAMENTO"), "Cobrança indevida. Quer cancelar o serviço.");
        Atendimentos.Recepcionar(atendimentoCliente5);

        /*pegando o próximo */
        Atendimento proximoAtendimento = Atendimentos.Atender();

        Random rand = new Random();
        /**
         * * Simulando Atendente varrendo lista de assuntos e resolvendo um a um **
         */
        for (int i = 0; i < proximoAtendimento.getAssuntosTratados().getTamanho(); i++) {
            /*Busca Assunto a ser tratado*/
            Assunto a = (Assunto) proximoAtendimento.getAssuntosTratados().get(i);
            /*Random usado para definir um tempo aleatório de atendimento*/
            int randomTime = rand.nextInt((20 - 10) + 1) + 10; // ((Max - Min) + 1) - Min
            /* informa duração do atendimento para o assunto e providência tomada*/
            a.setDuracaoAtendimento(randomTime);
            a.setProvidencias("Providência_" + Integer.toString(randomTime));
        }

        /*Encerrando atendimento*/
        Atendimentos.Encerrar(proximoAtendimento);

        /*pegando o próximo */
        proximoAtendimento = Atendimentos.Atender();

        /**
         * * Simulando Atendente varrendo lista de assuntos e resolvendo um a um **
         */
        for (int i = 0; i < proximoAtendimento.getAssuntosTratados().getTamanho(); i++) {
            /*Busca Assunto a ser tratado*/
            Assunto a = (Assunto) proximoAtendimento.getAssuntosTratados().get(i);
            /*Random usado para definir um tempo aleatório de atendimento*/
            int randomTime = rand.nextInt((20 - 10) + 1) + 10; // ((Max - Min) + 1) - Min
            /* informa duração do atendimento para o assunto e providência tomada*/
            a.setDuracaoAtendimento(randomTime);
            a.setProvidencias("Providência_" + Integer.toString(randomTime));
        }

        /*Encerrando atendimento*/
        Atendimentos.Encerrar(proximoAtendimento);

        /*pegando o próximo */
        proximoAtendimento = Atendimentos.Atender();

        /**
         * * Simulando Atendente varrendo lista de assuntos e resolvendo um a um **
         */
        for (int i = 0; i < proximoAtendimento.getAssuntosTratados().getTamanho(); i++) {
            /*Busca Assunto a ser tratado*/
            Assunto a = (Assunto) proximoAtendimento.getAssuntosTratados().get(i);
            /*Random usado para definir um tempo aleatório de atendimento*/
            int randomTime = rand.nextInt((20 - 10) + 1) + 10; // ((Max - Min) + 1) - Min
            /* informa duração do atendimento para o assunto e providência tomada*/
            a.setDuracaoAtendimento(randomTime);
            a.setProvidencias("Providência_" + Integer.toString(randomTime));
        }

        /*Encerrando atendimento*/
        Atendimentos.Encerrar(proximoAtendimento);

        /*pegando o próximo */
        proximoAtendimento = Atendimentos.Atender();

        /**
         * * Simulando Atendente varrendo lista de assuntos e resolvendo um a um **
         */
        for (int i = 0; i < proximoAtendimento.getAssuntosTratados().getTamanho(); i++) {
            /*Busca Assunto a ser tratado*/
            Assunto a = (Assunto) proximoAtendimento.getAssuntosTratados().get(i);
            /*Random usado para definir um tempo aleatório de atendimento*/
            int randomTime = rand.nextInt((20 - 10) + 1) + 10; // ((Max - Min) + 1) - Min
            /* informa duração do atendimento para o assunto e providência tomada*/
            a.setDuracaoAtendimento(randomTime);
            a.setProvidencias("Providência_" + Integer.toString(randomTime));
        }

        /*Encerrando atendimento*/
        Atendimentos.Encerrar(proximoAtendimento);

        /*pegando o próximo */
        proximoAtendimento = Atendimentos.Atender();

        /**
         * * Simulando Atendente varrendo lista de assuntos e resolvendo um a um **
         */
        for (int i = 0; i < proximoAtendimento.getAssuntosTratados().getTamanho(); i++) {
            /*Busca Assunto a ser tratado*/
            Assunto a = (Assunto) proximoAtendimento.getAssuntosTratados().get(i);
            /*Random usado para definir um tempo aleatório de atendimento*/
            int randomTime = rand.nextInt((20 - 10) + 1) + 10; // ((Max - Min) + 1) - Min
            /* informa duração do atendimento para o assunto e providência tomada*/
            a.setDuracaoAtendimento(randomTime);
            a.setProvidencias("Providência_" + Integer.toString(randomTime));
        }

        /*Encerrando atendimento*/
        Atendimentos.Encerrar(proximoAtendimento);

        /*Gerando estatísticas para o dia */
        estatisticas.GerarEstatistica(hoje);

    }
}
