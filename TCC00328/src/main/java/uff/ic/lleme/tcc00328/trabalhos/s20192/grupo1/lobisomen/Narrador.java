/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tcc00328.trabalhos.s20192.MarcosF_YanI_HenriqueY_DavidH.lobisomen;

import java.util.*;
import uff.ic.lleme.tcc00328.trabalhos.s20192.MarcosF_YanI_HenriqueY_DavidH.lobisomen.participantes.InteligenciaArtificial;
import uff.ic.lleme.tcc00328.trabalhos.s20192.MarcosF_YanI_HenriqueY_DavidH.lobisomen.participantes.Mesa;
import uff.ic.lleme.tcc00328.trabalhos.s20192.MarcosF_YanI_HenriqueY_DavidH.lobisomen.participantes.Participante;
import uff.ic.lleme.tcc00328.trabalhos.s20192.MarcosF_YanI_HenriqueY_DavidH.lobisomen.participantes.Usuario;

/**
 *
 * @author MarcosFrederico
 */
public class Narrador {

    private int ultimoAFalar;
    private String ultimaAcao;
    private Participante autorDaUltimaAcao;
    private Mesa mesa;

    public Narrador() {
        ultimoAFalar = 0;
        ultimaAcao = "";
    }

    public void setMesa(Mesa mesa) {
        if (this.mesa == null)
            this.mesa = mesa;
    }

    public void setUltimaAcao(String acao, Participante autor) {
        ultimaAcao = acao;
        autorDaUltimaAcao = autor;
    }

    public void votacao() {
        List<Participante> participantes = mesa.getParticipantes();
        Map<Participante, Integer> votos = new HashMap<>();
        for (Participante participante : participantes)
            votos.put(participante, 0);
        int tamanho = participantes.size();
        int i;
        //realiza a votacao
        for (i = 0; i < tamanho; i++) {
            Participante atual = participantes.get(i);
            Participante alvo = atual.getVoto();
            System.out.println(atual.getNome() + "votou em " + alvo.getNome());
            votos.put(alvo, votos.get(alvo) + 1);
        }
        //acha o desafortunado com mais votos
        Participante comMaisVotos = null;
        int MaiorVotos = 0;
        for (Participante participante : participantes)
            if (votos.get(participante) > MaiorVotos) {
                MaiorVotos = votos.get(participante);
                comMaisVotos = participante;
            }
        matarParticipante(comMaisVotos);
        System.out.println("A aldeia matou " + comMaisVotos.getNome() + ".");
    }

    public void matarParticipante(Participante alvo) {
        List<Participante> participantes = mesa.getParticipantes();
        //checa se é um Caçador, e se for, mata o alvo primeiro
        if ("Caçador".equals(alvo.getPapel().getId())) {
            String[] status;
            for (Participante participante : participantes) {
                status = mesa.getStatus(participante).split(" ");
                for (int i = 0; i < status.length; i++) {
                    String statusProcurado = "marcado:" + alvo.getNome();
                    if (statusProcurado.equals(status[i]))
                        matarParticipante(participantes.get(i));
                }
            }
        }
        //remove jogador
        participantes.remove(alvo);
        Map<Participante, String> status = mesa.getStatusMap();
        status.remove(alvo);//Map é uma estrutura passada por referência, então vai remover do Map original, terrível
    }

    public void rodadaNoturna() {
        System.out.println("Anoiteceu, cada jogador vê e realiza suas funções.");
        mesa.getParticipantes().get(0).getPapel().acaoNoturnaPlayer();
        for (int i = 1; i < mesa.getParticipantes().size(); i++)
            mesa.getParticipantes().get(i).getPapel().acaoNoturnaIA();
        operaStatus();
        System.out.println("Amanheceu!");
    }

    public void operaStatus() {
        int MaiorQuantiaDeVotos = 0;
        Participante participanteComMaisVotos = null;
        String[] status;
        List<Participante> participantes = mesa.getParticipantes();
        for (Participante participante : participantes) {
            status = mesa.getStatus(participante).split(" ");
            int quantiaDeVotos = 0;
            for (int i = 0; i < status.length; i++)
                if ("escolhido".equals(status[i]))
                    quantiaDeVotos++;
            if (quantiaDeVotos > MaiorQuantiaDeVotos) {
                participanteComMaisVotos = participante;
                MaiorQuantiaDeVotos = quantiaDeVotos;
            }
        }
        if (!"protegido".equals(participanteComMaisVotos)) {
            matarParticipante(participanteComMaisVotos);
            System.out.println(participanteComMaisVotos.getNome() + "morreu durante a noite.");
        }
    }

    public void rodadaDeFalas() {
        acaoDoJogador((Usuario) mesa.getParticipantes().get(0));
        procurarAcao(mesa.getIA());
    }

