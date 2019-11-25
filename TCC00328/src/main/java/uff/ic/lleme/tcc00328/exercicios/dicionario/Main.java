package uff.ic.lleme.tcc00328.exercicios.dicionario;

import uff.ic.lleme.tcc00328.exercicios.dicionario.controle.Dicionario;
import uff.ic.lleme.tcc00328.exercicios.dicionario.model.Palavra;
import uff.ic.lleme.tcc00328.exercicios.dicionario.model.Substantivo;
import uff.ic.lleme.tcc00328.exercicios.dicionario.model.SynSet;

public class Main {

    public static void main(String[] args) {
        {
            String palvra = "maçã";
            Palavra p = Dicionario.busca(palvra);
            System.out.println(p.palavra);
            System.out.println(p.pronuncia);
            System.out.println("nominais");
            for (Substantivo s : p.nominais) {
                System.out.println(s.significado);
                for (Substantivo rel : s.hiperonimos)
                    System.out.println(rel.id);
            }

            System.out.println("verbais");
            for (SynSet s : p.verbais)
                System.out.println(s.significado);
            System.out.println("adverbiais");
            for (SynSet s : p.adverbiais)
                System.out.println(s.significado);
        }
    }
}
