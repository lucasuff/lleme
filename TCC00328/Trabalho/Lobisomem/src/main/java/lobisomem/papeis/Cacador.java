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
public class Cacador extends Papel{
    public Cacador(){
        super(true);
        id = "Caçador";
    }
    @Override
    public void acaoNoturnaPlayer(){
        System.out.println(dono.getNome() + " Você é o " + id + ".");
        instrucao();
        int PosicaoAlvo = EntradaDaPosicao();
        Participante alvo = dono.getMesa().getParticipantes().get(PosicaoAlvo);
        marca(alvo);
    }
    
    private void instrucao(){
        System.out.println("Escolha um jogador para marcar, se você morrer, você leva esse jogador com você");
        int quantParticipantes = dono.getMesa().getParticipantes().size();
        int i;
        for (i = 1; i < quantParticipantes; i++){//Uma vez que o jogador é sempre o primeiro participante
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
                if((alvo < dono.getMesa().getParticipantes().size()) && (alvo > 0)) entradaIncorreta = false;
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
        int maiorDesconfianca = Integer.MAX_VALUE;
        int posMaior = 0;
        int i;
        //marca o personagem de quem mais desconfia
        for (i = 0; i < quantidadeDeParticipantes; i++){
            if(ia.getDesconfiancaDe(participantes.get(i)) > maiorDesconfianca){
                maiorDesconfianca = ia.getDesconfiancaDe(participantes.get(i));
                posMaior = i;
            }
        }
        marca(participantes.get(posMaior));
    }
    private void marca(Participante alvo){
        dono.getMesa().adicionaStatus(alvo, "marcado:" + dono.getNome() + " ");
    }
}
