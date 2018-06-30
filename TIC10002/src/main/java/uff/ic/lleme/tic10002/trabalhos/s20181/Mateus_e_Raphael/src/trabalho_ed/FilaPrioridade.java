package trabalho_ed;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 *
 * @author raphael
 */
public class FilaPrioridade {

    Random r = new Random();
    HeapDinamico fila = new HeapDinamico(100);
    int qtd_emAtendimento = 0;
    int lotacao = 5;
    Atendimento atendimentoNovo = null;
    Atendimento[] emAtendimento = new Atendimento[lotacao];
    PilhaEncerrados pilha = new PilhaEncerrados();
    HashTipoAssunto tipos = new HashTipoAssunto(10);

    private Cliente[] geraClientes() {
        Cliente[] clientes = new Cliente[11];

        clientes[0] = new Cliente(0, 17, "772.488.350-56", "Joao Cardoso Gomes");
        clientes[1] = new Cliente(1, 70, "316.755.170-45", "Paulo Gomes Santos");
        clientes[2] = new Cliente(2, 37, "727.363.610-08", "Isabela Sousa Oliveira");
        clientes[3] = new Cliente(3, 55, "927.627.612-20", "Rafaela Almeida Melo");
        clientes[4] = new Cliente(4, 79, "613.348.102-14", "Vitória Martins Castro");
        clientes[5] = new Cliente(5, 31, "179.135.948-53", "Laura Castro Correia");
        clientes[6] = new Cliente(6, 51, "805.827.634-01", "Rafael Sousa Barros");
        clientes[7] = new Cliente(7, 46, "688.121.437-50", "Felipe Goncalves Pereira");
        clientes[8] = new Cliente(8, 64, "844.730.719-01", "Kai Fernandes Araujo");
        clientes[9] = new Cliente(9, 72, "413.617.948-01", "Sofia Correia Almeida");
        clientes[10] = new Cliente(10, 72, "413.617.948-01", "Sara Correia Almeida");

        return clientes;
    }

    private TipoAssunto[] geraTipos() {
        TipoAssunto[] tiposAux = new TipoAssunto[20];

        tiposAux[0] = new TipoAssunto(0, ("OFTALMOLOGIA"), ("OFTALMOLOGIA"), 1);
        tiposAux[1] = new TipoAssunto(1, ("CIRURGIA VASCULAR"), ("CIRURGIA VASCULAR"), 2);
        tiposAux[2] = new TipoAssunto(2, ("PSICOLOGIA"), ("PSICOLOGIA"), 3);
        tiposAux[3] = new TipoAssunto(3, ("FISIOTERAPIA"), ("FISIOTERAPIA"), 3);
        tiposAux[4] = new TipoAssunto(4, ("FONOAUDIOLOGIA"), ("FONOAUDIOLOGIA"), 4);
        tiposAux[5] = new TipoAssunto(5, ("CLÍNICA MÉDICA"), ("CLÍNICA MÉDICA"), 5);
        tiposAux[6] = new TipoAssunto(6, ("PSIQUIATRIA"), ("PSIQUIATRIA"), 6);
        tiposAux[7] = new TipoAssunto(7, ("DERMATOLOGIA"), ("DERMATOLOGIA"), 7);
        tiposAux[8] = new TipoAssunto(8, ("NUTRICIONISTA"), ("NUTRICIONISTA"), 8);
        tiposAux[9] = new TipoAssunto(9, ("REUMATOLOGIA"), ("REUMATOLOGIA"), 9);
        tiposAux[10] = new TipoAssunto(10, ("CIRURGIA GERAL"), ("CIRURGIA GERAL"), 10);
        tiposAux[11] = new TipoAssunto(11, ("OTORRINOLARINGOLOGIA"), ("OTORRINOLARINGOLOGIA"), 6);
        tiposAux[12] = new TipoAssunto(12, ("ENDOCRINOLOGIA"), ("ENDOCRINOLOGIA"), 5);
        tiposAux[13] = new TipoAssunto(13, ("PEDIATRIA"), ("PEDIATRIA"), 2);
        tiposAux[14] = new TipoAssunto(14, ("GINECOLOGIA"), ("GINECOLOGIA"), 3);
        tiposAux[15] = new TipoAssunto(15, ("UROLOGIA"), ("UROLOGIA"), 2);
        tiposAux[16] = new TipoAssunto(16, ("GERIATRIA"), ("GERIATRIA"), 1);
        tiposAux[17] = new TipoAssunto(17, ("CARDIOLOGIA"), ("CARDIOLOGIA"), 5);
        tiposAux[18] = new TipoAssunto(18, ("NEUROLOGIA"), ("NEUROLOGIA"), 6);
        tiposAux[19] = new TipoAssunto(19, ("ORTOPEDIA"), ("ORTOPEDIA"), 8);

        return tiposAux;
    }

