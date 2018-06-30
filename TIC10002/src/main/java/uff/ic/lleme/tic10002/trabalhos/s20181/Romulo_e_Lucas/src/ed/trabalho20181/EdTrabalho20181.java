package uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.src.ed.trabalho20181;

public class EdTrabalho20181 {

    public static void main(String[] args) throws InterruptedException {
        ParametrosGlobais param = new ParametrosGlobais();
        // criar lista de assuntos
        param.assuntosCliente1 = new HeapAssunto();
        param.assuntosCliente1.incluir(param.a1);
        param.assuntosCliente1.incluir(param.a2);
        param.assuntosCliente1.incluir(param.a5);
        param.assuntosCliente1.incluir(param.a6);

        param.assuntosCliente2 = new HeapAssunto();
        param.assuntosCliente2.incluir(param.a2);
        param.assuntosCliente2.incluir(param.a7);

        param.assuntosCliente3 = new HeapAssunto();
        param.assuntosCliente3.incluir(param.a3);
        param.assuntosCliente3.incluir(param.a1);
        param.assuntosCliente3.incluir(param.a6);

        param.assuntosCliente4 = new HeapAssunto();
        param.assuntosCliente4.incluir(param.a4);

        Servico servico = new Servico();

        // recepcionar clientes
        servico.recepcionar(param.c1, param.assuntosCliente1);
        Thread.sleep(param.espera2min);
        servico.recepcionar(param.c2, param.assuntosCliente2);
        Thread.sleep(param.espera2min);
        servico.recepcionar(param.c3, param.assuntosCliente3);

        Atendimento atendido = null;

        // atendendo cliente com maior prioridade
        atendido = servico.atender();

        // recepcionando novo cliente "de repente"
        servico.recepcionar(param.c4, param.assuntosCliente4);

        // encerrar cliente que estava sendo atendido
        servico.encerrar(atendido);

        // gerar estatisticas
        servico.gerarEstatistica();

        // novo cliente atendido
        atendido = servico.atender();
    }

}
