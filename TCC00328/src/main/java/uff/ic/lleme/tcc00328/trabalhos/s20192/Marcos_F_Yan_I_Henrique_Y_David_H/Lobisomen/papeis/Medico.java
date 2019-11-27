/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tcc00328.trabalhos.s20192.Marcos_F_Yan_I_Henrique_Y_David_H.Lobisomen.papeis;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Marcos_F_Yan_I_Henrique_Y_David_H.Lobisomen.participantes.InteligenciaArtificial;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Marcos_F_Yan_I_Henrique_Y_David_H.Lobisomen.participantes.Participante;

/**
 *
 * @author MarcosFrederico
 */
public class Medico extends Papel {

    public Medico() {
        super(true);
        id = "Médico";
    }

    @Override
    public void acaoNoturnaPlayer() {
        System.out.println("Você é o " + id + ".");
        instrucao();
        int PosicaoAlvo = EntradaDaPosicao();
        Participante alvo = dono.getMesa().getParticipantes().get(PosicaoAlvo);
        protege(alvo);
    }

    private void instrucao() {
        System.out.println("Escolha um jogador para proteger, se esse jogador for atacado essa noite, ele sobrevive");
        int quantParticipantes = dono.getMesa().getParticipantes().size();
        int i;
        for (i = 1; i < quantParticipantes; i++)//Uma vez que o jogador é sempre o primeiro participante
            System.out.println((i) + "- " + dono.getMesa().getParticipantes().get(i).getNome() + ".");
    }

    private int EntradaDaPosicao() {
        int alvo = 0;
        boolean entradaIncorreta = true;
        while (entradaIncorreta)
            try {
                Scanner teclado = new Scanner(System.in);
                alvo = teclado.nextInt();
                if ((alvo < dono.getMesa().getParticipantes().size()) && (alvo > 0)) entradaIncorreta = false;
                else
                    System.out.println("Entre com um numero entre 0 e " + dono.getMesa().getParticipantes().size());
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida, entre com um número inteiro");
            }
        return alvo;
    }

    @Override
    public void acaoNoturnaIA() {
        InteligenciaArtificial ia = (InteligenciaArtificial) dono;
        List<Participante> participantes = ia.getMesa().getParticipantes();
        int quantidadeDeParticipantes = participantes.size();
        int menorDesconfianca = Integer.MAX_VALUE;
        int posMenor = 0;
        //protege o personagem de quem menos se desconfia
        for (int i = 0; i < quantidadeDeParticipantes; i++)
            if (ia.getDesconfiancaDe(participantes.get(i)) < menorDesconfianca)
                if (ia.getDesconfiancaDe(participantes.get(i)) > -50) {
                    menorDesconfianca = ia.getDesconfiancaDe(participantes.get(i));
                    posMenor = i;
                }
        protege(participantes.get(posMenor));
    }

    private void protege(Participante alvo) {
        dono.getMesa().adicionaStatus(alvo, "protegido ");
    }
}
