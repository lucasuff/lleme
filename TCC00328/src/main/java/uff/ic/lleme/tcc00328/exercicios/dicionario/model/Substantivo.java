package uff.ic.lleme.tcc00328.exercicios.dicionario.model;

import java.util.Set;
import java.util.TreeSet;

public class Substantivo extends SynSet {

    public Set<Substantivo> hiperonimos = new TreeSet<>();
    public Set<Substantivo> hiponimos = new TreeSet<>();

    public Substantivo(String significado) {
        super(significado);
    }

}
