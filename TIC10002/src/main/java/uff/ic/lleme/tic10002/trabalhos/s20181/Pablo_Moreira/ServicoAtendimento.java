/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Pablo_Moreira;

import uff.ic.lleme.tic10002.trabalhos.s20181.Pablo_Moreira.model.TipoAssunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.Pablo_Moreira.model.Assunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.Pablo_Moreira.model.Cliente;
import uff.ic.lleme.tic10002.trabalhos.s20181.Pablo_Moreira.model.Atendimento;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author pablomoreira
 */
public class ServicoAtendimento {

    private HeapEsperaAtendimento esperaAtendimento;
    private Atendimento atendimentoAtual;
    private TipoAssunto tabelaTiposDeAssunto[];
    private Cliente[] clientesDB;
    private HashAssuntos historicoDiarioDeAssuntos;
    private Date dataCorrente;
    private final static SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd");

    private final static int NUM_DE_TIPOS_DE_ASSUNTO = 10;

    public ServicoAtendimento() {
        esperaAtendimento = new HeapEsperaAtendimento();
        atendimentoAtual = null;
        tabelaTiposDeAssunto = CriadorDataset.criarTiposDeAssunto(NUM_DE_TIPOS_DE_ASSUNTO);
        clientesDB = CriadorDataset.criarClientes(50);
        historicoDiarioDeAssuntos = new HashAssuntos(tabelaTiposDeAssunto);
        dataCorrente = new Date();

    }

    public void recepcionar() {
        Atendimento novo = CriadorDataset.gerarAtendimento(clientesDB, tabelaTiposDeAssunto);

        Date data = new Date();
        String strData = dtf.format(data);
        String strDataCorrente = dtf.format(dataCorrente);
        if (strData.equals(strDataCorrente) == false) {
            dataCorrente = data;
            historicoDiarioDeAssuntos = new HashAssuntos(tabelaTiposDeAssunto);

        }
        novo.setHoraChegada(dataCorrente);

        float urgencias = 0.0f;
        for (Assunto assunto : novo.getAssunto())
            urgencias += tabelaTiposDeAssunto[assunto.getTipo()].getUrgencia();
        float prioridade = (novo.getCliente().getIdade() / 65) + (urgencias / novo.getAssunto().length) / 10;

        esperaAtendimento.adicionar(novo, prioridade);
    }

    public void atender() {
        atendimentoAtual = esperaAtendimento.obterAtendimento();
        Date dataAtendimento = new Date();
        atendimentoAtual.setHoraAtendimento(dataAtendimento);
    }

    public void encerrar() {
        Assunto[] assuntos = atendimentoAtual.getAssunto();
        int duracaoTotal = 0;
        for (Assunto assunto : assuntos) {
            int duracao = 1 + (int) (Math.random() * 15);
            assunto.setDuracao(duracao);
            duracaoTotal += duracao;
            historicoDiarioDeAssuntos.inserir(assunto);
        }
        atendimentoAtual = null;

    }

    public void gerarEstatistica() {
        System.out.println("Estatística do dia:");
        System.out.println("assunto\ttempo médio");
        for (TipoAssunto tipoAssunto : tabelaTiposDeAssunto) {
            String saida = tipoAssunto.getTítulo() + "\t";

            ListaDeAssuntos assuntos = historicoDiarioDeAssuntos.obterAssuntosPorTipo(tipoAssunto);

            float media = assuntos.obterMediaDeTempos();

            saida += Float.toString(media);
            System.out.println(saida);

        }
        System.out.println("Fim da estatística");
    }

}
