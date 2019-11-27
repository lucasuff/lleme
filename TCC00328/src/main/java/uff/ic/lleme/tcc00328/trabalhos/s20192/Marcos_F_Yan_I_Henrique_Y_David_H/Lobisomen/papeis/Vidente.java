/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tcc00328.trabalhos.s20192.Marcos_F_Yan_I_Henrique_Y_David_H.Lobisomen.papeis;

import java.util.*;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Marcos_F_Yan_I_Henrique_Y_David_H.Lobisomen.participantes.InteligenciaArtificial;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Marcos_F_Yan_I_Henrique_Y_David_H.Lobisomen.participantes.Participante;

/**
 *
 * @author MarcosFrederico
 */
public class Vidente extends Papel {

    public Vidente() {
        super(true);
        id = "Vidente";
    }

    @Override
    public void acaoNoturnaPlayer() {
        System.out.println("Você é o " + id + ".");
        instrucao();
        int PosicaoAlvo = EntradaDaPosicao();
        Participante alvo = dono.getMesa().getParticipantes().get(PosicaoAlvo);
        System.out.println("A classe do jogador " + alvo.getNome() + " é: " + alvo.getPapel().getId());
    }

    private void instrucao() {
        System.out.println("Escolha um jogador para ver qual o seu papel.");
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
        int tamanho = participantes.size();
        List<Participante> papeisDesconhecidos = new ArrayList<>();
        int i;
        //cria lista com Participantes de quem não se sabe o papel
        for (i = 0; i < tamanho; i++)
            if ("".equals(ia.getPapelConhecidoDe(participantes.get(i))))
                papeisDesconhecidos.add(participantes.get(i));
        int quantDeDesconhecidos = papeisDesconhecidos.size();
        if (quantDeDesconhecidos != 0) {//se ainda há alguém de quem não se sabe o papel
            int maiorDesconfianca = ia.getDesconfiancaDe(papeisDesconhecidos.get(0));
            int posMaior = 0;
            //escolhe o Participante de quem mais se desconfia
            for (i = 0; i < quantDeDesconhecidos; i++)
                if (ia.getDesconfiancaDe(papeisDesconhecidos.get(i)) > maiorDesconfianca) {
                    maiorDesconfianca = ia.getDesconfiancaDe(papeisDesconhecidos.get(i));
                    posMaior = i;
                }
            //adiciona aos papeis conhecidos
            ia.addPapelConhecidoDe(papeisDesconhecidos.get(posMaior), ia, papeisDesconhecidos.get(posMaior).getPapel().getId());
        }
    }
}
