/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tcc00328.trabalhos.s20192.Pedro_Bazilio_Victor_Brandão_Felipe_Esser.Lobisomem.papeis;

/**
 *
 * @author MarcosFrederico
 */
public class Aldeao extends Papel {

    public Aldeao() {
        super(true);
        id = "Aldeão";
    }

    @Override
    public void acaoNoturnaPlayer() {
        System.out.println("Você é o " + id + ".");
        System.out.println("Nao há ações a serem tomadas");
    }

    @Override
    public void acaoNoturnaIA() {
    }
}
