package uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed;

/* A classe Atendimentos representa a fila de Atendimentos enfileirados por prioridade.
 * Herda de MaxHeap, mas tem uma implementa��o espec�fica do m�todo remover, que solicita reorganiza��o do heap antes da remo��o de um item.
 * Esta reorganiza��o � necess�ria p/ que eventuais mudan�as de prioridade em fun��o do tempo possam ser contabilizadas.
 * */
public class Atendimentos {

    TiposAssuntos tiposAssuntos;
    Estatisticas estatisticas;
    MaxHeap fila;

    public Atendimentos(int maxsize, Estatisticas estatisticas) {
        
        this.fila = new MaxHeap(maxsize);
        this.estatisticas = estatisticas;
        this.tiposAssuntos = new TiposAssuntos(System.getProperty("user.dir") + "\\src\\main\\java\\uff\\ic\\lleme\\tic10002\\trabalhos\\s20181\\Bruna_Cleomar_Patrick\\data\\TipoAssunto.txt");
        // TODO Auto-generated constructor stub
    }

    public TiposAssuntos getTiposAssuntos() {
        return this.tiposAssuntos;
    }

    public void Recepcionar(Atendimento atendimento) {
        atendimento.setTimestampChegada();
        System.out.println("Cliente tem prioridade " + atendimento.getPrioridade());

        try {
            this.fila.inserir(atendimento);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Atendimento Atender() {
        //Remove o pr�ximo item do heap
        Atendimento atendimento = this.fila.remover();
        atendimento.setTimestampAtendimento();
        System.out.println("Próximo cliente-> Nome: " + atendimento.getCliente().getNome() + ", idade: " + atendimento.getCliente().getIdade());
        return atendimento;
    }

    public void Encerrar(Atendimento atendimento) {
        System.out.println("");
        System.out.println("Encerrando atendimento para cliente nome:'" + atendimento.getCliente().getNome()
                + "', idade: " + atendimento.getCliente().getIdade() + ", prioridade: " + atendimento.getPrioridade());

        //Busca o objeto Estat�stica do dia
        EstatisticaDia estatDia = this.estatisticas.Buscar(atendimento.getTimestampChegada());

        for (int i = 0; i < atendimento.getAssuntosTratados().getTamanho(); i++) {
            Assunto a = (Assunto) atendimento.getAssuntosTratados().get(i);
            System.out.println("Descrição Assunto " + i + ": " + a.getDescricao() + ", Providência:" + a.getProvidencias());

            //atualiza as estat�sticas
            estatDia.getEstatistica().AtualizarMetrica(atendimento.getTimestampChegada(), a.getChave(), a.getDuracaoAtendimento());
        }
        System.out.println("");

        //Define timestamp de encerramento
        atendimento.setTimestampEncerramento();
    }
}
