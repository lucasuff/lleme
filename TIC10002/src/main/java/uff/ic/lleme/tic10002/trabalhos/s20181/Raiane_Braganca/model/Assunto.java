package uff.ic.lleme.tic10002.trabalhos.s20181.Raiane_Braganca.model;

import java.text.DecimalFormat;
import uff.ic.lleme.tic10002.trabalhos.s20181.Raiane_Braganca.ListaLigada;

public class Assunto {

    public TipoAssunto tipoAssunto;
    public String descricao;
    public String providencia;
    public Double duracaoAtendimento;

    public Assunto(
            TipoAssunto tipoAssunto,
            String descricao,
            String providencia,
            Double duracaoAtendimento
    ) {
        this.tipoAssunto = tipoAssunto;
        this.descricao = descricao;
        this.providencia = providencia;
        this.duracaoAtendimento = duracaoAtendimento;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String output = "Tipo de Assunto: " + this.tipoAssunto.tipo
                + "  |  Urgência: " + this.tipoAssunto.getUrgencia()
                + "  |  Descrição: " + this.descricao;
        if (this.providencia != null)
            output += "  |  Providência: " + this.providencia;
        if (this.duracaoAtendimento != null)
            output += "  |  Duração do Atendimento: " + df.format(this.duracaoAtendimento);
        return output;
    }

    public static Double calculaMediaAssuntos(ListaLigada<Assunto> assuntos) {
        double soma = 0.0;
        Assunto assunto;
        for (int i = 0; i < assuntos.tamanho(); i++) {
            assunto = assuntos.obtem(i);
            soma += assunto.tipoAssunto.getUrgencia();
        }

        return soma / (double) assuntos.tamanho();
    }

    public static void imprimeAssuntos(ListaLigada<Assunto> assuntos) {
        Assunto assunto;
        for (int i = 0; i < assuntos.tamanho(); i++) {
            assunto = assuntos.obtem(i);
            System.out.println(assunto.toString());
        }
    }
}
