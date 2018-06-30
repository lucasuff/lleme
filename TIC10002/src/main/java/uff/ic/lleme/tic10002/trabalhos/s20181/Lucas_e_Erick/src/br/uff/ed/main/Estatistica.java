package br.uff.ed.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("rawtypes")
public class Estatistica implements Serializable, Comparable, Hasheavel {

    private static final long serialVersionUID = -5667115397060963822L;
    private LocalDate data;
    private TipoAssunto[] tipoassunto;
    private int[] aparicoes;
    private long[] totalemsegundos;

    public Estatistica() {
    }

    public Estatistica(LocalDate data, int quantidadeDeTiposDeAssuntosPorDia) {
        this.data = data;
        this.tipoassunto = new TipoAssunto[quantidadeDeTiposDeAssuntosPorDia];
        this.aparicoes = new int[quantidadeDeTiposDeAssuntosPorDia];
        this.totalemsegundos = new long[quantidadeDeTiposDeAssuntosPorDia];
    }

    public TipoAssunto[] getTipoassunto() {
        return tipoassunto;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setTipoassunto(TipoAssunto[] tipoassunto) {
        this.tipoassunto = tipoassunto;
    }

    public int[] getAparicoes() {
        return aparicoes;
    }

    public void setAparicoes(int[] aparicoes) {
        this.aparicoes = aparicoes;
    }

    public long[] getTotalemsegundos() {
        return totalemsegundos;
    }

    public void setTotalemsegundos(long[] totalemsegundos) {
        this.totalemsegundos = totalemsegundos;
    }

    public int getAparicao(int tipoassuntoid) {
        return this.aparicoes[tipoassuntoid];
    }

    public long getTotalEmSegundos(int tipoassuntoid) {
        return this.totalemsegundos[tipoassuntoid];
    }

    public void setAparicao(int tipoassuntoid, int aparicao) {
        this.aparicoes[tipoassuntoid] += aparicao;
    }

    public void setTotalEmSegundos(int tipoassuntoid, long l) {
        this.totalemsegundos[tipoassuntoid] += l;
    }

    public long estimarTamanho() {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baos.toByteArray().length;
    }

    @Override
    public long hashcode() {
        long[] chaves = new long[3];
        long dia = this.data.getDayOfMonth();
        long ano = this.data.getYear();
        long mes = this.data.getMonthValue();
        String anostring = Long.toString(ano);
        chaves[0] = ((Long.parseLong(anostring.substring(1, 2)) + Long.parseLong(anostring.substring(2, 3))) % 10L)
                * 10L
                + ((Long.parseLong(anostring.substring(0, 1)) + Long.parseLong(anostring.substring(3, 4))) % 10L);
        chaves[1] = mes * 100L + dia;
        chaves[2] = Integer.parseInt(anostring);
        return (long) (chaves[0] * 100000000L + chaves[1] * 10000L + chaves[2]);
    }

    public long getChave(LocalDate data) {
        Estatistica tmp = new Estatistica();
        tmp.setData(data);
        return tmp.hashcode();
    }

    @Override
    public int compareTo(Object arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
}
