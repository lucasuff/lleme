/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio;

import uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.model.TipoAssunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.model.Assunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.model.Atendimento;
import uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.model.Cliente;
import uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.model.SAC;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    /**
     * @param args the command line arguments
     */
    SAC servicoAtendimento;
    static boolean debugMode = true;

    public static void main(String[] args) throws InterruptedException, ParseException {
        SAC servicoAtendimento = new SAC(debugMode);

        /* Efetua recepções */
        simularRecepcoes(servicoAtendimento);

        while (!servicoAtendimento.filaDeAtendimentoVazia()) {
            /* Atende o atendimento com maior prioridade */
            Atendimento atual = servicoAtendimento.atender();

            /* Registra providências e encerra o atendimento */
            for (int j = 0; j < atual.getAssuntos().length; j++) {
                atual.getAssuntos()[j].setProvidencias(getProvidencia(atual.getAssuntos()[j].getTipoAssunto().getTipo()));
                atual.getAssuntos()[j].setDuracaoAtendimento((int) ((Math.random() * 60)) + 1);
            }

            servicoAtendimento.encerrar(atual);

            if (debugMode) {
                // Teste de mapeamento de chave (função hash)
                System.out.println("Hash retornado: " + servicoAtendimento.getAtendimentosEncerrados().getHash(getDataDoSistema()));
                System.out.println("Atendimento encerrado recuperado, do cliente: " + servicoAtendimento.getAtendimentosEncerrados().get(getDataDoSistema())[0].getCliente().getNome());
            }
        }
        servicoAtendimento.gerarEstatistica(getDataDoSistema());
    }

    public static void simularRecepcoes(SAC servicoAtendimento) throws InterruptedException {
        Cliente joao = new Cliente("111111111111", "João", 65); //Transferencia internacional e encerrar conta
        Cliente alister = new Cliente("22222222222", "Alister", 32); //Abertura de conta, depósito e investimento CDB
        Cliente maria = new Cliente("33333333333", "Maria", 80); //Consulta saldo, transferencia entre contas e saque
        Cliente luana = new Cliente("44444444444", "Luana", 23); //Investimento CDB, Investimento em moeda estrangeira, Transferencia Interbanco
        Cliente jorge = new Cliente("55555555555", "Jorge", 40); //Saque
        TipoAssunto tipo1 = new TipoAssunto(1, "Consulta de Saldo", 1);
        TipoAssunto tipo2 = new TipoAssunto(2, "Saque", 2);
        TipoAssunto tipo3 = new TipoAssunto(3, "Transferencia Internacional", 9);
        TipoAssunto tipo4 = new TipoAssunto(4, "Transferencia inter-banco", 6);
        TipoAssunto tipo5 = new TipoAssunto(5, "Transferencia entre contas", 3);
        TipoAssunto tipo6 = new TipoAssunto(6, "Abertura de conta", 4);
        TipoAssunto tipo7 = new TipoAssunto(7, "Encerramento de conta", 5);
        TipoAssunto tipo8 = new TipoAssunto(8, "Investimento CDB", 7);
        TipoAssunto tipo9 = new TipoAssunto(9, "Investimento Moeda estrangeira", 10);
        TipoAssunto tipo10 = new TipoAssunto(10, "Depósito", 8);
        TipoAssunto tipoParaDebug = new TipoAssunto(10, "Depósito milionário", 80); // Forçando uma urgência exageradamente alta
        Assunto[] assuntosJoao = {new Assunto(tipo3, "Preciso transferir dinheiro para minha conta em Londres."),
            new Assunto(tipo10, "E encerrar minha conta do BancoED.")};
        Assunto[] assuntosAlister = {new Assunto(tipo6, "Desejo abrir uma conta"),
            new Assunto(tipo10, "Realizar um depósito de 10000 reais"),
            new Assunto(tipo8, "E investir 5000 reais no CDB")};
        Assunto[] assuntosMaria = {new Assunto(tipo1, "Desejo consultar meu saldo"),
            new Assunto(tipo5, "Transferir 20000 para outra conta do BancoED"),
            new Assunto(tipo2, "E sacar 500 reais")};
        Assunto[] assuntosLuana = {new Assunto(tipo8, "Desejo investir 10000 no CDB"),
            new Assunto(tipo9, "Comprar 1000 dólares"),
            new Assunto(tipo4, "E transferir 6000 para minha conta do Utaí.")};
        Assunto[] assuntosJorge = {new Assunto(tipo2, "Desejo sacar 5000 reais")};
        servicoAtendimento.recepcionar(joao, assuntosJoao);
//        TimeUnit.SECONDS.sleep(15);
        servicoAtendimento.recepcionar(alister, assuntosAlister);
//        TimeUnit.SECONDS.sleep(15);
        servicoAtendimento.recepcionar(maria, assuntosMaria);
//        TimeUnit.SECONDS.sleep(15);
        servicoAtendimento.recepcionar(luana, assuntosLuana);
//        TimeUnit.SECONDS.sleep(15);
        servicoAtendimento.recepcionar(jorge, assuntosJorge);
    }

    private static String getDataDoSistema() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    private static String getProvidencia(int tipoAssunto) {
        switch (tipoAssunto) {
            case 1:
                return "O extrato bancário entregue ao cliente.";
            case 2:
                return "Quantia entregue ao cliente.";
            case 3:
                return "Transferência internacional efetuada com sucesso.";
            case 4:
                return "Transferência entre bancos efetuada com sucesso.";
            case 5:
                return "Transferência entre contas efetuada com sucesso.";
            case 6:
                return "Uma conta foi aberta para o cliente.";
            case 7:
                return "A conta foi encerrada e um comprovante foi entregue.";
            case 8:
                return "Foi investido a quantia informada em CDB.";
            case 9:
                return "Foi realizada a compra da quantia em dólares.";
            case 10:
                return "Foi adicionado à contado cliente a quantia apresentada.";
            default:
                return "O banco não oferece esse serviço.";
        }
    }

}
