package uff.ic.lleme.tic10002.trabalhos.s20181.Raiane_Braganca;

import java.util.Comparator;

public class EstatisticaTipoAssuntoComparator implements Comparator<EstatisticaTipoAssunto> {

    public int compare(EstatisticaTipoAssunto a, EstatisticaTipoAssunto b) {
        if (a.idTipo > b.idTipo)
            return 1;
        else
            return -1;
    }
}