    public void procurarAcao(List<InteligenciaArtificial> ias) {
        int quantosTiveramOportunidade = 0;
        int tamanho = ias.size();
        int i = ultimoAFalar;//Ter uma distribuição igual de ias falando
        boolean falouAlgo = false;
        while ((quantosTiveramOportunidade < tamanho) && (!falouAlgo)) {
            falouAlgo = ias.get(i).tomadaDeAcao(false);
            i++;
            quantosTiveramOportunidade++;
            if (i == tamanho)
                i = 0;
        }
        ultimoAFalar = i;
        if (!falouAlgo) {//Se todos falaram nada, uma ia toma uma decisão forçada
            Random gerador = new Random();
            int aleatorio = mod(gerador.nextInt() % tamanho);
            ias.get(aleatorio).tomadaDeAcao(true);
        }
    }

    private int mod(int i) {//módulo
        if (i < 0)
            return -i;
        return i;
    }

    public void acaoDoJogador(Usuario jogador) {
        boolean entradaInvalida = true;
        int opcaoEscolhida = 1;
        Scanner entrada = new Scanner(System.in);
        while (entradaInvalida)
            try {
            System.out.println("Escolha o que fazer:");
            System.out.println("1- Acusar alguém");
            System.out.println("2- Apoiar alguém");
            System.out.println("3- Falar Nada");
            int ultimaOpcao = 3;
            if (!"".equals(ultimaAcao)) {
                System.out.println("4- Concordar com " + autorDaUltimaAcao.getNome());
                System.out.println("5- Discordar de " + autorDaUltimaAcao.getNome());
                ultimaOpcao = 5;
            }
            opcaoEscolhida = entrada.nextInt();
            if ((opcaoEscolhida <= ultimaOpcao) && (opcaoEscolhida > 0))
                entradaInvalida = false;
            else
                System.out.println("Por favor, entrar com um número entre 0 e " + (ultimaOpcao + 1) + ".");
        } catch (InputMismatchException e) {
            System.out.println("Por favor, entrar com um valor inteiro.");
        }
        entradaInvalida = true;
        List<InteligenciaArtificial> ias = jogador.getMesa().getIA();
        while (entradaInvalida) {
            int i, escolha;
            try {
                switch (opcaoEscolhida) {
                    case 1:
                        System.out.println("Escolha quem acusar: ");
                        for (i = 0; i < ias.size(); i++)
                            System.out.println((i + 1) + "- " + ias.get(i).getNome());
                        escolha = entrada.nextInt() - 1;
                        if (escolha < ias.size()) {
                            System.out.println("Você acusou " + ias.get(escolha).getNome() + ".");
                            jogador.acusar(ias.get(escolha));
                            entradaInvalida = false;
                        } else
                            System.out.println("Escolha um valor entre 0 e " + ias.size());
                        break;
                    case 2:
                        System.out.println("Escolha quem apoiar: ");
                        for (i = 0; i < ias.size(); i++)
                            System.out.println((i + 1) + "- " + ias.get(i).getNome());
                        escolha = entrada.nextInt() - 1;
                        if (escolha < ias.size()) {
                            System.out.println("Você apoiou " + ias.get(escolha).getNome() + ".");
                            jogador.apoiar(ias.get(escolha));
                            entradaInvalida = false;
                        } else
                            System.out.println("Escolha um valor entre 0 e " + ias.size());
                        break;
                    case 3:
                        jogador.falarNada();
                        entradaInvalida = false;
                        break;
                    case 4:
                        System.out.println("Você concordou com " + autorDaUltimaAcao.getNome() + ".");
                        jogador.concordar(autorDaUltimaAcao, ultimaAcao);
                        entradaInvalida = false;
                        break;
                    case 5:
                        System.out.println("Você discordou de " + autorDaUltimaAcao.getNome() + ".");
                        jogador.discordar(autorDaUltimaAcao, ultimaAcao);
                        entradaInvalida = false;
                        break;
                }
            } catch (InputMismatchException e1) {
                System.out.println("Por favor, entre com um numero inteiro.");
            }
        }
    }

    public boolean terminouJogo() {
        int numAldeia = 0;
        int numEvil = 0;
        int numUser = 0;
        int i;
        List<Participante> participantes = mesa.getParticipantes();
        List<Participante> mesaInicial = mesa.getMesaInicial();

        for (i = 0; i < participantes.size(); i++)
            if (participantes.get(i).getPapel().isAldeia() == true)
                numAldeia++;
            else if (participantes.get(i) instanceof Usuario)
                numUser++;
            else
                numEvil++;

        if (numEvil > numAldeia) {
            System.out.println("Os aldeões foram dizimados, os lobisomens venceram!!!");
            for (i = 0; i < mesaInicial.size(); i++)
                System.out.println(mesaInicial.get(i).getNome() + " possuia o papel: " + mesaInicial.get(i).getPapel());
            return true;
        } else if (numEvil == 0) {
            System.out.println("Acabou o perigo, a Aldeia venceu!!!");
            for (i = 0; i < mesaInicial.size(); i++)
                System.out.println(mesaInicial.get(i).getNome() + " possuia o papel: " + mesaInicial.get(i).getPapel());
            return true;
        } else
            return false;
    }
}
