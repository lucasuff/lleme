package trabalho;

import java.util.Comparator;

public class AssuntoComparator implements Comparator<Assunto> {

    public int compare(Assunto a, Assunto b) {
        if (a.tipoAssunto.getUrgencia() > b.tipoAssunto.getUrgencia())
            return -1;
        else
            return 1;
    }
}
