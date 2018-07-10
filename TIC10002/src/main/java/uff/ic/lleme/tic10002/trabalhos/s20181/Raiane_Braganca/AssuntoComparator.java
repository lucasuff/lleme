package uff.ic.lleme.tic10002.trabalhos.s20181.Raiane_Braganca;

import uff.ic.lleme.tic10002.trabalhos.s20181.Raiane_Braganca.model.Assunto;
import java.util.Comparator;

public class AssuntoComparator implements Comparator<Assunto> {

    public int compare(Assunto a, Assunto b) {
        if (a.tipoAssunto.getUrgencia() > b.tipoAssunto.getUrgencia())
            return -1;
        else
            return 1;
    }
}
