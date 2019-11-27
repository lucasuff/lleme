/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tcc00328.trabalhos.s20192.Pedro_Bazilio_Victor_Brandão_Felipe_Esser.Lobisomem.participantes;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author MarcosFrederico
 */
public class Usuario extends Participante {

    @Override
    public Participante getVoto() throws IndexOutOfBoundsException {
        instrucaoParaVotacao();
        int PosicaoAlvo = EntradaDaPosicao();
        Participante alvo = getMesa().getParticipantes().get(PosicaoAlvo);
        return alvo;
    }

    private void instrucaoParaVotacao() {
        System.out.println("Escolha o jogador em quem você votará, o jogador com mais votos é morto pela aldeia");
        int quantParticipantes = getMesa().getParticipantes().size();
        int i;
        for (i = 1; i < quantParticipantes; i++)//Uma vez que o jogador é sempre o primeiro participante
            System.out.println((i) + "- " + getMesa().getParticipantes().get(i).getNome() + ".");
    }

    private int EntradaDaPosicao() {
        int alvo = 0;
        boolean entradaIncorreta = true;
        while (entradaIncorreta)
            try {
                Scanner teclado = new Scanner(System.in);
                alvo = teclado.nextInt();
                entradaIncorreta = false;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida, entre com um número inteiro");
            }
        return alvo;
    }

    private void escolhe(Participante alvo) {
        String statusAnterior = getMesa().getStatus(alvo);
        String statusNovo;
        if ("".equals(statusAnterior))
            statusAnterior = "0";
        int valorAnterior = Integer.parseInt(statusAnterior);
        valorAnterior += 1;
        statusNovo = Integer.toString(valorAnterior);
        getMesa().resetaStatus(alvo);
        getMesa().adicionaStatus(alvo, statusNovo);
    }

    @Override
    public void acusar(Participante alvo) {
        for (InteligenciaArtificial ia : getMesa().getIA())
            ia.ouveAcusacao(alvo, this);
    }

    @Override
    public void apoiar(Participante alvo) {
        for (InteligenciaArtificial ia : getMesa().getIA())
            ia.ouveApoiar(alvo, this);
    }

    @Override
    public void concordar(Participante alvo, String sobre) {
        for (InteligenciaArtificial ia : getMesa().getIA())
            ia.ouveConcordar(alvo, this, sobre);
    }

    @Override
    public void discordar(Participante alvo, String sobre) {
        for (InteligenciaArtificial ia : getMesa().getIA())
            ia.ouveDiscordar(alvo, this, sobre);
    }

    @Override
    public boolean falarNada() {
        return false;
    }
}
