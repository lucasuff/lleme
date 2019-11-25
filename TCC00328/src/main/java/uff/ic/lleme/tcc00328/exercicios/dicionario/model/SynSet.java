package uff.ic.lleme.tcc00328.exercicios.dicionario.model;

public abstract class SynSet {

    public String id = null;
    public String significado = null;

    public SynSet(String significado) {
        this.significado = significado;
    }
}
