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
public class CriadorDataset {

    public static Cliente[] criarClientes(int total) {
        Cliente gerados[] = new Cliente[total];

        for (int i = 0; i < gerados.length; i++) {
            String cpf = Integer.toString(10000 + (int) (Math.random() * 99999));
            String nome = "Cliente" + Integer.toString(i + 1);
            int idade = 18 + (int) (Math.random() * 90);
            Cliente novo = new Cliente(cpf, nome, idade);
            gerados[i] = novo;
        }

        return gerados;
    }

    static TipoAssunto[] criarTiposDeAssunto(int NUM_DE_TIPOS_DE_ASSUNTO) {
        TipoAssunto tipoAssunto[] = new TipoAssunto[NUM_DE_TIPOS_DE_ASSUNTO];
        for (int i = 0; i < NUM_DE_TIPOS_DE_ASSUNTO; i++) {
            String titulo = "assunto" + Integer.toString(i);
            tipoAssunto[i] = new TipoAssunto(i, titulo, i);
        }

        return tipoAssunto;
    }

    static Atendimento gerarAtendimento(Cliente[] clientesDB, TipoAssunto[] tabelaTiposDeAssunto) {
        int iDcliente = (int) (Math.random() * clientesDB.length);

        int numeroDeAssuntos = (int) (Math.random() * Atendimento.MAX_ASSUNTOS);

        Assunto assuntos[] = new Assunto[numeroDeAssuntos];
        for (int i = 0; i < numeroDeAssuntos; i++) {
            int tipoAssunto = (int) (Math.random() * tabelaTiposDeAssunto.length);
            Assunto novo = new Assunto(tipoAssunto, "assunto teste", 0);
            assuntos[i] = novo;

        }

        return new Atendimento(clientesDB[iDcliente], assuntos);
    }

}
