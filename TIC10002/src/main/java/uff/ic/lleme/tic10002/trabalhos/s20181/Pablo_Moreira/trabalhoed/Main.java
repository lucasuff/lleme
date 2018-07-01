/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Pablo_Moreira.trabalhoed;

/**
 *
 * @author pablomoreira
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServicoAtendimento servico = new ServicoAtendimento();

        servico.recepcionar();
        servico.recepcionar();
        servico.recepcionar();
        servico.recepcionar();

        servico.atender();
        servico.encerrar();
        servico.atender();
        servico.encerrar();
        servico.atender();
        servico.encerrar();
        servico.atender();
        servico.encerrar();

        servico.gerarEstatistica();

    }

}
