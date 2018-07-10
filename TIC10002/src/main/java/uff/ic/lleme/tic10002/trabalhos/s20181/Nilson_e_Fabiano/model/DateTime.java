package uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano.model;

import java.util.Date;

/**
 * Classe com métodos estáticos que permitem controlar qual a informação de
 * tempo atual retornada por "getTime" os demais métodos utilizarão.
 *
 * Útil para realização de testes controlados.
 *
 * @author Nilson e Fabiano
 */
public class DateTime {

    static long tm;                    // registrador interno de tempo
    static boolean modoTeste = false;  // indica o modo de operacao

    /**
     * Faz com que "getTime" retorne o tempo marcado no relogio de sistema
     */
    public static void setModoNormal() {
        modoTeste = false;
    }

    /**
     * Faz com que "getTime" retorne o tempo setado por "setTime" ou o tempo
     * retornado pela ultima chamada a "getTime" acrescido de 5 minutos.
     */
    public static void setModoTeste() {
        modoTeste = true;
    }

    /**
     * Retorna a hora a partir da meia noite de 1/1/1970 em SEGUNDOS ou,
     * estanddo no modo teste, o que ocorrer primeiro: ou o tempo informado por
     * "setTime" ou o tempo desde a ultima chamada a "getTime" mais 5 minutos.
     */
    public static long getTime() {
        if (!modoTeste)
            // retorna a hora em SEGUNDOS
            return new Date().getTime() / 1000;

        long t = tm;
        tm += 5 * 60; // adiciona cinco minutos
        return t;
    }

    /**
     * Seta o tempo "t" a ser retornado por getTime.
     */
    public static void setTime(long t) {
        tm = t;
    }

}
