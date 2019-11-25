package uff.ic.lleme.tcc00328.exercicios.dicionario.model;

import java.util.Set;
import java.util.TreeSet;

public class Verbo extends SynSet {

    public Set<Verbo> hiperonimos = new TreeSet<>();
    public Set<Verbo> troponimos = new TreeSet<>();

    public Verbo(String significado) {
        super(significado);
    }

}
