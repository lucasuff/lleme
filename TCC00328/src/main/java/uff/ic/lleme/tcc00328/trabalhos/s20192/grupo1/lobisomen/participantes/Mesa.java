/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tcc00328.trabalhos.s20192.MarcosF_YanI_HenriqueY_DavidH.lobisomen.participantes;

import java.util.*;
import uff.ic.lleme.tcc00328.trabalhos.s20192.MarcosF_YanI_HenriqueY_DavidH.lobisomen.Narrador;
import uff.ic.lleme.tcc00328.trabalhos.s20192.MarcosF_YanI_HenriqueY_DavidH.lobisomen.papeis.Aldeao;
import uff.ic.lleme.tcc00328.trabalhos.s20192.MarcosF_YanI_HenriqueY_DavidH.lobisomen.papeis.Cacador;
import uff.ic.lleme.tcc00328.trabalhos.s20192.MarcosF_YanI_HenriqueY_DavidH.lobisomen.papeis.Lobisomem;
import uff.ic.lleme.tcc00328.trabalhos.s20192.MarcosF_YanI_HenriqueY_DavidH.lobisomen.papeis.Medico;
import uff.ic.lleme.tcc00328.trabalhos.s20192.MarcosF_YanI_HenriqueY_DavidH.lobisomen.papeis.Vidente;

/**
 *
 * @author MarcosFrederico
 */
public class Mesa {

    private List<Participante> participantes;
    private final Map<Participante, String> statusMap;
    private final Narrador narrador;
    private List<Participante> mesaInicial;

    public Mesa(int quantidade) {
        narrador = new Narrador();
        narrador.setMesa(this);
        participantes = new ArrayList<>();
        mesaInicial = new ArrayList<>();
        statusMap = new HashMap<>();
        inicializaParticipanteNaMesa(true, participantes, statusMap);
        //primeiro elemento na lista 'participantes' é sempre o usuário.
        //Para ver a prova disso, use: System.out.println(mesa.getParticipantes().get(0));
        int i;
        for (i = 0; i < quantidade - 1; i++)
            inicializaParticipanteNaMesa(false, participantes, statusMap);
        //inicia os maps da ia
        for (i = 1; i < quantidade; i++) {
            InteligenciaArtificial ia = (InteligenciaArtificial) participantes.get(i);
            ia.iniciaMaps();
        }
        atribuiPapeisAleatorios();

        mesaInicial.addAll(participantes);

    }

    private void atribuiPapeisAleatorios() {
        Random gerador = new Random();
        int aleatorio;
        aleatorio = mod(gerador.nextInt() % participantes.size());
        Lobisomem lobisomemNecessario = new Lobisomem();
        participantes.get(aleatorio).setPapel(lobisomemNecessario);
        for (Participante participante : participantes) {
            aleatorio = mod(gerador.nextInt() % 5);
            switch (aleatorio) {
                case 0:
                    Aldeao aldeao = new Aldeao();
                    participante.setPapel(aldeao);
                    break;
                case 1:
                    Cacador cacador = new Cacador();
                    participante.setPapel(cacador);
                    break;
                case 2:
                    Medico medico = new Medico();
                    participante.setPapel(medico);
                    break;
                case 3:
                    Vidente vidente = new Vidente();
                    participante.setPapel(vidente);
                    break;
                case 4:
                    Lobisomem lobisomem = new Lobisomem();
                    participante.setPapel(lobisomem);
                    break;
            }
        }
    }

    private int mod(int i) {//módulo
        if (i < 0)
            return -i;
        return i;
    }

    private void inicializaParticipanteNaMesa(boolean usuario, List<Participante> participantes, Map<Participante, String> status) {
        Participante participanteIniciado;
        if (usuario)
            participanteIniciado = new Usuario();
        else
            participanteIniciado = new InteligenciaArtificial();

        participanteIniciado.setMesa(this);
        participantes.add(participanteIniciado);
        status.put(participanteIniciado, "");
    }

    public Narrador getNarrador() {
        return narrador;
    }

    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public List<InteligenciaArtificial> getIA() {
        List<InteligenciaArtificial> ias = new ArrayList<>();
        int i;
        int numParticipantes = participantes.size();
        for (i = 1; i < numParticipantes; i++) {
            InteligenciaArtificial ia = (InteligenciaArtificial) participantes.get(i);
            ias.add(ia);
        }
        return ias;
    }

    public void adicionaStatus(Participante key, String status) {
        String statusAnterior = statusMap.get(key);
        statusMap.remove(key);
        statusMap.put(key, statusAnterior + status);
    }

    public void resetaStatus(Participante key) {
        statusMap.remove(key);
        statusMap.put(key, "");
    }

    public Map<Participante, String> getStatusMap() {
        return statusMap;
    }

    public String getStatus(Participante key) {
        return statusMap.get(key);
    }

    public List<Participante> getMesaInicial() {
        return mesaInicial;
    }

    public void setMesaInicial(List<Participante> participantes) {
        this.participantes = participantes;
    }
}
