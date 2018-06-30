package uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed;

import uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed.model.ObjetoBase;
import uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed.model.Estatistica;

/*
 * Classe EstatisticaDia é uma classe associada a classe Estatistica.
 * Foi criada pois a classe Estatistica já estende HashTable, e não poderia também
 * estender ObjetoBase para permitir o seu armazenamento também em uma HashTable.
 * */
public class EstatisticaDia extends ObjetoBase {

    private String data;
    private Estatistica estatistica;

    public EstatisticaDia(String data) {
        this.data = data;
        this.estatistica = new Estatistica(20);
    }

    public Estatistica getEstatistica() {

        return this.estatistica;

    }

    @Override
    public String getChave() {
        // TODO Auto-generated method stub
        return this.data;
    }

}
