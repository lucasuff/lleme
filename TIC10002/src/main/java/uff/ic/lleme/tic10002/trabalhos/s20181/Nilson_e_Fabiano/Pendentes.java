package uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano;

import uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano.model.DateTime;
import uff.ic.lleme.tic10002.trabalhos.s20181.Nilson_e_Fabiano.model.Atendimento;

/**
 *
 * Estrutura de dados que armazena todos os atendimentos pendentes.
 *
 * A estrutura consiste num heap para que seja rápido localizar o atendimento
 * com maior prioridade para que seja efetivamente atendido (e removido do
 * conjunto de atendimentos pendentes - ou seja, do heap.
 *
 * Não existe problema em armazenar os atendimentos num heap porque a ordenação
 * relativa entre os elementos do heap não varia conforme o tempo. A razão disso
 * é que a função de prioridade é composta de um elemento fixo e um variável,
 * sendo que o elemento variável aumenta igualmente para todos no heap a medida
 * que o tempo passa.
 *
 * Quando um novo atendimento pendente deve ser inserido no heap, as comparações
 * entre as prioridades são realizadas baseadas no tempo de chegada do novo
 * elemento. Assim, como o tempo de espera de cada elemento do heap é computado
 * apenas NA HORA da comparação, toda a estrutura continua consistente.
 *
 * Apenas quando ocorre uma alteração da Urgência de algum tipo de Assunto é que
 * a ordem relativa entre os elementos do heap PODE ter ficado comprometida.
 * Nesse caso, o array com os elementos é ajustado, usando o método tradicional
 * de ajuste de heap com custo O(n)
 *
 * O tamanho do heap comeca com valores baixos e caso ocorra um estouro, o seu
 * tamanho é dobrado.
 *
 * @author Nilson e Fabiano
 */
public class Pendentes {

    Atendimento heap[];
    int n;
    boolean precisaAjuste;

    /**
     * Construtor
     *
     */
    public Pendentes() {
        this.heap = new Atendimento[20];
        this.n = 0;
        this.precisaAjuste = false;
    }

    /**
     *
     * Insere um atendimento no heap
     *
     * @param atendimento
     */
    public void insere(Atendimento atendimento) {

        // verifica se a estrutura do heap encheu
        if (n == heap.length) {
            // duplica o tamanho do heap
            Atendimento heap2[] = new Atendimento[n * 2];
            System.arraycopy(heap, 0, heap2, 0, n);
            heap = heap2;
        }

        // coloca o atendimento na ultima posicao do heap
        heap[n] = atendimento;
        n++;

        // verifica se houve algum evento que tornou o heap invalido
        if (precisaAjuste)
            // ajusta todo o heap
            ajustar(); // senão, precisa apenas ajustar a posicao deste atendimento no heap
        else
            // usa a hora de chegada deste atentimento como referencia de hora
            subir(n - 1, atendimento.getHoraChegada());

    }

    /**
     * Ajusta o array "heap" para que atenda as propriedades de um heap
     *
     */
    private void ajustar() {
        // varre todos os elementos do array recalculando o fator de prioridade
        for (int i = 0; i < n; i++)
            heap[i].computaFatorDePrioridade();

        // ajusta o heap com base na hora atual
        long tm = DateTime.getTime();
        for (int i = (n + 1) / 2 - 1; i > 0; i--)
            descer(i, tm);

        precisaAjuste = false;
    }

    /**
     * Extrai o atendimento com a maior prioridade
     *
     * Utiliza o valor do parametro horaAtendimento como base de referência para
     * ajusta do heap após a remoção.
     *
     * @param horaAtendimento
     * @return
     */
    public Atendimento pegaPrioritario(long horaAtendimento) {
        if (n == 0)
            return null;

        if (precisaAjuste)
            ajustar();

        Atendimento a = heap[0];

        n--;
        heap[0] = heap[n];

        descer(0, horaAtendimento);

        return a;
    }

    /**
     * Registra que a estrutura de dados precisa de ajuste interno antes de
     * fazer uma nova inserção ou remoção
     *
     */
    public void precisaAjustar() {
        precisaAjuste = true;
    }

    /**
     * Rotina padrão "subir" do heap adaptada para trabalhar com a prioridade
     * variavel com o tempo informado pelo parâmetro "tm"
     *
     * @param i
     * @param tm
     */
    private void subir(int i, long tm) {
        if (i == 0)
            return;
        int pai = (i + 1) / 2 - 1;
        if (heap[pai].prioridade(tm) < heap[i].prioridade(tm)) {
            Atendimento a = heap[pai];
            heap[pai] = heap[i];
            heap[i] = a;
            subir(pai, tm);
        }
    }

    /**
     * Rotina padrão "descer" do heap adaptada para trabalhar com a prioridade
     * variavel com o tempo informado pelo parâmetro "tm"
     *
     * @param i
     * @param tm
     */
    private void descer(int i, long tm) {
        int j = 2 * i + 1;  // j é o filho da esquerda
        if (j >= n)
            return; //nao tem filho? retorna
        // escolhe o maior dentre os filhos
        int k = j + 1;
        if (k < n && (heap[k].prioridade(tm) > heap[j].prioridade(tm)))
            j = k;

        // pai menor do que o maior filho?
        if (heap[i].prioridade(tm) < heap[j].prioridade(tm)) {
            Atendimento a = heap[i];  // troca o pai
            heap[i] = heap[j];        // com
            heap[j] = a;              // o maior filho
            descer(j, tm);             // ajusta o novo filho (antigo pai)
        }
    }

}
