/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobisomem.participantes;

import java.util.*;
import lobisomem.papeis.Papel;

/**
 *
 * @author MarcosFrederico
 */
public class InteligenciaArtificial extends Participante{
    public Map<Participante, Integer> desconfianca;
    private final int comportamento;
    private final Map<Participante, String> papelConhecido;
    
    public InteligenciaArtificial(){
        Random gerador = new Random();
        comportamento = 1 + mod(gerador.nextInt()%3);
        desconfianca = new HashMap<>();
        papelConhecido = new HashMap<>();
    }
    private int mod(int i) {//módulo
        if(i < 0) return -i;
        return i;
    }
    public void iniciaMaps(){//se por no construtor, vai dar errado pois a lista da mesa não estará completa
        int i;
        List<Participante> participantes = getMesa().getParticipantes();
        int quantidadeDeParticipantes = participantes.size();
        for (i = 0; i < quantidadeDeParticipantes; i++){
            papelConhecido.put(participantes.get(i), "");
            //se não for ele mesmo
            if(!this.equals(getMesa().getParticipantes().get(i))) desconfianca.put(participantes.get(i), 0);    
            else desconfianca.put(this, -100);   
        }
    }
    @Override
    public void setPapel(Papel papel){
        this.papel = papel;
        papel.setDono(this);
        papelConhecido.put(this, papel.getId());
    }
    public int getComportamento(){
        return comportamento;
    }
    public int getDesconfiancaDe(Participante key){
        return desconfianca.get(key);
    }
    
    public String getPapelConhecidoDe(Participante key){
        return papelConhecido.get(key);
    }
    public void addPapelConhecidoDe(Participante alvo, Participante fonte, String papel){
        int desconfiancaNaFonte = desconfianca.get(alvo);
        if ("".equals(papelConhecido.get(alvo))){
            if (desconfiancaNaFonte < -5) papelConhecido.put(alvo, papel);
        }else{
            if(papel.equals(papelConhecido.get(alvo))){
                diminuiDesconfiancaEm(fonte, 2);
                if ("Lobisomem".equals(papel)) aumentaDesconfiancaEm(alvo, 2);
                else diminuiDesconfiancaEm(alvo, 2);
            }else{
                if (desconfiancaNaFonte < -5){
                    papelConhecido.put(alvo, papel);
                    aumentaDesconfiancaEm(fonte);
                }else aumentaDesconfiancaEm(fonte, 4);
            }
        }
    }
    public void aumentaDesconfiancaEm(Participante key){
        int desconfiancaAtual = desconfianca.get(key);
        desconfianca.put(key, comportamento + desconfiancaAtual);
    }
    public void aumentaDesconfiancaEm(Participante key, int vezes){
        int desconfiancaAtual = desconfianca.get(key);
        desconfianca.put(key, comportamento*vezes + desconfiancaAtual);
    }
    public void diminuiDesconfiancaEm(Participante key){
        int desconfiancaAtual = desconfianca.get(key);
        desconfianca.put(key, desconfiancaAtual - comportamento);
    }
    public void diminuiDesconfiancaEm(Participante key, int vezes){
        int desconfiancaAtual = desconfianca.get(key);
        desconfianca.put(key, desconfiancaAtual - comportamento*vezes);
    }
    
    @Override
    public Participante getVoto(){
        int indiceDoVoto = posicaoDaMaiorDesconfianca();
        Participante participanteIndicado = getMesa().getParticipantes().get(indiceDoVoto);
        //votaNaDecisao(participanteIndicado);
        return participanteIndicado;
    }
    private int posicaoDaMaiorDesconfianca(){
        List<Participante> participantes = getMesa().getParticipantes();
        int quantidadeDeParticipantes = participantes.size();
        int i;
        int maiorDesconfianca = desconfianca.get(participantes.get(0));//inicia no primeiro valor
        int posMaiorDesconfianca = 0;
        for(i = 1; i < quantidadeDeParticipantes; i++){
            if(desconfianca.get(participantes.get(i)) > maiorDesconfianca){
                maiorDesconfianca = desconfianca.get(participantes.get(i));
                posMaiorDesconfianca = i;
            }
        }
        return posMaiorDesconfianca;
    }
    private void votaNaDecisao(Participante alvo){
        String statusAnterior = getMesa().getStatus(alvo);
        String statusNovo;
        if("".equals(statusAnterior)){
            statusAnterior = "0";
        }
        int valorAnterior = Integer.parseInt(statusAnterior);
        valorAnterior += 1;
        statusNovo = Integer.toString(valorAnterior);
        getMesa().resetaStatus(alvo);
        getMesa().adicionaStatus(alvo, statusNovo);
    }
    
    
    
