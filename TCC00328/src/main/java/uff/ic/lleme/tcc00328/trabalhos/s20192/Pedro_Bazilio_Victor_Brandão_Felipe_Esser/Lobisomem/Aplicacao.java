/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tcc00328.trabalhos.s20192.Pedro_Bazilio_Victor_Brandão_Felipe_Esser.Lobisomem;

import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Pedro_Bazilio_Victor_Brandão_Felipe_Esser.Lobisomem.participantes.InteligenciaArtificial;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Pedro_Bazilio_Victor_Brandão_Felipe_Esser.Lobisomem.participantes.Mesa;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Pedro_Bazilio_Victor_Brandão_Felipe_Esser.Lobisomem.participantes.Participante;

/**
 *
 * @author MarcosFrederico
 */
public class Aplicacao {

    public static void main(String[] args) {
        menu();
        Mesa mesa = configurar();
        Run(mesa);
        //testRun();
    }

    public static void menu() {
        System.out.println("Bem-vindo ao jogo Lobisomem!");
        System.out.println("Aperte: \n1-Iniciar \n2-Sair");
        Scanner entrada = new Scanner(System.in);
        int escolha = entrada.nextInt();
        switch (escolha) {
            case 1:
                break;
            case 2:
                exit(0);
                break;
        }
    }

    public static Mesa configurar() {
        System.out.println("Você quer: \n1-Jogar contra uma quantidade aleatória de IA's \n2-Definir a quantidade de IA's no jogo");
        Scanner entrada = new Scanner(System.in);
        int escolha = entrada.nextInt();
        int quantidade = 0;
        Random gerador = new Random();
        Mesa mesa = null;
        if (escolha == 1) {
            quantidade = 4 + mod(gerador.nextInt() % 9);
            mesa = new Mesa(quantidade);
        } else if (escolha == 2) {
            System.out.println("Digite contra quantas IA's você quer jogar.\n O recomendado é um valor entre 4 e 12");
            quantidade = entrada.nextInt();
            mesa = new Mesa(quantidade);
        }
        System.out.println("Os jogadores são : " + geradorDeNomes(mesa, quantidade) + "e você.");
        System.out.println("Insira seu nome:");
        mesa.getParticipantes().get(0).setNome(entrada.next());

        return mesa;
    }

    public static int mod(int x) {
        if (x < 0) return -x;
        return x;
    }

    public static String geradorDeNomes(Mesa mesa, int quantidade) {
        Random gerador = new Random();
        List<String> nomes = new ArrayList<>();
        nomes.add("Marcos");
        nomes.add("David");
        nomes.add("Henrique");
        nomes.add("Yan");
        nomes.add("Gabriel");
        nomes.add("Lucas");
        nomes.add("Bianca");
        nomes.add("Karine");
        nomes.add("Mariana");
        nomes.add("Johann");
        nomes.add("Sophia");
        nomes.add("Rosaria");
        int i;
        if (quantidade <= 12) {
            int quantasRemoções = 12 - quantidade;
            for (i = 0; i < quantasRemoções; i++) {
                int escolha = mod(gerador.nextInt() % nomes.size());
                nomes.remove(escolha);
            }
        }
        String resultado = "";
        for (i = 0; i < quantidade; i++) {
            int aleatorio = mod(gerador.nextInt() % nomes.size());
            mesa.getParticipantes().get(i).setNome(nomes.get(aleatorio));
            resultado = resultado + nomes.get(aleatorio) + ", ";
            nomes.remove(aleatorio);
        }
        return resultado;
    }

    public static void Run(Mesa mesa) {
        boolean jogoRodando = true;
        while (jogoRodando) {
            Narrador narrador = mesa.getNarrador();
            narrador.rodadaNoturna();
            for (int i = 0; i < 7; i++)
                narrador.rodadaDeFalas();
            narrador.votacao();
            narrador.terminouJogo();
        }
    }

    public static void testRun() { //Exemplo de como a mesa está funcionando
        Mesa mesa = new Mesa(4);
        List<Participante> participantes = mesa.getParticipantes();
        for (Participante participante : mesa.getStatusMap().keySet())
            System.out.println("O participante eh:   " + participante + "e a chave eh:  " + mesa.getStatus(participante));
        Narrador narrador = mesa.getNarrador();
        participantes.get(0).setNome("Jogador");
        participantes.get(1).setNome("Fulano");
        participantes.get(2).setNome("Ciclano");
        participantes.get(3).setNome("Beltrano");
        int h, i, j;
        int quantParticipantes = mesa.getParticipantes().size();
        for (h = 0; h < 10; h++) {
            for (i = 1; i < quantParticipantes; i++) {
                InteligenciaArtificial ia = (InteligenciaArtificial) mesa.getParticipantes().get(i);
                System.out.println(mesa.getParticipantes().get(i).getNome() + "   " + ia.getComportamento());
                for (j = 0; j < quantParticipantes; j++)
                    System.out.println("    " + mesa.getParticipantes().get(j).getNome() + ": " + ia.getDesconfiancaDe(mesa.getParticipantes().get(j)));
            }
            narrador.rodadaDeFalas();
        }
    }
}
