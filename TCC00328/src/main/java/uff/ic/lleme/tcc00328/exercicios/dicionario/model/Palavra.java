package uff.ic.lleme.tcc00328.exercicios.dicionario.model;

import java.util.Set;
import java.util.TreeSet;

public class Palavra {

    public String palavra = null;
    public String pronuncia = null;
    public Set<Substantivo> nominais = new TreeSet<>();
    public Set<Verbo> verbais = new TreeSet<>();
    public Set<Adverbio> adverbiais = new TreeSet<>();

    public Palavra(String palavra, String pronuncia) {
        this.palavra = palavra;
        this.pronuncia = pronuncia;
    }
}
