/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhojava;

/**
 *
 * @author Breno
 */
public class Resumo {

    //int[] alldays;
    dias[] dia;
    //Cliente[] ClienteAtendidos;

    public Resumo(int Qdias) {
        this.dia = new dias[Qdias];
    }

    public void resumofinal() {
        int totalDias = dia.length;
        System.out.println("_________________________________________________");
        System.out.println("Total de dias: " + dia.length);
    }

    class dias {

        Cliente[] clientes;
    }
}
