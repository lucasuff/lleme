package trabalho;

import java.util.Comparator;

public class EstatisticaTipoAssuntoComparator implements Comparator<EstatisticaTipoAssunto> {

    public int compare(EstatisticaTipoAssunto a, EstatisticaTipoAssunto b) {
        if (a.idTipo > b.idTipo)
            return 1;
        else
            return -1;
    }
}