    public void atendimento() { // teste

        Cliente[] clientes = geraClientes();
        TipoAssunto[] tiposAux = geraTipos();

        for (int i = 0; i < 20; i++)
            tipos.inserir(tiposAux[i]);

        ListaAssuntos[] lista = new ListaAssuntos[11];
        Random r = new Random();
        for (int i = 0; i < 11; i++) {
            lista[i] = new ListaAssuntos();
            for (int j = 0; j < 10; j++)
                if (r.nextFloat() <= 0.5) {
                    int number = r.nextInt(20);
                    lista[i].incluir(new Assunto(tiposAux[number], "Descrição.." + 1));
                }
        }

        for (int i = 0; i < 10; i++)
            recepcionar(clientes[i], lista[i], i);

        System.out.println("Início da fila");
        fila.imprimir();
        System.out.println('\n');

        //Chegada de novo paciente
        recepcionar(clientes[10], lista[10], 2);

        System.out.println("Situação atual da fila");
        fila.imprimir();
        System.out.println('\n');

        System.out.println("Início dos atendimentos por prioridade:");
        atender();
        System.out.println('\n');

        System.out.println("Situação atual da fila após recálculo de prioridade do primeiro atendimento");
        fila.imprimir();
        System.out.println('\n');

        atender();
        atender();
        atender();
        atender();

        System.out.println("Situação atual da fila após os atendimentos");
        fila.imprimir();
        System.out.println('\n');

        System.out.println("Pacientes sendo atendidos");
        this.imprimirEmAndamento();
        System.out.println('\n');

        System.out.println("Consultas sendo encerradas...");
        encerrar(0);
        encerrar(1);
        encerrar(2);
        encerrar(3);
        encerrar(4);
        System.out.println('\n');

        System.out.println("Estatísticas da média de atendimento por assunto do dia sendo geradas..");
        this.gerarEstatistica();
        System.out.println('\n');

        System.out.println("Ínicio das re-consultas do segundo dia...");
        for (int i = 0; i < 10; i++)
            recepcionar(clientes[i], lista[i], i + 1500);
        System.out.println('\n');

        System.out.println("Situação atual da fila");
        fila.imprimir();
        System.out.println('\n');

        System.out.println("Início dos atendimentos por prioridade:");
        atender();
        atender();
        atender();
        atender();
        atender();
        System.out.println('\n');

        System.out.println("Situação atual da fila");
        fila.imprimir();
        System.out.println('\n');

        System.out.println("Pacientes sendo atendidos");
        this.imprimirEmAndamento();
        System.out.println('\n');

        System.out.println("Consultas sendo encerradas...");
        encerrar(0);
        encerrar(1);
        encerrar(2);
        encerrar(3);
        encerrar(4);
        System.out.println('\n');

        System.out.println("Situação atual da fila");
        fila.imprimir();
        System.out.println('\n');

        System.out.println("Estatísticas da média de atendimento por assunto do dia sendo geradas..");
        this.gerarEstatistica();
        System.out.println('\n');

    }

    private void recepcionar(Cliente cliente, ListaAssuntos l) { // O(log n)
        //recebe os dados dos clientes e suas demandas e os coloca em espera de atendimento
        Atendimento atend = new Atendimento(cliente, l, Instant.now());
        fila.incluir(atend); // O(log n)
    }

    private void recepcionar(Cliente cliente, ListaAssuntos l, int minus) { // O(log n)
        //recebe os dados dos clientes e suas demandas e os coloca em espera de atendimento
        Atendimento atend = new Atendimento(cliente, l, Instant.now().minus(minus, ChronoUnit.MINUTES));
        fila.incluir(atend); // O(log n)
    }

