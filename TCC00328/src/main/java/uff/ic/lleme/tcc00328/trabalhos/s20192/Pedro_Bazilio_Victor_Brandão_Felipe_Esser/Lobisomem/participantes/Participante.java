
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tcc00328.trabalhos.s20192.Pedro_Bazilio_Victor_Brandão_Felipe_Esser.Lobisomem.participantes;

import uff.ic.lleme.tcc00328.trabalhos.s20192.Pedro_Bazilio_Victor_Brandão_Felipe_Esser.Lobisomem.papeis.Papel;

/**
 *
 * @author MarcosFrederico
 */
public abstract class Participante {

    private String nome;
    protected Papel papel;
    protected Mesa mesa;

    public void setMesa(Mesa mesa) {
        if (this.mesa == null)
            this.mesa = mesa;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public final void setNome(String nome) {
        this.nome = nome;
    }

    public final String getNome() {
        return nome;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
        papel.setDono(this);
    }

    public final Papel getPapel() {
        return papel;
    }

    public abstract Participante getVoto();

    public abstract void acusar(Participante participante);

    public abstract void apoiar(Participante participante);

    public abstract void concordar(Participante participante, String sobre);

    public abstract void discordar(Participante participante, String sobre);

    public abstract boolean falarNada();
}
