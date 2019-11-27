/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobisomem.papeis;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import lobisomem.participantes.InteligenciaArtificial;
import lobisomem.participantes.Participante;

/**
 *
 * @author MarcosFrederico
 */
public class Lobisomem extends Papel{
    public Lobisomem(){
        super(false);
        id = "Lobisomem";
    }
    @Override
    public void acaoNoturnaPlayer(){ 
        System.out.println("Você é o " + id + ".");
        instrucao();
        int PosicaoAlvo = EntradaDaPosicao();
        Participante alvo = dono.getMesa().getParticipantes().get(PosicaoAlvo);
        escolher(alvo);
    }
    private void instrucao(){
        System.out.println("Escolha um jogador para matar, aquele com mais votos dentre os lobisomens será morto ao fim da noite");
        int quantParticipantes = dono.getMesa().getParticipantes().size();
        int i;
        System.out.println("Lobisomens aliados:");
        for (i = 1; i < quantParticipantes; i++){//Primeiro os Lobisomens aliados
            if("Lobisomem".equals(dono.getMesa().getParticipantes().get(i).getPapel().getId()))
                System.out.println((i) + "- " + dono.getMesa().getParticipantes().get(i).getNome() + ".");
        }
        System.out.println("Jogadores Elegíveis");
        for (i = 1; i < quantParticipantes; i++){//Uma vez que o jogador é sempre o primeiro participante
            if(!"Lobisomem".equals(dono.getMesa().getParticipantes().get(i).getPapel().getId()))
                System.out.println((i) + "- " + dono.getMesa().getParticipantes().get(i).getNome() + ".");
        }
    }
    private int EntradaDaPosicao() {
        int alvo = 0;
        boolean entradaIncorreta = true;
        while (entradaIncorreta){
            try{
                Scanner teclado = new Scanner(System.in);
                alvo = teclado.nextInt();
                if((alvo < dono.getMesa().getParticipantes().size()) && (alvo > 0)){
                    if(!"Lobisomem".equals(dono.getMesa().getParticipantes().get(alvo).getPapel().getId()))
                        entradaIncorreta = false;
                    else
                        System.out.println("O jogador escolhido não pode ser um Lobisomem.");
                }
                else System.out.println("Entre com um numero entre 0 e " + dono.getMesa().getParticipantes().size());
            }catch(InputMismatchException e){
               System.out.println("Entrada inválida, entre com um número inteiro");
            }
        }
        return alvo;
    }

    @Override
    public void acaoNoturnaIA(){
        InteligenciaArtificial ia = (InteligenciaArtificial) dono;
        List<Participante> participantes = ia.getMesa().getParticipantes();
        int quantidadeDeParticipantes = participantes.size();
        int maiorDesconfianca = Integer.MIN_VALUE;
        int posMaior = 0;
        int i;
        for(i = 0; i < quantidadeDeParticipantes; i++){
            if("Lobisomem".equals(participantes.get(i).getPapel().getId()))
                ia.diminuiDesconfiancaEm(participantes.get(i), 5);
                if("".equals(ia.getPapelConhecidoDe(participantes.get(i))))
                    ia.addPapelConhecidoDe(participantes.get(i), dono, "Lobisomem");
        }
        //ataca o personagem de quem mais desconfia
        for (i = 0; i < quantidadeDeParticipantes; i++){
            if(ia.getDesconfiancaDe(participantes.get(i)) > maiorDesconfianca){
                maiorDesconfianca = ia.getDesconfiancaDe(participantes.get(i));
                posMaior = i;
            }
        }
        escolher(participantes.get(posMaior));
    }
    private void escolher(Participante alvo){
        dono.getMesa().adicionaStatus(alvo, "escolhido ");
    }
}
