/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz;

import uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz.model.TipoAssunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz.model.Atendimento;
import uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz.model.Assunto;
import java.util.Calendar;

/**
 *
 * @author Luiz Antonio and Carlos Muniz
 */
public class ListaDeAtendimentosEncerrados {

    private class Elemento {

        public Atendimento atendimento;
        public Elemento proximo;

        public Elemento(Atendimento atendimento) {
            this.atendimento = atendimento;
        }
    }

    private Elemento primeiro = null;

    private double duracaoTotal = 0d;
    private double quantidadeAssuntos = 0d;

    public void incluir(Atendimento atendimento) {
        if (this.primeiro == null)
            this.primeiro = new Elemento(atendimento);
        else
            incluir(atendimento, primeiro);
    }

    private void incluir(Atendimento atendimento, Elemento elemento) {
        if (elemento.proximo == null)
            elemento.proximo = new Elemento(atendimento);
        else
            incluir(atendimento, elemento.proximo);
    }

    public Atendimento buscar(int idCliente) {
        if (this.primeiro == null) {
            System.out.println("Não existem clientes em atendimento.");
            return null;
        } else if (this.primeiro.atendimento.getCliente().getId() == idCliente) {
            Atendimento atendimento = this.primeiro.atendimento;
            return atendimento;
        } else
            return buscar(idCliente, this.primeiro.proximo);
    }

    private Atendimento buscar(int idCliente, Elemento elemento) {
        if (elemento == null)
            return null;
        else if (elemento.atendimento.getCliente().getId() == idCliente) {
            Atendimento atendimento = elemento.atendimento;
            return atendimento;
        } else
            return buscar(idCliente, elemento.proximo);
    }

    public double calculaTempoMedioDeAtendimento(TipoAssunto tipoAssunto, Calendar dataAtendimento) {
        if (this.primeiro != null) {
            this.quantidadeAssuntos = 0d;
            this.duracaoTotal = 0d;

            HashMap.No noAtual = ServicoDeAtendimento.hashMap.consultar(tipoAssunto);
            if (noAtual != null) {
                Atendimento atendimento = noAtual.getElemento();
                while (noAtual != null) {
                    int duracao = getDuracao(atendimento, tipoAssunto, dataAtendimento);
                    if (duracao >= 0)
                        this.duracaoTotal += duracao;
                    noAtual = noAtual.getProximo();
                }

                return (this.quantidadeAssuntos > 0) ? (this.duracaoTotal / this.quantidadeAssuntos) : 0d;
            }
        }
        return 0d;
    }

    private void calculaTempoMedioDeAtendimento(TipoAssunto tipoAssunto, Calendar dataAtendimento, Elemento elemento) {
        if (elemento != null) {
            Atendimento atendimento = elemento.atendimento;
            int duracao = getDuracao(atendimento, tipoAssunto, dataAtendimento);
            if (duracao >= 0)
                this.duracaoTotal += duracao;

            calculaTempoMedioDeAtendimento(tipoAssunto, dataAtendimento, elemento.proximo);
        }

    }

    private int getDuracao(Atendimento atendimento, TipoAssunto tipoAssunto, Calendar dataAtendimento) {
        if (getMesmoDiaMesOuAno(atendimento, dataAtendimento) < 1)
            return -1;

        int duracao = 0;
        boolean possuiAssuntoDoMesmoTipo = false;
        Assunto[] assuntos = atendimento.getAssuntos();
        for (Assunto assunto : assuntos)
            if (assunto.getTipo().equals(tipoAssunto)) {
                possuiAssuntoDoMesmoTipo = true;
                int duracaoAssunto = assunto.getDuracaoAtendimento();
                duracao += duracaoAssunto;
                this.quantidadeAssuntos++;
            }

        return possuiAssuntoDoMesmoTipo ? duracao : -1;
    }

    private int getMesmoDiaMesOuAno(Atendimento atendimento, Calendar dataAtendimento) {
        int day = dataAtendimento.get(Calendar.DAY_OF_MONTH);
        int month = dataAtendimento.get(Calendar.MONTH);
        int year = dataAtendimento.get(Calendar.YEAR);

        Calendar data = atendimento.getHoraAtendimento();
        int dayToCompare = data.get(Calendar.DAY_OF_MONTH);
        int monthToCompare = data.get(Calendar.MONTH);
        int yearToCompare = data.get(Calendar.YEAR);

        if ((day != dayToCompare) || (month != monthToCompare) || (year != yearToCompare))
            return 0;
        return 1;
    }

    public void print() {
        if (primeiro != null) {
            Elemento elemento = primeiro;
            System.out.print("[" + elemento.atendimento.getCliente());

            if (elemento.proximo != null)
                System.out.print(",");

            while (elemento.proximo != null) {
                elemento = elemento.proximo;
                System.out.print(elemento.atendimento.getCliente());
                if (elemento.proximo != null)
                    System.out.print(",");
            }
            System.out.println("]");
        }
    }

}