    private void atender() { // O(n) = n é a quantidade de elementos no heap
        if (qtd_emAtendimento < lotacao) {
            atendimentoNovo = null;
            for (int i = 0; i < lotacao && atendimentoNovo == null; i++) // O(lotacao) = O(1)
                if (emAtendimento[i] == null) {
                    emAtendimento[i] = fila.removerPrioritario(); // O(n) = n é a quantidade de elementos no heap
                    atendimentoNovo = emAtendimento[i];
                    qtd_emAtendimento++;
                }
        }
    }

    private void atender(Instant agora) { // O(n) = n é a quantidade de elementos no heap
        if (qtd_emAtendimento < lotacao) {
            atendimentoNovo = null;
            for (int i = 0; i < lotacao && atendimentoNovo == null; i++) // O(lotacao) = O(1)
                if (emAtendimento[i] == null) {
                    emAtendimento[i] = fila.removerPrioritario(agora); // O(n) = n é a quantidade de elementos no heap
                    atendimentoNovo = emAtendimento[i];
                    qtd_emAtendimento++;
                }
        }
    }

    private void encerrarAssuntos(ListaAssuntos assuntos) { // O(n) = n é quantidade de Assuntos
        String[] providencia = new String[assuntos.getTamanhoLista()];
        int[] duracao = new int[assuntos.getTamanhoLista()];
        for (int i = 0; i < assuntos.getTamanhoLista(); i++) {   // O(n) = n é quantidade de Assuntos
            //System.out.print("Digite a providencia: ");
            providencia[i] = "Terminou com exito";
            //System.out.print("Digite a duração (em min): ");
            duracao[i] = r.nextInt(100);
        }
        assuntos.finalizarAssuntos(providencia, duracao); // O(n) = n é quantidade de Assuntos
    }

    private void encerrar(int i) {  // O(n) = n é quantidade de Assuntos
        if (i < this.lotacao && this.emAtendimento[i] != null) {
            encerrarAssuntos(this.emAtendimento[i].getAssuntos()); // O(n) = n é quantidade de Assuntos
            this.pilha.incluir(this.emAtendimento[i]);             // O(1)
            this.emAtendimento[i] = null;
            qtd_emAtendimento--;
        }
    }

    private void imprimirEmAndamento() {
        for (int i = 0; i < this.lotacao; i++)
            if (this.emAtendimento[i] != null)
                System.out.println(this.emAtendimento[i].getCliente().getNome());
    }

    private TipoAssunto alterarUrgencia(TipoAssunto ta, int urgencia) {
        ta = this.tipos.alterarUrgencia(ta, urgencia);
        for (int i = 0; i < fila.getN(); i++)
            fila.recalcularPrioridade(true);
        return ta;
    }

    private void gerarEstatistica() {//(Instant inicio, Instant fim){
        Instant agora = Instant.now().truncatedTo(ChronoUnit.DAYS);
        gerarEstatistica(agora);
    }

    private void gerarEstatistica(Instant dia) {
        /*fornece, quando solicitado, o tempo médio de atendimento por tipo de assunto no dia corrente,
        permitindo calibrar o critério de prioridade: assuntos mais demorados, em média, podem ter
        sua prioridade aumentada.*/

        System.out.println("Estatística");
        HashEstatistica he = new HashEstatistica(tipos.getQuantiadadeElementos());

        PilhaEncerrados.No aux = pilha.getPrimeiro();
        //inicio = inicio.truncatedTo(ChronoUnit.DAYS);
        //fim = fim.truncatedTo(ChronoUnit.DAYS);
        while (aux != null && dia.compareTo(aux.getAtendimento().getHoraChegada().truncatedTo(ChronoUnit.DAYS)) == 0) {
            ListaAssuntos assuntos = aux.getAtendimento().getAssuntos();
            ListaAssuntos.No assuntoIterator = assuntos.getPrimeiro();
            for (int i = 0; i < assuntos.getTamanhoLista(); i++) {
                //System.out.println("Asssunto: "+assuntoIterator.getAssunto().getTipo().getId());
                he.atualizar(assuntoIterator.getAssunto());
                assuntoIterator = assuntoIterator.getProximo();
            }
            aux = aux.getProximo();
        }

        he.imprimir();

    }

}
