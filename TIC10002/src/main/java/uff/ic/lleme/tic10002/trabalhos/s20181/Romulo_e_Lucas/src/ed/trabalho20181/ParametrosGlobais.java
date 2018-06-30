/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Romulo_e_Lucas.src.ed.trabalho20181;

public class ParametrosGlobais {

    Long espera2min = 120000L;
    Long espera1min = 60000L;
    Long espera3min = 180000L;

    HeapAssunto assuntosCliente1 = null;
    HeapAssunto assuntosCliente2 = null;
    HeapAssunto assuntosCliente3 = null;
    HeapAssunto assuntosCliente4 = null;

    // instanciando clientes
    Cliente c1 = new Cliente(1, "c1_idade21", 21, "060.434.432-02");
    Cliente c2 = new Cliente(2, "c2_idade89", 89, "070.542.742-02");
    Cliente c3 = new Cliente(3, "c3_idade30", 30, "090.642.942-01");
    Cliente c4 = new Cliente(4, "c4_idade52", 52, "080.534.342-01");

    // instanciando tipos de assuntos
    TipoAssunto t0 = new TipoAssunto(0, "tipo de assunto 0", 0);
    TipoAssunto t1 = new TipoAssunto(1, "tipo de assunto 1", 1);
    TipoAssunto t2 = new TipoAssunto(2, "tipo de assunto 2", 2);
    TipoAssunto t3 = new TipoAssunto(3, "tipo de assunto 3", 3);
    TipoAssunto t4 = new TipoAssunto(4, "tipo de assunto 4", 4);
    TipoAssunto t5 = new TipoAssunto(5, "tipo de assunto 5", 5);
    TipoAssunto t6 = new TipoAssunto(6, "tipo de assunto 6", 6);
    TipoAssunto t7 = new TipoAssunto(7, "tipo de assunto 7", 7);
    TipoAssunto t8 = new TipoAssunto(8, "tipo de assunto 8", 8);
    TipoAssunto t9 = new TipoAssunto(9, "tipo de assunto 9", 9);
    TipoAssunto t10 = new TipoAssunto(10, "tipo de assunto 10", 10);

    // instanciando assunto
    Assunto a1 = new Assunto(t0, "descrição do assunto 1");
    Assunto a2 = new Assunto(t3, "descrição do assunto 2");
    Assunto a3 = new Assunto(t7, "descrição do assunto 3");
    Assunto a4 = new Assunto(t2, "descrição do assunto 4");
    Assunto a5 = new Assunto(t1, "descrição do assunto 5");
    Assunto a6 = new Assunto(t5, "descrição do assunto 6");
    Assunto a7 = new Assunto(t6, "descrição do assunto 7");
}