    public boolean tomadaDeAcao(boolean forcada){
        List<Participante> participantes = getMesa().getParticipantes();
        int quantidadeDeParticipantes = participantes.size();
        int maiorDesconfianca = desconfianca.get(participantes.get(0));
        int menorDesconfianca = Integer.MAX_VALUE;
        int posMaior = 0;
        int posMenor = 0;
        
        for (int i = 0; i < quantidadeDeParticipantes; i++){
            //acha maior desconfianca
            if(desconfianca.get(participantes.get(i)) > maiorDesconfianca){
                maiorDesconfianca = desconfianca.get(participantes.get(i));
                posMaior = i;
            }
            //acha a menor
            if(desconfianca.get(participantes.get(i)) < menorDesconfianca){
                if(desconfianca.get(participantes.get(i)) > -50){
                    menorDesconfianca = desconfianca.get(participantes.get(i));
                    posMenor = i;
                }
            }
        }
        
        //toma decisao baseado em que achou, o 5 é o momento onde se passa a confiar ou desconfiar
        if(!forcada){
            if(maiorDesconfianca > -menorDesconfianca){
                if (maiorDesconfianca > 5){
                    acusar(participantes.get(posMaior));
                    return true;
                }
                else return falarNada();
            }else{
                if (menorDesconfianca < -5){
                    apoiar(participantes.get(posMenor));
                    return true;
                }
                else return falarNada();
            }
        }else{
            Random gerador = new Random();
            int aleatorio = gerador.nextInt()%2;
            if(aleatorio == 1) acusar(participantes.get(posMaior));
            else apoiar(participantes.get(posMenor));
            return true;
        }
    }
    
    
    @Override
    public void acusar(Participante alvo){
        System.out.println(getNome() + ":  " + falaAcusar(alvo.getNome()));
        for (InteligenciaArtificial ia : getMesa().getIA()){
            ia.ouveAcusacao(alvo, this);
        }
        getMesa().getNarrador().setUltimaAcao("Acusar", this);
        //feedback loop
        aumentaDesconfiancaEm(alvo);
    }
    private String falaAcusar(String nomeAlvo){
        return "Acusar " + nomeAlvo;//ainda não implementado
    }
    @Override
    public void apoiar(Participante alvo){
        System.out.println(getNome() + ":  " + falaApoiar(alvo.getNome()));
        for (InteligenciaArtificial ia : getMesa().getIA()){
            ia.ouveApoiar(alvo, this);
        }
        getMesa().getNarrador().setUltimaAcao("Apoiar", this);
         //feedback loop
        diminuiDesconfiancaEm(alvo);
    }
    private String falaApoiar(String nomeAlvo){
        return "Apoiar " + nomeAlvo;//ainda não implementado
    }
    @Override
    public void concordar(Participante alvo, String sobre){
        System.out.println(getNome() + ":  " + falaConcordar(alvo.getNome()));
        for (InteligenciaArtificial ia : getMesa().getIA()){
            ia.ouveConcordar(alvo, this, sobre);
        }
         //feedback loop
        diminuiDesconfiancaEm(alvo);
    }
    private String falaConcordar(String nomeAlvo){
        return "Concordar " + nomeAlvo;//ainda não implementado
    }
    @Override
    public void discordar(Participante alvo, String sobre){
        System.out.println(getNome() + ":  " + falaDiscordar(alvo.getNome()));
        for (InteligenciaArtificial ia : getMesa().getIA()){
            ia.ouveDiscordar(alvo, this, sobre);
        }
         //feedback loop
        aumentaDesconfiancaEm(alvo);
    }
    private String falaDiscordar(String nomeAlvo){
        return "Discordar " + nomeAlvo;//ainda não implementado
    }
    @Override
    public boolean falarNada(){
        return false;
    }
    
