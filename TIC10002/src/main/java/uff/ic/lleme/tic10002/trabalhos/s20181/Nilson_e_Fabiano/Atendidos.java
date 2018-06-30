package uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano;

/**
 * Class auxliar para Lista de Atendimentos Atendidos
 *
 * @author Nilson e Fabiano
 */
class NoAtendido {

    protected Atendimento atendimento;
    protected NoAtendido prox;
    protected NoAtendido ante;

    NoAtendido(Atendimento a) {
        this.atendimento = a;
    }

}

/**
 * Implementa uma lista encadeada de atendimentos realizados
 *
 * @author Nilson e Fabiano
 */
public class Atendidos {

    NoAtendido primeiro;
    NoAtendido ultimo;
    int qtd;
    long tm;

    /**
     * Insere um atendimento "a" na lista na ultima posicao
     */
    public void insere(Atendimento a) {
        NoAtendido no = new NoAtendido(a);
        if (primeiro == null)
            primeiro = no;
        else
            ultimo.prox = no;
        // no.ante = ultimo;
        ultimo = no;
        qtd++;
    }

    /**
     * Varre todos os atendimentos da lista, considerando apenas os que
     * iniciaram no mesmo determinado por "data". Para cada atendimento
     * encontrado varre todos os assuntos, registrando, na célula estatistica
     * correspondente ao tipo de assunto, o tempo necessário para resolver o
     * assunto.
     */
    public void computaEstatisticas(Estatisticas es, long data) {

        // varre todos os atendimentos
        for (NoAtendido no = ultimo; no != null; no = no.ante) {

            Atendimento atendimento = no.atendimento;

            // verifica se a data
            int cmp = atendimento.comparaData(data);

            if (cmp > 0)
                continue; // Pega o proximo se a data ao atendimento é maior
            if (cmp < 0)
                return;  // Se encontrou uma data anterior, terminou

            // o atendimento ocorreu no dia correto
            // Pega todos os assuntos do atendimento
            Assuntos as = atendimento.getAssuntos();

            // Para cada assunto do atendimento
            for (Assunto a = as.getPrimeiro();
                    a != null;
                    a = as.getProximo()) {

                // localiza a estatistica referente ao tipo de assunto
                Estatistica e = es.localizaEstatistica(a.getTipo().getTipo());

                // contabiliza o tempo necessario para resolver o assunto
                if (e != null)
                    e.contabilizaEvento(a.getDuracao());
                else
                    System.out.println("falha\n");
            }
        }
    }
}