    public void ouveAcusacao(Participante alvo, Participante fonte){
        if(!this.equals(fonte)){
            int desconfiancaNaFonte = NivelDeDesconfianca(fonte);
            int desconfiancaNoAlvo = NivelDeDesconfianca(alvo);
            switch(desconfiancaNaFonte){
                case 1:
                    aumentaDesconfiancaEm(fonte);
                    if(desconfiancaNoAlvo == -1){
                        diminuiDesconfiancaEm(alvo);
                        discordar(fonte, "Acusar");
                    }
                    if (desconfiancaNoAlvo == 1) aumentaDesconfiancaEm(alvo);
                    break;
                case 0:
                    if (desconfiancaNoAlvo == 1){
                        aumentaDesconfiancaEm(alvo);
                        diminuiDesconfiancaEm(fonte);
                    }
                    if (desconfiancaNoAlvo == -1) aumentaDesconfiancaEm(fonte, 2);
                    if(desconfiancaNoAlvo == 0){
                        aumentaDesconfiancaEm(alvo);
                        aumentaDesconfiancaEm(fonte, 2);
                    }
                    break;
                case -1:
                    aumentaDesconfiancaEm(alvo);
                    if(desconfiancaNoAlvo == 1){
                        aumentaDesconfiancaEm(alvo);
                        concordar(fonte, "Acusar");
                    }
                    if(desconfiancaNoAlvo == -1) aumentaDesconfiancaEm(fonte);
                    break;
            }
        }
    }
    private int NivelDeDesconfianca(Participante key){
        if (desconfianca.get(key) > 5) return 1;
        if (desconfianca.get(key) < -5) return -1;
        return 0;
    }
    public void ouveApoiar(Participante alvo, Participante fonte){
        if(!this.equals(fonte)){
            int desconfiancaNaFonte = NivelDeDesconfianca(fonte);
            int desconfiancaNoAlvo = NivelDeDesconfianca(alvo);
            switch(desconfiancaNaFonte){
                case 1:
                    aumentaDesconfiancaEm(alvo);
                    if(desconfiancaNoAlvo == 1){
                        aumentaDesconfiancaEm(fonte);
                        discordar(fonte, "Apoiar");
                    }
                    if(desconfiancaNoAlvo == -1) aumentaDesconfiancaEm(fonte, 2);
                    break;
                case 0:
                    if(desconfiancaNoAlvo == 1) aumentaDesconfiancaEm(fonte);
                    if(desconfiancaNoAlvo == -1) diminuiDesconfiancaEm(fonte);
                    if(desconfiancaNoAlvo == 0){
                        aumentaDesconfiancaEm(alvo);
                        aumentaDesconfiancaEm(fonte);
                    }
                    break;
                case -1:
                    diminuiDesconfiancaEm(alvo);
                    if (desconfiancaNoAlvo == 1) aumentaDesconfiancaEm(fonte);
                    if (desconfiancaNoAlvo == -1){
                        diminuiDesconfiancaEm(fonte);
                        concordar(fonte, "Apoiar");
                    }
                    break;
            }
        }
    }
    
    public void ouveConcordar(Participante alvo, Participante fonte, String sobre){
        if(!this.equals(fonte)){
            if(("Acusar".equals(sobre))||("Apoiar".equals(sobre))) 
                ouveApoiarSemDisparo(alvo, fonte);
        }
    }
    public void ouveDiscordar(Participante alvo, Participante fonte, String sobre){
        if(!this.equals(fonte)){
            if(("Acusar".equals(sobre))||("Apoiar".equals(sobre))) 
                ouveAcusarSemDisparo(alvo, fonte);
        }
    }
    private void ouveAcusarSemDisparo(Participante alvo, Participante fonte){//igual à acusar, mas sem disparar concordar
        if(!this.equals(fonte)){
            int desconfiancaNaFonte = NivelDeDesconfianca(fonte);
            int desconfiancaNoAlvo = NivelDeDesconfianca(alvo);
            switch(desconfiancaNaFonte){
                case 1:
                    aumentaDesconfiancaEm(fonte);
                    if(desconfiancaNoAlvo == -1){
                        diminuiDesconfiancaEm(alvo);
                    }
                    if (desconfiancaNoAlvo == 1) aumentaDesconfiancaEm(alvo);
                    break;
                case 0:
                    if (desconfiancaNoAlvo == 1){
                        aumentaDesconfiancaEm(alvo);
                        diminuiDesconfiancaEm(fonte);
                    }
                    if (desconfiancaNoAlvo == -1)
                    break;
                case -1:
                    aumentaDesconfiancaEm(alvo);
                    if(desconfiancaNoAlvo == 1){
                        aumentaDesconfiancaEm(alvo);
                    }
                    if(desconfiancaNoAlvo == -1) aumentaDesconfiancaEm(fonte);
                    break;
            }
        }
    }
    private void ouveApoiarSemDisparo(Participante alvo, Participante fonte){
        if(!this.equals(fonte)){
            int desconfiancaNaFonte = NivelDeDesconfianca(fonte);
            int desconfiancaNoAlvo = NivelDeDesconfianca(alvo);
            switch(desconfiancaNaFonte){
                case 1:
                    aumentaDesconfiancaEm(alvo);
                    if(desconfiancaNoAlvo == 1){
                        aumentaDesconfiancaEm(fonte);
                    }
                    if(desconfiancaNoAlvo == -1) aumentaDesconfiancaEm(fonte, 2);
                    break;
                case 0:
                    if(desconfiancaNoAlvo == 1) aumentaDesconfiancaEm(fonte);
                    if(desconfiancaNoAlvo == -1) diminuiDesconfiancaEm(fonte);
                    break;
                case -1:
                    diminuiDesconfiancaEm(alvo);
                    if (desconfiancaNoAlvo == 1) aumentaDesconfiancaEm(fonte);
                    if (desconfiancaNoAlvo == -1){
                        diminuiDesconfiancaEm(fonte);
                    }
                    break;
            }
        }
    }   
}